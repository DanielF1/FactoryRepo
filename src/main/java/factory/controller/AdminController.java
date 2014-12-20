package factory.controller;

import java.util.ArrayList;
import java.util.List;

import org.salespointframework.order.Cart;
import org.salespointframework.useraccount.UserAccountManager;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import factory.model.AdminTasksManager;
import factory.model.DepartmentRepository;
import factory.model.EmployeeRepository;
import factory.model.Location;
import factory.model.LocationRepository;


@Controller
@PreAuthorize("hasRole('ROLE_ADMIN') ||  hasRole('ROLE_SUPERUSER')")
class AdminController {

	private final LocationRepository locationRepository;
	private final EmployeeRepository employeeRepository;
	private final DepartmentRepository departmentRepository;
	private final UserAccountManager userAccountManager;
	private final AdminTasksManager adminTasksManager;
	
	@Autowired
	public AdminController(	LocationRepository locationRepository, 
							EmployeeRepository employeeRepository, 
							DepartmentRepository departmentRepository, 
							UserAccountManager userAccountManager,
							AdminTasksManager adminTasksManager) {

		this.locationRepository = locationRepository;
		this.employeeRepository = employeeRepository;
		this.departmentRepository = departmentRepository;
		this.userAccountManager = userAccountManager;
		this.adminTasksManager = adminTasksManager;
		
	}

		
	 @RequestMapping(value = "/adminLocList", method = RequestMethod.GET)
		public String standortUebersicht(ModelMap modelMap){
	    	
	    	modelMap.addAttribute("locations", locationRepository.findAll());
	    	System.out.println(locationRepository.findAll());
			return "adminLocList";
		}
	
	 
	  //Search START
		@RequestMapping(value = "/adminLocList", method = RequestMethod.POST)
		public String goToFilteredLocations(@RequestParam("searchTerm") String searchTerm, RedirectAttributes redirectAttrs)
		{		
			redirectAttrs.addAttribute("term", searchTerm);
			return "redirect:/adminLocList/{term}";
		}

		//show result
		@RequestMapping(value = "/adminLocList/{term}")
		public String showFilteredLocations(@PathVariable("term") String searchTerm, ModelMap modelMap, RedirectAttributes redirectAttrs)
		{		
			List<Location> resultList = new ArrayList<>();
			for(Location location : locationRepository.findAll())
			{
				if(location.getName().equals(searchTerm)){resultList.add(location);}
				if(location.getAddress().equals(searchTerm)){resultList.add(location);}
				if(location.getCity().contains(searchTerm)){resultList.add(location);}
				if(location.getTelefon().equals(searchTerm)){resultList.add(location);}
				if(location.getMail().equals(searchTerm)){resultList.add(location);}
				if(location.getDepartments().contains(searchTerm)){resultList.add(location);}
			}
			
			modelMap.addAttribute("locations", resultList);
			
			return "adminLocList";
		}
	//Search END

				
		@RequestMapping(value="/addLocation", method=RequestMethod.GET)
	    public String addLocation() {
	        return "addLocation";
	    }
	
	    @RequestMapping(value="/addedLocation", method=RequestMethod.POST)
	    public String Standortausgabe(	@RequestParam ("name") String name,
	    								@RequestParam ("address") String address,
	    								@RequestParam ("city") String city,
	    								@RequestParam ("telefon") String telefon,
	    								@RequestParam ("mail") String mail) {
	    	
	    	adminTasksManager.addLocation(name, address, city, telefon, mail);
	    		    	
	    	return "redirect:/adminLocList";
	    }
	    
	    @RequestMapping(value="/editLocation/{id}", method = RequestMethod.GET)
		public String editLocations(@PathVariable Long id, Model model){
			
			model.addAttribute("location" ,locationRepository.findOne(id));
			
			return "editLocation";
		}
				
		@RequestMapping(value="/editLocation", method = RequestMethod.POST)
		public String editLocation(	@RequestParam("name") String name,
									@RequestParam("address") String address,
									@RequestParam("city") String city,
									@RequestParam("telefon")String telefon,
									@RequestParam("mail") String mail,
									@RequestParam("id") Long id){
			
			adminTasksManager.editLocation(name, address, city, telefon, mail, id);
			
			return "redirect:/adminLocList";
		}

		
		//Mitarbeiterbearbeitung von 1 Standort
		
