package factory.controller;

import java.util.List;

import javax.validation.Valid;

import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import factory.model.AdminTasksManager;
import factory.model.Article;
import factory.model.ArticleRepository;
import factory.model.Customer;
import factory.model.validation.RegistrationForm;
import factory.repository.CustomerRepository;


@Controller
@PreAuthorize("hasRole('ROLE_SALESMAN') || hasRole('ROLE_ADMIN') ||  hasRole('ROLE_SUPERUSER')" )
public class Verkaeufercontroller {
	
	private final OrderManager<Order> orderManager;
	private final UserAccountManager userAccountManager;
	private final CustomerRepository customerRepository;
	private final ArticleRepository articleRepository;
	private final AdminTasksManager adminTasksManager;
	
	@Autowired
	public Verkaeufercontroller(OrderManager<Order> orderManager,
								UserAccountManager userAccountManager,
								CustomerRepository customerRepository,
								ArticleRepository articleRepository,
								AdminTasksManager adminTasksManager) {

		this.orderManager = orderManager;
		this.userAccountManager = userAccountManager;
		this.customerRepository = customerRepository;
		this.articleRepository = articleRepository;
		this.adminTasksManager = adminTasksManager;
	}	
	
	/**
	 * Abgeschlossene Bestellungen werden angezeigt 
	 * 
	 * @param modelMap ModelMap bereitgestellt von Spring
	 * @return abgeschlossenen Bestellungen einsehbar
	 */
	@RequestMapping("/orders")
	public String orders(ModelMap modelMap) {

		modelMap.addAttribute("ordersCompleted", orderManager.find(OrderStatus.COMPLETED));

		return "orders";
	}

	/**
	 * Anzeigen der Kundenliste
	 * 
	 * @param modelMap ModelMap bereitgestellt von Spring
	 * @return Kundenliste wird angezeigt
	 */
	@RequestMapping("/customerlist")
	public String customerlist(ModelMap modelMap) {

		modelMap.addAttribute("customerlist", customerRepository.findAll());

		return "customerlist";
	}
	
	/**
	 * Registrierung vom Verkäufer von neuen Kunden
	 * 
	 * @param registrationForm Formular für Registrierung und Validierung
	 * @param result Ergebnis der Validierung
	 */
	@RequestMapping("/registerNew")
	public String registerNew(@ModelAttribute("registrationForm") @Valid RegistrationForm registrationForm,
			BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "register";
		}
		UserAccount userAccount = userAccountManager.create(registrationForm.getName(), registrationForm.getPassword(),
				new Role("ROLE_CUSTOMER"));
		userAccountManager.save(userAccount);
		customerRepository.save(new Customer(userAccount, registrationForm.getName(), registrationForm.getPassword(), registrationForm.getFamilyname(), registrationForm.getFirstname(), registrationForm.getAddress()));
		
		model.addAttribute("error_green", "Kundenaccount erstellt");
		
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
	 * Formular für Kundenregistrierung
	 * 
	 * @param modelMap ModelMap bereitgestellt von Spring
	 */
	@RequestMapping("/register")
	public String register(ModelMap modelMap) {
		modelMap.addAttribute("registrationForm", new RegistrationForm());
		return "register";
	}

	/**
	 * Schnellauswahl der Artikel für den Verkäufer 
	 * 
	 * @param model Model bereitgestellt von Spring
	 */
	@RequestMapping(value = "/saleSortiment", method = RequestMethod.GET)
	public String buyForCustomer(Model model){
		
		model.addAttribute("articles", articleRepository.findAll());
		
		return "saleSortiment";
	}
	
	/**
	 * Kundendaten bearbeiten
	 * 
	 * @param id identifier des Kunden
	 * @param model Model bereitgestellt von Spring
	 * @param customer ist im model definiert, Customer ist eine Person, die hergestellte Produkte der Firma kaufen kann
	 */
	@RequestMapping(value="/editCustomer/{id}", method = RequestMethod.GET)
	public String editEmployee(@PathVariable Long id, Model model, Customer customer){
			
		model.addAttribute("customer", customerRepository.findOne(id));
		
		return "editCustomer";
	} 

	/**
	 * Kundendaten speichern
	 * 
	 * @param customer ist im model definiert, Customer ist eine Person, die hergestellte Produkte der Firma kaufen kann
	 * @param bindingResult Ergebnis der Validierung
	 * @param username Benutzername des Kunden
	 * @param familyname Nachname des Kunden
	 * @param firstname Vorname des Kunden
	 * @param address Adresse des Kunden
	 * @return Kundenliste wird aktualisiert(gespeichert) und angezeigt
	 */
	@RequestMapping(value="/editCustomer", method = RequestMethod.POST)
	public String editEmployee(	@Valid Customer customer,
								BindingResult bindingResult,
								@RequestParam("username") String username,
								@RequestParam("familyname") String familyname,
								@RequestParam("firstname") String firstname,
								@RequestParam("address") String address){
		
		if (bindingResult.hasErrors()){
	   		return "editCustomer";
	   	}
		adminTasksManager.editCustomer(username, familyname, firstname, address);
		
		return "redirect:/customerlist";
		}
	
	@RequestMapping(value="/deleteCustomer/{id}", method = RequestMethod.POST)
	public String deleteCustomer(@PathVariable Long id, Model model){
		
		adminTasksManager.deleteCustomer(id);
		
		model.addAttribute("error_green", "Kundenaccount gelöscht");
		model.addAttribute("customer", customerRepository.findOne(id));
		
		return "customerlist";
		}
}
