package factory.controller;

import org.salespointframework.order.Cart;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.quantity.Units;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import factory.model.Article;
import factory.model.Sortiment;

@Controller
@PreAuthorize("isAuthenticated()")
@SessionAttributes("cart")
public class ShopController {
	
	
	private final Sortiment sortiment;
	
	@Autowired
	public ShopController(Sortiment sortiment){
		this.sortiment = sortiment;
	}

		
	    @RequestMapping(value="/sortiment", method=RequestMethod.GET)
	    public String Willkommen(Model model) {
	    	
	    	
	    	model.addAttribute("articles", sortiment.findAll());
	    	
		
	        return "sortiment";
	    }
	    
	    
	    @RequestMapping("/detail/{pid}")
		public String detail(@PathVariable("pid") Article article ,Model model) {
			
	    	model.addAttribute("article", article);
	    	
			return "detail";
		}
	    
	    @RequestMapping(value = "/warenkorb", method = RequestMethod.POST)
	    public String addArticle(@RequestParam("pid") Article article, @RequestParam("number") int number, @ModelAttribute Cart cart,
				ModelMap modelMap) {
	    	
			Quantity quantity = Units.of(number);
			cart.addOrUpdateItem(article, quantity);
			
			return "redirect:/sortiment";
	    }
	    
	    
		@ModelAttribute("cart")
		private Cart getCart() {
			return new Cart();
		}
	    
		@RequestMapping(value = "/warenkorb", method = RequestMethod.GET)
		public String basket() {
			return "warenkorb";
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
