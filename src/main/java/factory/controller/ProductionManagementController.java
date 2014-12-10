package factory.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import factory.model.DepartmentRepository;
import factory.model.Location;
import factory.model.LocationRepository;

@Controller
public class ProductionManagementController {
	
	private final LocationRepository locationRepository;
	private final DepartmentRepository departmentrepository;
	
	@Autowired
	public ProductionManagementController(LocationRepository locationRepository, DepartmentRepository departmentRepository) {
		this.locationRepository = locationRepository;
		this.departmentrepository = departmentRepository;
	}

	@RequestMapping(value = "/locationwithwine", method = RequestMethod.GET)
	public String specification(ModelMap modelMap){

		String Searchterm = "Produktion";
		
		List<Location> resultList = new ArrayList<>();
		for(Location location : locationRepository.findAll())
		{
			if(location.getDepartments().contains(Searchterm)){resultList.add(location);}
		}
		
		modelMap.addAttribute("locations", resultList);
		
		
		return "locationwithwine";
	}
	
	
	// if overflow
	
}