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

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
class AdminController {

	private final LocationRepository locationRepository;
	private final EmployeeRepository employeeRepository;
	
	@Autowired
	public AdminController(LocationRepository locationRepository, EmployeeRepository employeeRepository) {

		this.locationRepository = locationRepository;
		this.employeeRepository = employeeRepository;
	}

		
	 @RequestMapping(value = "/adminloclist", method = RequestMethod.GET)
		public String standortUebersicht(ModelMap modelMap){
	    	
	    	modelMap.addAttribute("locations", locationRepository.findAll());
	    	
			return "adminloclist";
		}
	
	 
	  //Search START
		@RequestMapping(value = "/adminloclist", method = RequestMethod.POST)
		public String goToFilteredLocations(@RequestParam("searchTerm") String searchTerm, RedirectAttributes redirectAttrs)
		{		
			redirectAttrs.addAttribute("term", searchTerm);
			return "redirect:/adminloclist/{term}";
		}

		//show result
		@RequestMapping(value = "/adminloclist/{term}")
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
			
			return "adminloclist";
		}
	//Search END

				
		@RequestMapping(value="/eingabe", method=RequestMethod.GET)
	    public String Anzeige() {
	        return "eingabe";
	    }
	
	    @RequestMapping(value="/ausgabe", method=RequestMethod.POST)
	    public String Standortausgabe(	@RequestParam ("name") String name,
	    								@RequestParam ("address") String address,
	    								@RequestParam ("city") String city,
	    								@RequestParam ("telefon") String telefon,
	    								@RequestParam ("mail") String mail) {
	    	
	    	List<Employee> emp = new ArrayList<Employee>();
	    	List<Department> dep = new ArrayList<Department>();
	    
	    	Location location = new Location(name, address, city, telefon, mail, emp, dep);
	    	
	    	locationRepository.save(location);
	    	
	    	return "ausgabe";
	    }
	    
	    //LÖSCHEN EINES STANDORTES (funzt noch nicht :D )
	    
//	    @RequestMapping(value="/delete/{id}", method = RequestMethod.POST)
//		public String deleteLocation(@PathVariable Long id, Model model){
//			
//			Location location = locationmanagement.findOne(id);
//			
//			locationmanagement.delete(location);
//			
//			model.addAttribute("locations", locationmanagement.findAll());
//			
//			return "redirect:/adminloclist";
//		}
	    
	    @RequestMapping(value="/editlocation/{id}", method = RequestMethod.GET)
		public String editLocations(@PathVariable Long id, Model model){
			
			model.addAttribute("location" ,locationRepository.findOne(id));
			
			return "editlocation";
		}
				
		@RequestMapping(value="/editlocation", method = RequestMethod.POST)
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
			return "redirect:/adminloclist";
		}

		
		//Mitarbeiterbearbeitung von 1 Standort
		
		 @RequestMapping(value="/employees/{id}", method = RequestMethod.GET)
		 public String editEmployees(@PathVariable Long id, Model model){	
			
			model.addAttribute("location", locationRepository.findOne(id));
			 
			return "employees";
			}
		
		 
		 
		@RequestMapping(value="/editemployee/{id}", method = RequestMethod.GET)
		public String editEmployee(@PathVariable Long id, Model model){
				
			model.addAttribute("employee", employeeRepository.findOne(id));
			
			return "editemployee";
		} 
		 
		@RequestMapping(value="/editemployee", method = RequestMethod.POST)
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
			
			return "redirect:/adminloclist";
			}
		 
		
		//Übersicht aller Arbeiter in allen Standorten
		
	    @RequestMapping(value = "/employeelist", method = RequestMethod.GET)
		public String mitarbeiterUebersicht(ModelMap modelMap){
	    	
	    	modelMap.addAttribute("employees", employeeRepository.findAll());
	    	
			return "employeelist";
		}   
}
