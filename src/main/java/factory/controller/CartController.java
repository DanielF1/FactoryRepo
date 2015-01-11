package factory.controller;

import java.time.LocalDate;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.salespointframework.order.Cart;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.quantity.Units;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import factory.model.Article;
import factory.model.Customer;
import factory.model.CustomerRepository;
import factory.model.ExpenditureRepository;
import factory.model.Income;
import factory.model.IncomeRepository;

@Controller
@PreAuthorize("isAuthenticated()")
@SessionAttributes("cart")
public class CartController {
	
	private final OrderManager<Order> orderManager;
	private final UserAccountManager userAccountManager;
	private final CustomerRepository customerRepository;
	private final IncomeRepository incomeRepository;
	
	@Autowired
	public CartController(	OrderManager<Order> orderManager, 
							UserAccountManager userAccountManager, 
							CustomerRepository customerRepository,
							IncomeRepository incomeRepository) {

		Assert.notNull(orderManager, "OrderManager must not be null!");
		this.orderManager = orderManager;
		this.userAccountManager = userAccountManager;
		this.customerRepository = customerRepository;
		this.incomeRepository = incomeRepository;
	}

	/**
	 * 
	 * @return new cart
	 */
	@ModelAttribute("cart")
	private Cart inizializeCart(){
		return new Cart();
	}
	
	/**
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public String basket(ModelMap modelMap) {
		modelMap.addAttribute("customerlist", customerRepository.findAll());
		return "cart";
	}
	
	/**
	 * Artikel wird mit der quantity hinzugefügt, außerdem wird der cart geupdated
	 * wenn der verkäufer eingeloggt ist, wird ihm das Schnellsortiment zurückgegeben, allen anderen das Sortiment
	 * 
	 * @param article
	 * @param number
	 * @param userAccount
	 * @param cart
	 * @return
	 */
	 @RequestMapping(value = "/cart", method = RequestMethod.POST)
	    public String addArticle(	@RequestParam("pid") Article article, 
	    							@RequestParam("number") int number,
	    							@LoggedIn Optional<UserAccount> userAccount,
	    							@ModelAttribute Cart cart) {
	    	
			Quantity quantity = Units.of(number);
			
			cart.addOrUpdateItem(article, quantity);
			
			if(userAccount.get().hasRole(new Role("ROLE_SALESMAN"))){
			return "redirect:/saleSortiment";	
			}
			return "redirect:/sortiment";
	    }
	 /**
	  *  Einzelne Artikel werden im Warenkorb gelöscht
	  * 
	  * @param pid
	  * @param cart
	  * @return
	  */
	 @RequestMapping(value = "/article/{pid}", method = RequestMethod.GET)
	 public String deleteArticle(@PathVariable String pid, @ModelAttribute Cart cart) {

	 cart.removeItem(pid);

	 return "redirect:/cart";
	 }
	 
	 
	 /**
	  * KUNDE BESTELLT PERSÖNLICH
	  * wenn man die Bestellung bezahlt und abgeschlossen hat,
	  * wird der Warenkorb gelöscht und man kommt wieder zurück auf die Startseite
	  * 
	  * @param userAccount
	  * @param cart
	  * @return
	  */
	   @RequestMapping(value="/checkout", method=RequestMethod.POST)   
	   public String Buy(@LoggedIn Optional<UserAccount> userAccount, @ModelAttribute Cart cart){
	    	
	    	return userAccount.map(account -> {

					Order order = new Order(account, Cash.CASH);
					
					cart.addItemsTo(order);
					
					UserAccount userAcc = userAccount.get();
					
					double i = cart.getPrice().getAmountMajorLong();
					
					Customer cust = customerRepository.findByUserAccount(userAcc);
					String customer = cust.getFirstname() + " " + cust.getFamilyname();
					incomeRepository.save(new Income(customer, LocalDate.now(), i, "Produktkauf"));
					
					orderManager.payOrder(order);
					orderManager.completeOrder(order);
					orderManager.save(order);

					cart.clear();

					return "redirect:/";
				}).orElse("redirect:/cart");
		}
	   
	   /**
	    * VERKÄUFER BESTELLT FÜR KUNDE
		* wenn man die Bestellung bezahlt und abgeschlossen hat,
	    * wird der Warenkorb gelöscht und man kommt wieder zurück auf die Startseite
	    * 
	    * @param session
	    * @param userAccountIdent
	    * @param cart
	    * @return
	    */
	   @RequestMapping(value="/saleCheckout", method=RequestMethod.POST)   
	   public String BuyForCustomer(HttpSession session, @RequestParam("id") String userAccountIdent, @ModelAttribute Cart cart){
	    	
		   Optional<UserAccount> userAccount = userAccountManager.findByUsername(userAccountIdent);
		   
	    	return userAccount.map(account -> {

	    			Order order = new Order(account, Cash.CASH);
	    			
	    			cart.addItemsTo(order);

	    			UserAccount userAcc = userAccount.get();
					
	    			double i = cart.getPrice().getAmountMajorLong();
	    			
					Customer cust = customerRepository.findByUserAccount(userAcc);
					String customer = cust.getFirstname() + " " + cust.getFamilyname();
					incomeRepository.save(new Income(customer, LocalDate.now(), i, "Produktkauf"));
	    			
	    			orderManager.payOrder(order);
	    			orderManager.completeOrder(order);
	    			orderManager.save(order);

	    			cart.clear();

					return "redirect:/";
				}).orElse("redirect:/cart");
		}

	   /**
	    * Der Warenkorb wird komplett verworfen und gelöscht
	    * 
	    * @param cart
	    * @return
	    */
	    @RequestMapping(value = "cart/clear", method = RequestMethod.POST)
		public String clear(@ModelAttribute Cart cart) {
	    	
	    	cart.clear();
	    	
			return "redirect:/sortiment";
			
		}
}

