package factory.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	    
}