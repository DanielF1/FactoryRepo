package factory.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import factory.model.Location;
import factory.model.LocationRepository;

@Controller
public class ManagementController {
	
	private final LocationRepository locationRepository;
	
		@Autowired
		public ManagementController(LocationRepository locationRepository) {
			this.locationRepository = locationRepository;
		}
	    
	    @RequestMapping(value = "/loclist", method = RequestMethod.GET)
		public String standortUebersicht(ModelMap modelMap){
	    	
	    	modelMap.addAttribute("locations", locationRepository.findAll());
	    	
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
