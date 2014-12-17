package factory.controller;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import factory.model.Department;
import factory.model.Location;
import factory.model.LocationManagement;
import factory.model.LocationRepository;
import factory.model.TransportRepository;

@Controller
public class WineGrowerController {

	
	private Date date;
	private final LocationRepository locationRepository;
	private final TransportRepository transportRepository;
	private final LocationManagement locationManagement;
	private static final int DAY_IN_MILLIS = 24 * 3600 * 1000;
	private static final int VOLUME_HEKTOLITERS_IN_TWO_DAYS = 24;

	@Autowired
	public WineGrowerController(LocationRepository locationRepository, TransportRepository transportRepository, LocationManagement locationManagement) {
		this.locationRepository = locationRepository;
		this.transportRepository = transportRepository;
		this.locationManagement = locationManagement;
	}

	
	
	
	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String Show(ModelMap model,
			@RequestParam(required = false) final String error) {
		
			String term = "Produktion";
		
			List<Location> resultList = new ArrayList<>();
			for(Location location : locationRepository.findAll())
			{
				for(Department dep : location.getDepartments()){
					if(dep.getName().contains(term)){resultList.add(location);}
				}
//				if(location.getDepartments().contains(Searchterm)){resultList.add(location);}
			}
		model.addAttribute("locations", resultList);
		
			if (error != null)
				switch (error) {
				case "date":
					model.addAttribute("error",	"Das Datum muss folgendes Format haben : YYYY-MM-DD");
				break;
				}
			
		return "lieferungForm";
	}

	@RequestMapping(value = "/LF_result", method = RequestMethod.POST)
	public String specification(ModelMap modelMap,
			@RequestParam("quantity") double quantity,
			@RequestParam("date") String date, @RequestParam("id")  String location) {
		
		Location loc = locationRepository.findByName(location);
		Long id = loc.getId();
		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		try {
			this.date = formatter.parse(date);
		} catch (ParseException e) {
			return "redirect:form?error=date";
		}
		// this.place = place;

		// deliver wine!
//		LocationManagement locationmanagement;
//		locationmanagement.deliverWine(quantity, this.date);
		
		
		locationManagement.actualizationCapacity(this.date);
		locationManagement.deliverWine(quantity, this.date , id);
		
		modelMap.addAttribute("quantity", quantity);
		modelMap.addAttribute("date", this.date);
		modelMap.addAttribute("location", location);
		return "LF_result";
	}

	
	
	
}