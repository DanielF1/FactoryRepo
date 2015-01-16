package factory.controller;

import java.time.LocalDate;
import java.util.List;
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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import factory.model.Article;
import factory.model.ArticleRepository;
import factory.model.Customer;
import factory.model.Income;
import factory.repository.CustomerRepository;
import factory.repository.IncomeRepository;

@Controller
@PreAuthorize("isAuthenticated()")
@SessionAttributes("cart")
public class CartController {
	
	private final OrderManager<Order> orderManager;
	private final UserAccountManager userAccountManager;
	private final CustomerRepository customerRepository;
	private final IncomeRepository incomeRepository;
	private final ArticleRepository articleRepository;
	
	@Autowired
	public CartController(	OrderManager<Order> orderManager, 
							UserAccountManager userAccountManager, 
							CustomerRepository customerRepository,
							IncomeRepository incomeRepository,
							ArticleRepository articleRepository) {

		Assert.notNull(orderManager, "OrderManager must not be null!");
		this.orderManager = orderManager;
		this.userAccountManager = userAccountManager;
		this.customerRepository = customerRepository;
		this.incomeRepository = incomeRepository;
		this.articleRepository = articleRepository;
	}

	/**
	 * 
	 * @return Cart bereitgestellt von Salespoint
	 */
	@ModelAttribute("cart")
	private Cart inizializeCart(){
		return new Cart();
	}
	
