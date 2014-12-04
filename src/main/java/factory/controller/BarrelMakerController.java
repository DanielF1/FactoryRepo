package factory.controller;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import Fassbinder.insertBarrel.InsertBarrel;
//import Fassbinder.model.Barrel;
//import Fassbinder.model.BarrelContentType;
//import Fassbinder.model.BarrelList;
//import Fassbinder.model.BarrelStorageArea;
//
//@Controller
//@PreAuthorize("hasRole('ROLE_FASSBINDER')")
//public class FassbinderController {
//	BarrelStorageArea barrelstoragearea;
//
//	@Autowired
//	public FassbinderController(BarrelList barrellist) {
//		this.barrelstoragearea = new BarrelStorageArea(barrellist);
//	}
//
//	@RequestMapping("/BarrelList")
//	public String barrel(ModelMap modelMap) {
//		Iterable<Barrel> barrels = this.barrelstoragearea.getAllBarrels();
//		System.out.print(barrels.iterator().next());
//		modelMap.addAttribute("BarrelList", barrels);
//
//		return "BarrelList";
//	}
//
//	@RequestMapping("/insertBarrel")
//	public String insertBarrel(@RequestParam("ID") Long ID,
//			@RequestParam("StartDate") String startdate,
//			@RequestParam("Content") String Content,
//			@RequestParam("Volume") int volume,
//			@RequestParam("lastFill") String lastFill,
//			@RequestParam("AblaufDate") String ablaufdate,
//			@ModelAttribute("insertBarrel") @Valid InsertBarrel insertBarrel,
//			BindingResult result) {
//		if (result.hasErrors()) {
//			return "inserted";
//		}
//
//		BarrelContentType content = BarrelContentType.Leer;
//
//		switch (Content) {
//		case "Wein":
//			content = BarrelContentType.Wein;
//			break;
//		case "Destillat":
//			content = BarrelContentType.Destillat;
//			break;
//		}
//
//		Barrel barrel = new Barrel(ID, startdate, content, volume, lastFill,
//				ablaufdate);
//		barrelstoragearea.saveBarrel(barrel);
//		return "redirect:/BarrelList";
//	}
//
//	@RequestMapping("/inserted")
//	public String inserted(ModelMap modelMap) {
//		modelMap.addAttribute("insertBarrel", new InsertBarrel());
//		return "inserted";
//	}
//
//	@RequestMapping(value = "/deleteBarrel", params = "ID")
//	public String deleteBarrel(@RequestParam("ID") Long ID, ModelMap modelMap) {
//
//		barrelstoragearea.deleteBarrel(ID);
//
//		return "redirect:/BarrelList";
//	}
//
//
//
//
//
//
//
//
//
//
//@Controller
//@PreAuthorize("hasRole('ROLE_FASSBINDER')")
//public class FassbinderController {
//	BarrelStorageArea barrelstoragearea;
//
//	@Autowired
//	public FassbinderController(BarrelList barrellist) {
//		this.barrelstoragearea = new BarrelStorageArea(barrellist);
//	}
//
//	@RequestMapping("/BarrelList")
//	public String barrel(ModelMap modelMap) {
//		Iterable<Barrel> barrels = this.barrelstoragearea.getAllBarrels();
//		System.out.print(barrels.iterator().next());
//		modelMap.addAttribute("BarrelList", barrels);
//
//		return "BarrelList";
//	}
//
//	@RequestMapping("/insertBarrel")
//	public String insertBarrel(@RequestParam("ID") Long ID,
//			@RequestParam("StartDate") String startdate,
//			@RequestParam("Content") String Content,
//			@RequestParam("Volume") int volume,
//			@RequestParam("lastFill") String lastFill,
//			@RequestParam("AblaufDate") String ablaufdate,
//			@ModelAttribute("insertBarrel") @Valid InsertBarrel insertBarrel,
//			BindingResult result) {
//		if (result.hasErrors()) {
//			return "inserted";
//		}
//
//		BarrelContentType content = BarrelContentType.Leer;
//
//		switch (Content) {
//		case "Wein":
//			content = BarrelContentType.Wein;
//			break;
//		case "Destillat":
//			content = BarrelContentType.Destillat;
//			break;
//		}
//
//		Barrel barrel = new Barrel(ID, startdate, content, volume, lastFill,
//				ablaufdate);
//		barrelstoragearea.saveBarrel(barrel);
//		return "redirect:/BarrelList";
//	}
//
//	@RequestMapping("/inserted")
//	public String inserted(ModelMap modelMap) {
//		modelMap.addAttribute("insertBarrel", new InsertBarrel());
//		return "inserted";
//	}
//
//	@RequestMapping(value = "/deleteBarrel", params = "ID")
//	public String deleteBarrel(@RequestParam("ID") Long ID, ModelMap modelMap) {
//
//		barrelstoragearea.deleteBarrel(ID);
//
//		return "redirect:/BarrelList";
//	}
//
//}











//}
