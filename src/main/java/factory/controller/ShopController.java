package factory.controller;


import java.util.List;
import java.util.Optional;

import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.quantity.Units;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import factory.model.Article;
import factory.model.ArticleRepository;
import factory.model.Customer;
import factory.model.CustomerRepository;

@Controller
@SessionAttributes("cart")
public class ShopController {
	
	private final Inventory<InventoryItem> inventory;
	private final ArticleRepository articleRepository;
	private final CustomerRepository customerRepository;
	
	@Autowired
	public ShopController(ArticleRepository articleRepository, Inventory<InventoryItem> inventory, CustomerRepository customerRepository){
		this.articleRepository = articleRepository;
		this.inventory = inventory;
		this.customerRepository = customerRepository;
	}

	
		@RequestMapping({ "/", "/index"})
		public String start(Model model) {
				
			List<Article> list1 = (List<Article>) articleRepository.findAll();
			Article article = list1.get(2);
			
			model.addAttribute("article", article);
			
			return "index";
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
	    	model.addAttribute("quantity", quantity);
	    	
			return "detail";
		}
	    
	   @RequestMapping(value="/datacustomer", method=RequestMethod.GET)
	    public String showData(@LoggedIn Optional<UserAccount> userAccount, Model model){
	    	
	    	Customer customer = customerRepository.findByUserAccount(userAccount.get());
	    	model.addAttribute("customer", customer);
	    	
	    	return "datacustomer";
	    }
	    
	    @RequestMapping(value="/editdatacustomer/{id}", method=RequestMethod.GET)
	    public String editData(@PathVariable("id") Long id, Model model) {
	    	
	    	Customer customer = customerRepository.findOne(id);
	    	model.addAttribute("customer", customer);
	    	
	        return "editdatacustomer";
	    }
	    
	    @RequestMapping(value="/editdatacustomer", method=RequestMethod.POST)
	    public String saveDate(	@RequestParam("id") Long id,
//	    						@RequestParam("username") String username,
	    						@RequestParam("familyname") String familyname,
	    						@RequestParam("firstname") String firstname,
	    						@RequestParam("address") String address){
			
	    	Customer customer = customerRepository.findOne(id);
	    	customer.setFamilyname(familyname);
	    	customer.setFirstname(firstname);
	    	customer.setAddress(address);
	    	
	    	customerRepository.save(customer);
	    	
	    	return "redirect:/datacustomer";
	    }
	    
	    
}