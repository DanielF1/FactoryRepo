package factory.controller;

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

import factory.model.Article;
import factory.model.CustomerRepository;

@Controller
@PreAuthorize("isAuthenticated()")
public class CartController {
	
	private final OrderManager<Order> orderManager;
	private final UserAccountManager userAccountManager;
	private final CustomerRepository customerRepository;
	
	@Autowired
	public CartController(OrderManager<Order> orderManager, UserAccountManager userAccountManager, CustomerRepository customerRepository) {

		Assert.notNull(orderManager, "OrderManager must not be null!");
		this.orderManager = orderManager;
		this.userAccountManager = userAccountManager;
		this.customerRepository = customerRepository;
	}

	
	@ModelAttribute("cart")
	private Cart getCart(HttpSession session) {

		Cart cart = (Cart) session.getAttribute("cart");

		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}

		return cart;
	}
    
	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public String basket(ModelMap modelMap) {
		modelMap.addAttribute("customerlist", customerRepository.findAll());
		return "cart";
	}
    	
	 @RequestMapping(value = "/cart", method = RequestMethod.POST)
	    public String addArticle(	@RequestParam("pid") Article article, 
	    							@RequestParam("number") int number,
	    							@LoggedIn Optional<UserAccount> userAccount,
	    							HttpSession session) {
	    	
			Quantity quantity = Units.of(number);
			Cart cart = getCart(session);
			cart.addOrUpdateItem(article, quantity);
			
			if(userAccount.get().hasRole(new Role("ROLE_SALESMAN"))){
			return "redirect:/saleSortiment";	
			}
			return "redirect:/sortiment";
	    }
	 
	 // Einzelne Artikel sollen im Warenkorb gelöscht werden
	 @RequestMapping(value = "/article/{pid}", method = RequestMethod.GET)
	 public String deleteArticle(@PathVariable String pid, @ModelAttribute Cart cart) {

	 cart.removeItem(pid);

	 return "redirect:/cart";
	 }
	 
	 
	   @RequestMapping(value="/checkout", method=RequestMethod.POST)   
	   public String Buy(HttpSession session, @LoggedIn Optional<UserAccount> userAccount){
	    	
	    	return userAccount.map(account -> {

					Order order = new Order(account, Cash.CASH);
					Cart cart = getCart(session);
					cart.addItemsTo(order);

					orderManager.payOrder(order);
					orderManager.completeOrder(order);
					orderManager.save(order);

					cart.clear();

					return "redirect:/";
				}).orElse("redirect:/cart");
		}
	
	   @RequestMapping(value="/saleCheckout", method=RequestMethod.POST)   
	   public String BuyForCustomer(HttpSession session, @RequestParam("id") String userAccountIdent){
	    	
		   Optional<UserAccount> userAccount = userAccountManager.findByUsername(userAccountIdent);
		   
	    	return userAccount.map(account -> {

	    			Order order = new Order(account, Cash.CASH);
	    			Cart cart = getCart(session);
	    			cart.addItemsTo(order);

	    			orderManager.payOrder(order);
	    			orderManager.completeOrder(order);
	    			orderManager.save(order);

	    			cart.clear();

					return "redirect:/";
				}).orElse("redirect:/cart");
		}
	    
	    @RequestMapping(value = "cart/clear", method = RequestMethod.POST)
		public String clear(HttpSession session) {
	    	
	    	Cart cart = getCart(session);
	    	cart.clear();
	    	
			return "redirect:/sortiment";
			
		}
}

