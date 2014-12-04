package factory.controller;

//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;

//@Controller
//public class WineGrowerController {
//
//	private int quantity;
//	private Date date;
//	private String place;
//
//	@RequestMapping(value = "/form", method = RequestMethod.GET)
//	public String Show(ModelMap model, @RequestParam("error") String error) {
//		model.addAttribute("locations", StandortDataInitializer.getInstance().getLocationManagement().getLocationWithWine());
//		switch (error) {
//		case "date":
//			model.addAttribute("error",
//					"Das Datum muss folgendes Format haben : YYYY-MM-DD");
//			break;
//		}
//		return "lieferungForm";
//	}
//
//	@RequestMapping(value = "/LF_result", method = RequestMethod.POST)
//	public String specification(ModelMap model,
//			@RequestParam("quantity") int quantity,
//			@RequestParam("date") String date,
//			@RequestParam("place") String place) {
//		this.quantity = quantity;
//		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//
//		try {
//			this.date = formatter.parse(date);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			return "redirect:form?error=date";
//		}
//		this.place = place;
//		model.addAttribute("quantity", this.quantity);
//		model.addAttribute("date", this.date);
//		model.addAttribute("place", this.place);
//		return "LF_result";
//	}
//
//}