	/**
	 * 
	 * @param modelMap ModelMap bereitgestellt von Spring
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
	 * @param article ist im model definiert, Article ist das Produkt, welches in der Firma hergestellt und verkauft wird
	 * @param number ist für die Anzahl der Artikel verantwortlich
	 * @param userAccount UserAccount bereitgestellt von Salespoint
	 * @param cart Cart bereitgestellt von Salespoint
	 */
	 @RequestMapping(value = "/cart", method = RequestMethod.POST)
	    public String addArticle(	@RequestParam("pid") Article article, 
	    							@RequestParam("number") int number,
	    							@LoggedIn Optional<UserAccount> userAccount,
	    							@ModelAttribute Cart cart,
	    							Model model) {
	    	
			Quantity quantity = Units.of(number);
			
			cart.addOrUpdateItem(article, quantity);
			
			if(userAccount.get().hasRole(new Role("ROLE_SALESMAN"))){
				model.addAttribute("error_green", "Artikel in den Warenkorb hinzugefügt");
				model.addAttribute("articles", articleRepository.findAll());
			return "saleSortiment";	
			}
			
			model.addAttribute("error_green", "Artikel in den Warenkorb hinzugefügt");
			model.addAttribute("articles", articleRepository.findAll());
			
			return "sortiment";
	    }
	 /**
	  *  Einzelne Artikel werden im Warenkorb gelöscht
	  * 
	  * @param pid Artikel hat festgelegten Identifier und kann dadurch gelöscht werden
	  * @param cart Cart bereitgestellt von Salespoint
	  */
	 @RequestMapping(value = "/article/{pid}", method = RequestMethod.GET)
	 public String deleteArticle(@PathVariable String pid, @ModelAttribute Cart cart, Model model) {

	 cart.removeItem(pid);
	 model.addAttribute("error_green", "Artikel gelöscht");

	 return "cart";
	 }
	 
	 
	 /**
	  * KUNDE BESTELLT PERSÖNLICH
	  * wenn man die Bestellung bezahlt und abgeschlossen hat,
	  * wird der Warenkorb gelöscht und man kommt wieder zurück auf die Startseite
	  * 
	  * @param userAccount UserAccount bereitgestellt von Salespoint
	  * @param cart Cart bereitgestellt von Salespoint
	  */
	   @RequestMapping(value="/checkout", method=RequestMethod.POST)   
	   public String Buy(@LoggedIn Optional<UserAccount> userAccount, @ModelAttribute Cart cart, Model model){
	    	
		   if(cart.isEmpty()){
			   model.addAttribute("error", "Es wurden keine Artikel ausgewählt");
			   return "cart";
		   }
		   
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

					List<Article> list1 = (List<Article>) articleRepository.findAll();
					Article bestseller = list1.get(2);
					Article newbie1 = list1.get(4);
					Article newbie2 = list1.get(5);
					
					model.addAttribute("error_green", "Bestellung abgeschlossen");
					model.addAttribute("article", bestseller);
					model.addAttribute("newbie1", newbie1);
					model.addAttribute("newbie2", newbie2);
					
					return "index";
				}).orElse("redirect:/cart");
		}
	   
	   /**
		  * KUNDE BESTELLT PERSÖNLICH
		  * wenn man die Bestellung bezahlt und abgeschlossen hat,
		  * wird der Warenkorb gelöscht und man kommt wieder zurück auf die Startseite
		  * 
		  * @param userAccount UserAccount bereitgestellt von Salespoint
		  * @param cart Cart bereitgestellt von Salespoint
		  */
		   @RequestMapping(value="/superCheckout", method=RequestMethod.POST)   
		   public String BuySuper(@ModelAttribute Cart cart, Model model){
		    	
			   Customer cust = customerRepository.findByUsername("hans");
			  Optional<UserAccount> userAccount = userAccountManager.findByUsername("hans");
			   
			   if(cart.isEmpty()){
				   model.addAttribute("error", "Es wurden keine Artikel ausgewählt");
				   return "cart";
			   }
			   
		    	return userAccount.map(account -> {

						Order order = new Order(account, Cash.CASH);
						
						cart.addItemsTo(order);
						
						
						
						double i = cart.getPrice().getAmountMajorLong();
						
						String customer = cust.getFirstname() + " " + cust.getFamilyname();
						incomeRepository.save(new Income(customer, LocalDate.now(), i, "Produktkauf"));
						
						orderManager.payOrder(order);
						orderManager.completeOrder(order);
						orderManager.save(order);

						cart.clear();

						List<Article> list1 = (List<Article>) articleRepository.findAll();
						Article bestseller = list1.get(2);
						Article newbie1 = list1.get(4);
						Article newbie2 = list1.get(5);
						
						model.addAttribute("error_green", "Bestellung abgeschlossen");
						model.addAttribute("article", bestseller);
						model.addAttribute("newbie1", newbie1);
						model.addAttribute("newbie2", newbie2);
						
						return "index";
					}).orElse("redirect:/cart");
			}
	   
	   /**
	    * VERKÄUFER BESTELLT FÜR KUNDE
		* wenn man die Bestellung bezahlt und abgeschlossen hat,
	    * wird der Warenkorb gelöscht und man kommt wieder zurück auf die Startseite
	    * 
	    * @param session
	    * @param userAccountIdent Identifier eines Useraccounts
	    * @param cart Cart bereitgestellt von Salespoint
	    * @return geht auf die Startseite zurück
	    */
	   @RequestMapping(value="/saleCheckout", method=RequestMethod.POST)   
	   public String BuyForCustomer(HttpSession session, @RequestParam("id") String userAccountIdent, @ModelAttribute Cart cart, Model model){
	    	
		   Optional<UserAccount> userAccount = userAccountManager.findByUsername(userAccountIdent);
		   
		   if(cart.isEmpty()){
			   model.addAttribute("error", "Es wurden keine Artikel ausgewählt");
			   return "cart";
		   }
		   
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

	    			List<Article> list1 = (List<Article>) articleRepository.findAll();
					Article bestseller = list1.get(2);
					Article newbie1 = list1.get(4);
					Article newbie2 = list1.get(5);
					
					model.addAttribute("error_green", "Bestellung abgeschlossen");
					model.addAttribute("article", bestseller);
					model.addAttribute("newbie1", newbie1);
					model.addAttribute("newbie2", newbie2);
					
					return "index";
				}).orElse("redirect:/cart");
		}

	   /**
	    * Der Warenkorb wird komplett verworfen und gelöscht
	    * 
	    * @param cart Cart bereitgestellt von Salespoint
	    * @return man kommt wieder in den Artikelkatalog
	    */
	    @RequestMapping(value = "cart/clear", method = RequestMethod.POST)
		public String clear(@ModelAttribute Cart cart,
							@LoggedIn Optional<UserAccount> userAccount,
							Model model) {
	    	
	    	cart.clear();
	    	
	    	if(userAccount.get().hasRole(new Role("ROLE_SALESMAN"))){
				model.addAttribute("error_green", "Warenkorb verworfen");
				model.addAttribute("articles", articleRepository.findAll());
			return "saleSortiment";	
			}
	    	model.addAttribute("error_green", "Warenkorb verworfen");
			model.addAttribute("articles", articleRepository.findAll());
			return "sortiment";
			
		}
}

