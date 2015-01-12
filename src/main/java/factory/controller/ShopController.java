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
import factory.model.Employee;
import factory.repository.CustomerRepository;
import factory.repository.EmployeeRepository;

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

	/**
	 * 
	 * @param model Model ist aus der Salespoint api
	 */
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
		
		/**
		 * alle Artikel aus dem Artikelrepository befinden sich aktuellen Artikelkatalog
		 * 
		 * @param model Model ist aus der Salespoint api
		 * @return den Artikelkatalog
		 */
	    @RequestMapping(value="/sortiment", method=RequestMethod.GET)
	    public String Willkommen(Model model) {
	  	    	
	    	model.addAttribute("articles", articleRepository.findAll());

	        return "sortiment";
	    }

	    /**
	     *  Detailansicht der einzelnen Artikel
	     * 
	     * @param article ist im model definiert, Article ist das Produkt, welches in der Firma hergestellt und verkauft wird
	     * @param model Model ist aus der Salespoint api
	     * @return die Detailansicht des einzelnen Artikels
	     */
	    @RequestMapping("/detail/{pid}")
		public String detail(@PathVariable("pid") Article article ,Model model) {
			
	    	Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
	    	Quantity quantity = item.map(InventoryItem::getQuantity).orElse(Units.ZERO);
	    	
	    	model.addAttribute("article", article);
	    	model.addAttribute("quantity", quantity);
	    	
			return "detail";
		}
	    
	    /**
	     * Persönliche Daten vom Kunden können angezeigt werden
	     * 
	     * @param userAccount UserAccount ist aus der Salespoint api
	     * @param model Model ist aus der Salespoint api
	     * @return Persönliche Daten des Kunden
	     */
	   @PreAuthorize("isAuthenticated()")
	   @RequestMapping(value="/datacustomer", method=RequestMethod.GET)
	    public String showCustomerData(@LoggedIn Optional<UserAccount> userAccount, Model model){
	    	
	    	Customer customer = customerRepository.findByUserAccount(userAccount.get());
	    	model.addAttribute("customer", customer);
	    	
	    	return "datacustomer";
	    }
	   
	   /**
	    * Persönliche Daten vom Kunden können editiert werden
	    * 
	    * @param userAccount UserAccount ist aus der Salespoint api
	    * @param model Model ist aus der Salespoint api
	    */
	   @PreAuthorize("isAuthenticated()")
	   @RequestMapping(value="/editdatacustomer", method=RequestMethod.GET)
	   public String editCustomerData(@LoggedIn Optional<UserAccount> userAccount, Model model) {
	    	
		   Customer customer = customerRepository.findByUserAccount(userAccount.get());
	    	model.addAttribute("customer", customer);
	    	
	        return "editdatacustomer";
	    }

	   /**
	    * Persönliche Daten vom Kunden werden gespeichert
	    * 
	    * @param customer ist im model definiert, Customer ist eine Person, die hergestellte Produkte der Firma kaufen kann
	    * @param bindingResult
	    * @param username Benutzername des Kunden
	    * @param familyname Nachname des Kunden
	    * @param firstname Vorname des Kunden
	    * @param address Adresse des Kunden
	    * @return aktualisierte Persönliche Kundendaten werden angezeigt
	    */
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

	   /**
	    * Persönliche Daten vom Mitarbeiter werden gespeichert
	    * 
	    * @param userAccount UserAccount ist aus der Salespoint api
	    * @param model Model ist aus der Salespoint api
	    * @return aktualisierte Persönliche Mitarbeiterdaten werden angezeigt
	    */
	   @PreAuthorize("isAuthenticated()")
	    @RequestMapping(value="/dataemployee", method=RequestMethod.GET)
	    public String showEmployeeData(@LoggedIn Optional<UserAccount> userAccount, Model model){
	    	
	    	Employee employee = employeeRepository.findByUserAccount(userAccount.get());
	    	model.addAttribute("employee", employee);
	    	
	    	return "dataemployee";
	    }

	   
	   /**
	    * Persönliche Daten vom Mitarbeiter können editiert werden
	    * 
	    * @param userAccount UserAccount ist aus der Salespoint api
	    * @param model Model ist aus der Salespoint api
	    */
	   @PreAuthorize("isAuthenticated()")
	    @RequestMapping(value="/editdataemployee", method=RequestMethod.GET)
	    public String editEmployeeData(@LoggedIn Optional<UserAccount> userAccount, Model model) {
	    
		   Employee employee = employeeRepository.findByUserAccount(userAccount.get());
		   model.addAttribute("employee", employee);
	    	
	        return "editdataemployee";
	    }
	   
	   /**
	    * Persönliche Daten vom Mitarbeiter werden gespeichert
	    * 
	    * @param employee ist im model definiert, ein Employee ist eine Person, die in der Firma verschiedene Arbeiten verrichtet
	    * @param bindingResult
	    * @param username Benutzername des Mitarbeiters
	    * @param workplace Arbeitsplatz des Mitarbeiters
	    * @param familyname Nachname des Mitarbeiters
	    * @param firstname Vorname des Mitarbeiters
	    * @param mail Email Adresse des Mitarbeiters
	    * @param address Adresse des Mitarbeiters
	    */
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