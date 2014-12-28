package factory.controller;

import javax.validation.Valid;

import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountIdentifier;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import factory.model.AdminTasksManager;
import factory.model.DepartmentRepository;
import factory.model.Employee;
import factory.model.EmployeeRepository;
import factory.model.Location;
import factory.model.LocationRepository;


@Controller
@PreAuthorize("hasRole('ROLE_ADMIN') ||  hasRole('ROLE_SUPERUSER')")
class AdminController {

	private final LocationRepository locationRepository;
	private final EmployeeRepository employeeRepository;
	private final DepartmentRepository departmentRepository;
	private final AdminTasksManager adminTasksManager;
	private final UserAccountManager userAccountManager;
	
	@Autowired
	public AdminController(	LocationRepository locationRepository, 
							EmployeeRepository employeeRepository, 
							DepartmentRepository departmentRepository, 
							AdminTasksManager adminTasksManager,
							UserAccountManager userAccountManager) {

		this.locationRepository = locationRepository;
		this.employeeRepository = employeeRepository;
		this.departmentRepository = departmentRepository;
		this.adminTasksManager = adminTasksManager;
		this.userAccountManager = userAccountManager;
	}

		
	 @RequestMapping(value = "/adminLocList", method = RequestMethod.GET)
		public String standortUebersicht(ModelMap modelMap){
	    	
	    	modelMap.addAttribute("locations", locationRepository.findAll());
	    	System.out.println(locationRepository.findAll());
			return "adminLocList";
		}
	
	 
//	  //Search START
//		@RequestMapping(value = "/adminLocList", method = RequestMethod.POST)
//		public String goToFilteredLocations(@RequestParam("searchTerm") String searchTerm, RedirectAttributes redirectAttrs)
//		{		
//			redirectAttrs.addAttribute("term", searchTerm);
//			return "redirect:/adminLocList/{term}";
//		}
//
//		//show result
//		@RequestMapping(value = "/adminLocList/{term}")
//		public String showFilteredLocations(@PathVariable("term") String searchTerm, ModelMap modelMap, RedirectAttributes redirectAttrs)
//		{		
//			List<Location> resultList = new ArrayList<>();
//			for(Location location : locationRepository.findAll())
//			{
//				if(location.getName().equals(searchTerm)){resultList.add(location);}
//				if(location.getAddress().equals(searchTerm)){resultList.add(location);}
//				if(location.getCity().contains(searchTerm)){resultList.add(location);}
//				if(location.getTelefon().equals(searchTerm)){resultList.add(location);}
//				if(location.getMail().equals(searchTerm)){resultList.add(location);}
//				if(location.getDepartments().contains(searchTerm)){resultList.add(location);}
//			}
//			
//			modelMap.addAttribute("locations", resultList);
//			
//			return "adminLocList";
//		}
//	//Search END

				
		@RequestMapping(value="/addLocation", method=RequestMethod.GET)
	    public String addLocation(Location location) {
	        return "addLocation";
	    }
	
	    @RequestMapping(value="/addLocation", method=RequestMethod.POST)
	    public String Standortausgabe(	@Valid Location location,
	    								BindingResult bindingResult,
	    								@RequestParam ("name") String name,
	    								@RequestParam ("address") String address,
	    								@RequestParam ("city") String city,
	    								@RequestParam ("telefon") String telefon,
	    								@RequestParam ("mail") String mail) {
	    	
	    	if (bindingResult.hasErrors()) {
	            return "addLocation";
	        }
	    	
	    	adminTasksManager.addLocation(name, address, city, telefon, mail);
	    		    	
	    	return "redirect:/adminLocList";
	    }
	    
	    @RequestMapping(value="/editLocation/{id}", method = RequestMethod.GET)
		public String editLocations(@PathVariable Long id, Model model, Location location){
			
			model.addAttribute("location" ,locationRepository.findOne(id));
			
			return "editLocation";
		}
				
		@RequestMapping(value="/editLocation", method = RequestMethod.POST)
		public String editLocation(	@Valid Location location,
									BindingResult bindingResult,
									@RequestParam("name") String name,
									@RequestParam("address") String address,
									@RequestParam("city") String city,
									@RequestParam("telefon")String telefon,
									@RequestParam("mail") String mail,
									@RequestParam("id") Long id){
			
			if (bindingResult.hasErrors()) {
	            return "editLocation";
	        }
			adminTasksManager.editLocation(name, address, city, telefon, mail, id);
			
			return "redirect:/adminLocList";
		}

		@RequestMapping(value="/deleteLocation/{id}", method = RequestMethod.GET)
		public String deleteLocation(@PathVariable Long id, Model model){
			
			adminTasksManager.deleteLocation(id);
			
			return "redirect:/adminLocList";
		}
		
		
		//Mitarbeiterbearbeitung von 1 Standort
		
		 @RequestMapping(value="/employees/{id}", method = RequestMethod.GET)
		 public String editEmployees(@PathVariable Long id, Model model){	
			
			model.addAttribute("location", locationRepository.findOne(id));
			 
			return "employees";
			}
		
		@RequestMapping(value="/editEmployee/{id}", method = RequestMethod.GET)
		public String editEmployee(@PathVariable Long id, Model model, Employee employee){
				
			model.addAttribute("employee", employeeRepository.findOne(id));
			
			return "editEmployee";
		} 
		 
		@RequestMapping(value="/editEmployee", method = RequestMethod.POST)
		public String editEmployee(	@Valid Employee employee,
									BindingResult bindingResult,
									@RequestParam("username") String username,
									@RequestParam("password") String password,
									@RequestParam("workplace") String workplace,
									@RequestParam("familyname") String familyname,
									@RequestParam("firstname") String firstname,
									@RequestParam("salary") String salary,
									@RequestParam("mail") String mail,
									@RequestParam("address") String address
									){
			
			if (bindingResult.hasErrors()) {
	            return "editEmployee";
	        }
			adminTasksManager.editEmployee(username, familyname, firstname, salary, mail, address);
			
			return "redirect:/employeeList";
			}
		 
		@RequestMapping(value="/addEmployee", method=RequestMethod.GET)
	    public String addEmployee( Model model, Employee employee) {
			
			model.addAttribute("locations", locationRepository.findAll());
	        
			return "addEmployee";
	    }
		
		@RequestMapping(value="/addEmployee", method=RequestMethod.POST)
	    public String addedEmployee(	@Valid Employee employee,
	    								BindingResult bindingResult,
	    								@RequestParam ("username") String username,
	    								@RequestParam ("password") String password,
										@RequestParam ("location") String location,
	    								@RequestParam ("workplace") String workplace,
	    								@RequestParam ("familyname") String familyname,
	    								@RequestParam ("firstname") String firstname,
	    								@RequestParam ("salary") String salary,
	    								@RequestParam ("mail") String mail,
	    								@RequestParam ("address") String address,
	    								Model model) {
	    	
			model.addAttribute("locations", locationRepository.findAll());
	
			if (bindingResult.hasErrors()) {
	            return "addEmployee";
	        }
			adminTasksManager.addEmployee(username, password, location, workplace, familyname, firstname, salary, mail, address);
			
	    	return "redirect:/employeeList";
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
  
	    @RequestMapping(value = "/dismissEmployee/{id}", method = RequestMethod.GET)
		public String dismissEmployee(@PathVariable Long id){
	    	
	    	adminTasksManager.dismissEmployee(id);
	    	
			return "redirect:/employeeList";
		}
}
