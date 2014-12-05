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
import factory.model.Location;
import factory.model.Locationmanagement;
import factory.model.NormalUserRepository;

@Controller
@PreAuthorize("hasRole('ROLE_BOSS')")
class AdminController {

	private final Locationmanagement locationmanagement;
	private final NormalUserRepository customerRepository;

	
	@Autowired
	public AdminController(Locationmanagement locationmanagement, NormalUserRepository customerRepository) {

		this.locationmanagement = locationmanagement;
		this.customerRepository = customerRepository;
	}

	@RequestMapping("/customers")
	public String customers(ModelMap modelMap) {

		modelMap.addAttribute("customerList", customerRepository.findAll());

		return "customers";	
	}
	
	 @RequestMapping(value = "/adminloclist", method = RequestMethod.GET)
		public String standortUebersicht(ModelMap modelMap){
	    	
	    	modelMap.addAttribute("locations", locationmanagement.findAll());
	    	
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
			for(Location location : locationmanagement.findAll())
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
		
		@RequestMapping(value="/edit/{id}", method = RequestMethod.GET)
		public String editLocations(@PathVariable Long id, Model model){
			
			model.addAttribute("location" ,locationmanagement.findOne(id));
			return "edit";
		}
		
		@RequestMapping(value="/edit", method = RequestMethod.POST)
		public String editLocation(	@RequestParam("telefon")String telefon,
									@RequestParam("mail") String mail,
									@RequestParam("departments") String[] departments, 
									@RequestParam("id") Long id){
			
			Location location = locationmanagement.findOne(id);
			location.setTelefon(telefon);
			location.setMail(mail);
			
			List<Department> dep = new ArrayList<Department>();
			for(String s : departments){
				dep.add(new Department(s));
			}
			location.setDepartments(dep);

			locationmanagement.save(location);
			return "redirect:/adminloclist";
		}
	
	
	
	
	
	
}
