package factory.controller;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import factory.model.Location;
import factory.model.ProductionManagement;
import factory.model.Transport;

@Controller
public class ProductionManagementController {

	@RequestMapping(value = "/locationwithwine", method = RequestMethod.GET)
	public String specification(ModelMap model)		 {

		model.addAttribute("locations", Location.getLocationsListWithProductionManagement());
		return "locationwithwine";
	}
	
	
	// if overflow
	
}