		 @RequestMapping(value="/employees/{id}", method = RequestMethod.GET)
		 public String editEmployees(@PathVariable Long id, Model model){	
			
			model.addAttribute("location", locationRepository.findOne(id));
			 
			return "employees";
			}
		
		@RequestMapping(value="/editEmployee/{id}", method = RequestMethod.GET)
		public String editEmployee(@PathVariable Long id, Model model){
				
			model.addAttribute("employee", employeeRepository.findOne(id));
			
			return "editEmployee";
		} 
		 
		@RequestMapping(value="/editEmployee", method = RequestMethod.POST)
		public String editEmployee(	@RequestParam("id") Long id,
									@RequestParam("workplace") String workplace,
									@RequestParam("name") String name,
									@RequestParam("firstname") String firstname,
									@RequestParam("salary") String salary,
									@RequestParam("mail") String mail,
									@RequestParam("address") String address
									){
			
			adminTasksManager.editEmployee(id, workplace, name, firstname, salary, mail, address);
			
			return "redirect:/adminLocList";
			}
		 
		@RequestMapping(value="/addEmployee/{id}", method=RequestMethod.GET)
	    public String addEmployee(@PathVariable Long id, Model model) {
			
			model.addAttribute("id", id);
	        
			return "addEmployee";
	    }
		
		@RequestMapping(value="/addedEmployee", method=RequestMethod.POST)
	    public String addedEmployee(	@RequestParam ("username") String username,
	    								@RequestParam ("password") String password,
										@RequestParam ("id") Long id,
	    								@RequestParam ("workplace") String workplace,
	    								@RequestParam ("name") String name,
	    								@RequestParam ("firstname") String firstname,
	    								@RequestParam ("salary") String salary,
	    								@RequestParam ("mail") String mail,
	    								@RequestParam ("address") String address) {
	    	
			adminTasksManager.addEmployee(username, password, id, workplace, name, firstname, salary, mail, address);
			
	    	return "redirect:/adminLocList";
	    }
	
		@RequestMapping(value="/editDepartments/{id}", method = RequestMethod.GET)
		public String editDepartments(@PathVariable Long id, Model model){	
			
			model.addAttribute("location", locationRepository.findOne(id));
			
			return "editDepartments";
			}
	
		@RequestMapping(value="/editOneDep/{id}", method = RequestMethod.GET)
		public String editOneDepartment(@PathVariable Long id, Model model){	
			
			model.addAttribute("dep", departmentRepository.findOne(id));
			
			return "editOneDep";
		}
		
//		@RequestMapping(value="/editDepartment", method = RequestMethod.POST)
//		public String editDep(	@RequestParam("id") Long id,
//								@RequestParam("quantity") double quantity,
//								@RequestParam("capacity") double capacity){
//			
//			adminTasksManager.editDepartment(id, quantity, capacity);
//			
//			return "redirect:/adminLocList";
//			}
		
		@RequestMapping(value="/addDepartment", method=RequestMethod.POST)
		public String addedDepartment(	@RequestParam ("id") Long id,
		   								@RequestParam ("sort") String sort) {
						
			String result = adminTasksManager.addDepartment(id, sort);
				
			return result;
		}

		//Übersicht aller Arbeiter in allen Standorten
		
	    @RequestMapping(value = "/employeeList", method = RequestMethod.GET)
		public String employeeList(ModelMap modelMap){
	    	
	    	modelMap.addAttribute("employees", employeeRepository.findAll());
	    	
			return "employeeList";
		}
  
	//
	//Funktion um Mitarbeiter zu entlassen ist unvollständig und muss erweitert werden,
	//da nicht nur aus dem EmployeeRepo sondern auch aus der Liste gelöscht werden muss
	//
//	    @RequestMapping(value = "/dismissEmployee/{id}", method = RequestMethod.GET)
//		public String dismissEmployee(@PathVariable Long id){
//	    	
//	    	adminTasksManager.dismissEmployee(id);
//	    	
//			return "employeeList";
//		}
}
