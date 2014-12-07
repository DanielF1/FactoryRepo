package factory.controller;

import java.util.Optional;

import org.salespointframework.order.Cart;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.quantity.Units;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import factory.model.Article;

@Controller
@PreAuthorize("isAuthenticated()")
@SessionAttributes("cart")
public class CartController {
	
	private final OrderManager<Order> orderManager;
	
	@Autowired
	public CartController(OrderManager<Order> orderManager) {

		Assert.notNull(orderManager, "OrderManager must not be null!");
		this.orderManager = orderManager;
	}

	
	@ModelAttribute("cart")
	private Cart getCart() {
		return new Cart();
	}
    
	@RequestMapping(value = "/warenkorb", method = RequestMethod.GET)
	public String basket() {
		return "warenkorb";
	}
    	
	 @RequestMapping(value = "/warenkorb", method = RequestMethod.POST)
	    public String addArticle(@RequestParam("pid") Article article, @RequestParam("number") int number, @ModelAttribute Cart cart) {
	    	
			Quantity quantity = Units.of(number);
			cart.addOrUpdateItem(article, quantity);
			
			return "redirect:/sortiment";
	    }
	 
	 // Einzelne Artikel sollen im Warenkorb gelöscht werden
	 @RequestMapping(value = "/article/{pid}", method = RequestMethod.GET)
	 public String deleteArticle(@PathVariable String pid, @ModelAttribute Cart cart) {

	 cart.removeItem(pid);

	 return "redirect:/warenkorb";
	 }
	 
	 
	    @RequestMapping(value="/kaufen", method=RequestMethod.POST)   
	    public String Buy(@ModelAttribute Cart cart, @LoggedIn Optional<UserAccount> userAccount){
	    	
	    	return userAccount.map(account -> {

					Order order = new Order(account, Cash.CASH);

					cart.addItemsTo(order);

					orderManager.payOrder(order);
					orderManager.completeOrder(order);
					orderManager.save(order);

					cart.clear();

					return "redirect:/sortiment";
				}).orElse("redirect:/warenkorb");
		}
	
	
	    
	    @RequestMapping(value = "cart/clear", method = RequestMethod.POST)
		public String clear(@ModelAttribute Cart cart) {
	    	
	    	cart.clear();
	    	
			return "redirect:/sortiment";
			
		}   
}
