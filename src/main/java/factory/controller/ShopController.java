package factory.controller;


import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.quantity.Units;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import factory.model.AdminTasksManager;
import factory.model.Article;
import factory.model.ArticleRepository;
import factory.model.Customer;
import factory.model.CustomerRepository;
import factory.model.Employee;
import factory.model.EmployeeRepository;

@Controller
public class ShopController {
	
	private final Inventory<InventoryItem> inventory;
	private final ArticleRepository articleRepository;
	private final CustomerRepository customerRepository;
	private final EmployeeRepository employeeRepository;
	private final UserAccountManager userAccountManager;
	private final AdminTasksManager adminTasksManager;
	
	@Autowired
	public ShopController(	ArticleRepository articleRepository, 
							Inventory<InventoryItem> inventory, 
							CustomerRepository customerRepository,
							EmployeeRepository employeeRepository,
							UserAccountManager userAccountManager,
							AdminTasksManager adminTasksManager){
		this.articleRepository = articleRepository;
		this.inventory = inventory;
		this.customerRepository = customerRepository;
		this.employeeRepository = employeeRepository;
		this.userAccountManager = userAccountManager;
		this.adminTasksManager = adminTasksManager;
	}

	
		@RequestMapping({ "/", "/index"})
		public String start(Model model) {
				
			List<Article> list1 = (List<Article>) articleRepository.findAll();
			Article bestseller = list1.get(2);
			Article newbie1 = list1.get(4);
			Article newbie2 = list1.get(5);
			
			model.addAttribute("article", bestseller);
			model.addAttribute("newbie1", newbie1);
			model.addAttribute("newbie2", newbie2);
			
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
	   
	   @PreAuthorize("isAuthenticated()")
	   @RequestMapping(value="/datacustomer", method=RequestMethod.GET)
	    public String showCustomerData(@LoggedIn Optional<UserAccount> userAccount, Model model){
	    	
	    	Customer customer = customerRepository.findByUserAccount(userAccount.get());
	    	model.addAttribute("customer", customer);
	    	
	    	return "datacustomer";
	    }
	    
	   @PreAuthorize("isAuthenticated()")
	   @RequestMapping(value="/editdatacustomer", method=RequestMethod.GET)
	   public String editCustomerData(@LoggedIn Optional<UserAccount> userAccount, Model model) {
	    	
		   Customer customer = customerRepository.findByUserAccount(userAccount.get());
	    	model.addAttribute("customer", customer);
	    	
	        return "editdatacustomer";
	    }
	    
	   @PreAuthorize("isAuthenticated()")
	   @RequestMapping(value="/editdatacustomer", method=RequestMethod.POST)
	   public String editCustomerData(	@Valid Customer customer,
			   							BindingResult bindingResult,
										@RequestParam("username") String username,
										@RequestParam("familyname") String familyname,
										@RequestParam("firstname") String firstname,
										@RequestParam("address") String address){
			
		   if (bindingResult.hasErrors()){
			   return "editdatacustomer";
		   }
		   adminTasksManager.editCustomerData(username, familyname, firstname, address);
	    	
		   return "redirect:/datacustomer";
	    }
	    
	   @PreAuthorize("isAuthenticated()")
	    @RequestMapping(value="/dataemployee", method=RequestMethod.GET)
	    public String showEmployeeData(@LoggedIn Optional<UserAccount> userAccount, Model model){
	    	
	    	Employee employee = employeeRepository.findByUserAccount(userAccount.get());
	    	model.addAttribute("employee", employee);
	    	
	    	return "dataemployee";
	    }
	    
	   @PreAuthorize("isAuthenticated()")
	    @RequestMapping(value="/editdataemployee", method=RequestMethod.GET)
	    public String editEmployeeData(@LoggedIn Optional<UserAccount> userAccount, Model model) {
	    
		   Employee employee = employeeRepository.findByUserAccount(userAccount.get());
		   model.addAttribute("employee", employee);
	    	
	        return "editdataemployee";
	    }
	    
	   @PreAuthorize("isAuthenticated()")
	    @RequestMapping(value="/editdataemployee", method=RequestMethod.POST)
	    public String editEmployeeData(	@Valid Employee employee,
										BindingResult bindingResult,
										@RequestParam("username") String username,
										@RequestParam("workplace") String workplace,
										@RequestParam("familyname") String familyname,
										@RequestParam("firstname") String firstname,
										@RequestParam("mail") String mail,
										@RequestParam("address") String address){

		   	if (bindingResult.hasErrors()){
		   		return "editdataemployee";
		   	}
	    	adminTasksManager.editEmployeeData(username, workplace, familyname, firstname, mail, address);
	    	
	    	return "redirect:/dataemployee";
	    }
}