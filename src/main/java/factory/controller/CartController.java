package factory.controller;

import org.salespointframework.order.Cart;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.quantity.Units;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import factory.model.Article;

@Controller
@SessionAttributes("cart")
public class CartController {

	
	@ModelAttribute("cart")
	private Cart getCart() {
		return new Cart();
	}
    
	@RequestMapping(value = "/warenkorb", method = RequestMethod.GET)
	public String basket() {
		return "warenkorb";
	}
    	
	 @RequestMapping(value = "/warenkorb", method = RequestMethod.POST)
	    public String addArticle(@RequestParam("pid") Article article, @RequestParam("number") int number, @ModelAttribute Cart cart,
				ModelMap modelMap) {
	    	
			Quantity quantity = Units.of(number);
			cart.addOrUpdateItem(article, quantity);
			
			return "redirect:/sortiment";
	    }
	    
	
	    @RequestMapping(value="/kaufen", method=RequestMethod.POST)   
	    public String Buy(){
	    	
	    	return "kaufen";
	}
	    
	    @RequestMapping(value = "cart/clear", method = RequestMethod.POST)
		public String clear(@ModelAttribute Cart cart) {
	    	
	    	cart.clear();
	    	
			return "redirect:/sortiment";
			
		}   
}

