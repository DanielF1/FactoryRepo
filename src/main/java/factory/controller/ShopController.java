package factory.controller;


import java.util.Optional;

import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.quantity.Units;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import factory.model.Article;
import factory.model.ArticleRepository;

@Controller
@SessionAttributes("cart")
public class ShopController {
	
	private final Inventory<InventoryItem> inventory;
	private final ArticleRepository articleRepository;
	
	@Autowired
	public ShopController(ArticleRepository articleRepository, Inventory<InventoryItem> inventory){
		this.articleRepository = articleRepository;
		this.inventory = inventory;
	}

		
	    @RequestMapping(value="/sortiment", method=RequestMethod.GET)
	    public String Willkommen(Model model) {
	    	
	    	
	    	model.addAttribute("articles", articleRepository.findAll());
	    	
		
	        return "sortiment";
	    }
	    
	    
	    @RequestMapping("/detail/{pid}")
		public String detail(@PathVariable("pid") Article article ,Model model) {
			
	    	Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
	    	Quantity quantity = item.map(InventoryItem::getQuantity).orElse(Units.ZERO);
	    	
	    	model.addAttribute("article", article);
	    	
			return "detail";
		}
	    
}