package factory.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import factory.model.Location;
import factory.model.Locationmanagement;

@Controller
public class ManagementController {
	
	private final Locationmanagement locationmanagement;
	
		@Autowired
		public ManagementController(Locationmanagement locationmanagement) {
			this.locationmanagement = locationmanagement;
		}


		@RequestMapping(value="/eingabe", method=RequestMethod.GET)
	    public String Anzeige() {
	        return "eingabe";
	    }

		
	    @RequestMapping(value="/ausgabe", method=RequestMethod.POST)
	    public String Standortausgabe(	@RequestParam ("name") String name,
	    								@RequestParam ("address") String address,
	    								@RequestParam ("city") String city,
	    								@RequestParam ("telefon") String telefon,
	    								@RequestParam ("mail") String mail,
	    								@RequestParam ("employees") List<Employee> employees,
	    								@RequestParam ("departments") List<Department> departments) {
	        
	    	Location location = new Location(name, address, city, telefon, mail, employees, departments);
	    	
	    	locationmanagement.save(location);
	    	
	    	return "ausgabe";
	    }
	    
	    @RequestMapping(value = "/loeschen", method = RequestMethod.GET)
		public String standortLöschen(ModelMap modelMap){
	    	
	    	modelMap.addAttribute("locations", locationmanagement.findAll());
	    	
			return "loeschen";
		}
	    
	    @RequestMapping(value = "/ausgabe2", method = RequestMethod.POST)
		public String löschen(@RequestParam ("name") String name){
	    	
	    	for(Location location : locationmanagement.findAll())
			{
				if(location.getName().equals(name)){
					locationmanagement.delete(location);
				}
			}
	    	
	    	return "ausgabe2";
		}
	    
	    @RequestMapping(value = "/loclist", method = RequestMethod.GET)
		public String standortUebersicht(ModelMap modelMap){
	    	
	    	modelMap.addAttribute("locations", locationmanagement.findAll());
	    	
			return "loclist";
		}
	    
	  //Search START
		@RequestMapping(value = "/loclist", method = RequestMethod.POST)
		public String goToFilteredLocations(@RequestParam("searchTerm") String searchTerm, RedirectAttributes redirectAttrs)
		{		
			redirectAttrs.addAttribute("term", searchTerm);
			return "redirect:/loclist/{term}";
		}

		
		//show result
		@RequestMapping(value = "/loclist/{term}")
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
			
			return "loclist";
		}
	//Search END
		
		//für Liste von Locations mit Productionmanagement
//		public List<Location> getLocationWithWine(){
//			List<Location> result = new ArrayList<Location>();
//			for(Location location : locations)
//				if (location.getWineDepartment()!= null)
//					result.add(location);
//			return result;
//		}
		
		
}
