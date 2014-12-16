package factory.controller;

import javax.validation.Valid;

import org.salespointframework.order.Cart;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.quantity.Units;
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

import factory.model.Article;
import factory.model.ArticleRepository;
import factory.model.Customer;
import factory.model.CustomerRepository;
import factory.model.validation.RegistrationForm;


@Controller
@PreAuthorize("hasRole('ROLE_SALESMAN') || hasRole('ROLE_ADMIN')" )
public class Verkaeufercontroller {
	
	private final OrderManager<Order> orderManager;
	private final UserAccountManager userAccountManager;
	private final CustomerRepository customerRepository;
	private final ArticleRepository articleRepository;
	
	@Autowired
	public Verkaeufercontroller(OrderManager<Order> orderManager,
								UserAccountManager userAccountManager,
								CustomerRepository customerRepository,
								ArticleRepository articleRepository) {

		this.orderManager = orderManager;
		this.userAccountManager = userAccountManager;
		this.customerRepository = customerRepository;
		this.articleRepository = articleRepository;
	}	
	
	
	@RequestMapping("/orders")
	public String orders(ModelMap modelMap) {

		modelMap.addAttribute("ordersCompleted", orderManager.find(OrderStatus.COMPLETED));

		return "orders";
	}
	
	@RequestMapping("/customerlist")
	public String customerlist(ModelMap modelMap) {

		modelMap.addAttribute("customerlist", customerRepository.findAll());

		return "customerlist";
	}

	@RequestMapping("/registerNew")
	public String registerNew(@ModelAttribute("registrationForm") @Valid RegistrationForm registrationForm,
			BindingResult result) {

		if (result.hasErrors()) {
			return "register";
		}
		UserAccount userAccount = userAccountManager.create(registrationForm.getName(), registrationForm.getPassword(),
				new Role("ROLE_CUSTOMER"));
		userAccountManager.save(userAccount);
		customerRepository.save(new Customer(userAccount, registrationForm.getFamilyname(), registrationForm.getFirstname(), registrationForm.getAddress()));
		
		return "redirect:/";
	}

	@RequestMapping("/register")
	public String register(ModelMap modelMap) {
		modelMap.addAttribute("registrationForm", new RegistrationForm());
		return "register";
	}

	@RequestMapping(value = "/saleSortiment", method = RequestMethod.GET)
	public String buyForCustomer(Model model){
		
		//Customer customer = customerRepository.findOne(id);
		model.addAttribute("articles", articleRepository.findAll());
		
		return "saleSortiment";
	}
	
	 @RequestMapping(value = "/saleCart", method = RequestMethod.POST)
	    public String addForCustomer(	@RequestParam("customerid") Long id, 
	    								@RequestParam("pid") Article article, 
	    								@RequestParam("number") int number, 
	    								@ModelAttribute Cart cart) {
	    	
			Quantity quantity = Units.of(number);
			cart.addOrUpdateItem(article, quantity);
			
			
			return "redirect:/saleSortiment/{customerid}";
	    }
}
