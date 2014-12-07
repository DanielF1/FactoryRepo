package factory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import factory.model.Location;
import factory.model.Locationmanagement;
import factory.model.Transport;

@Controller
public class WineGrowerController {

	private int quantity;
	private Date date;
	private String place;

	private final Locationmanagement locationmanagement;

	@Autowired
	public WineGrowerController(Locationmanagement locationmanagement) {
		this.locationmanagement = locationmanagement;
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String Show(ModelMap model,
			@RequestParam(required = false) final String error) {
		model.addAttribute("locations",	Location.getLocationsListWithProductionManagement());
		if (error != null)
			switch (error) {
			case "date":
				model.addAttribute("error",	"Das Datum muss folgendes Format haben : YYYY-MM-DD");
				break;
			}
		return "lieferungForm";
	}

	@RequestMapping(value = "/LF_result", method = RequestMethod.POST)
	public String specification(ModelMap model,
			@RequestParam("quantity") int quantity,
			@RequestParam("date") String date, @RequestParam("place") long place) {
		this.quantity = quantity;
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		try {
			this.date = formatter.parse(date);
		} catch (ParseException e) {
			return "redirect:form?error=date";
		}
		// this.place = place;

		// deliver wine!
		Location loc = Location.getLocationById(place);
		Transport transport = loc.deliverWine(quantity, this.date);

		loc.Save();

		model.addAttribute("quantity", this.quantity);
		model.addAttribute("date", this.date);
		model.addAttribute("location", loc);
		model.addAttribute("transport", transport);
		return "LF_result";
	}

}
