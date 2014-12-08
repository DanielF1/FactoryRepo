package factory.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import factory.model.Department;
import factory.model.Employee;
import factory.model.EmployeeRepository;
import factory.model.Location;
import factory.model.LocationRepository;
import factory.model.CustomerRespository;


@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
class AdminController {

	private final LocationRepository locationRepository;
	private final EmployeeRepository employeeRepository;
	private final CustomerRespository customerRepository;
	
	@Autowired
	public AdminController(LocationRepository locationRepository, EmployeeRepository employeeRepository, CustomerRespository customerRepository) {

		this.locationRepository = locationRepository;
		this.employeeRepository = employeeRepository;
		this.customerRepository = customerRepository;
		
	}

		
	 @RequestMapping(value = "/adminLocList", method = RequestMethod.GET)
		public String standortUebersicht(ModelMap modelMap){
	    	
	    	modelMap.addAttribute("locations", locationRepository.findAll());
	    	
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
	    	
	    	List<Employee> emp = new ArrayList<Employee>();
	    	List<Department> dep = new ArrayList<Department>();
	    
	    	Location location = new Location(name, address, city, telefon, mail, emp, dep);
	    	
	    	locationRepository.save(location);
	    	
	    	return "addedLocation";
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
									//@RequestParam("employees") String[] employees,
									//@RequestParam("departments") String[] departments, 
									@RequestParam("id") Long id){
			
			Location location = locationRepository.findOne(id);
			location.setName(name);
			location.setAddress(address);
			location.setCity(city);
			location.setTelefon(telefon);
			location.setMail(mail);
			
//			List<Employee> emp = new ArrayList<Employee>();
//			for(String e : employees){
//				emp.add(new Employee(e, "", "", "", "", ""));
//			}
//			location.setEmployees(emp);
//
//			
//			List<Department> dep = new ArrayList<Department>();
//			for(String s : departments){
//				dep.add(new Department(s));
//			}
//			location.setDepartments(dep);

			locationRepository.save(location);
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
			
			Employee employee = employeeRepository.findOne(id);
			employee.setWorkplace(workplace);
			employee.setName(name);
			employee.setFirstname(firstname);
			employee.setSalary(salary);
			employee.setMail(mail);
			employee.setAddress(address);
			
			employeeRepository.save(employee);
			
			return "redirect:/adminLocList";
			}
		 
		
		//Übersicht aller Arbeiter in allen Standorten
		
	    @RequestMapping(value = "/employeeList", method = RequestMethod.GET)
		public String mitarbeiterUebersicht(ModelMap modelMap){
	    	
	    	modelMap.addAttribute("employees", employeeRepository.findAll());
	    	
			return "employeeList";
		}   
}
