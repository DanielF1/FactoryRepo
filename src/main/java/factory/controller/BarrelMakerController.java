package factory.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import factory.model.Barrel;
import factory.model.BarrelList;
import factory.model.BarrelStock;
import factory.model.validation.InsertBarrel;


@Controller
@PreAuthorize("hasRole('ROLE_BARRELMAKER') || hasRole('ROLE_BREWER')")
public class BarrelMakerController {
	BarrelStock barrelstock;

	@Autowired
	public BarrelMakerController(BarrelList barrellist) {
		this.barrelstock = new BarrelStock(barrellist);
	}

	@RequestMapping("/BarrelList")
	public String barrel(ModelMap modelMap) {
		Iterable<Barrel> barrels = this.barrelstock.getAllBarrels();
		System.out.print("11111111111111");
		modelMap.addAttribute("BarrelList", barrels);

		return "BarrelList";
	}

	@RequestMapping("/insertBarrel")
	public String insertBarrel(@RequestParam("Id") Long Id,
			@RequestParam("Content") String content,
			@RequestParam("Amount") double amount,
			@RequestParam("Birthdate_of_barrel") String birthdate_of_barrel,
			@RequestParam("Death_of_barrel") String death_of_barrel,
			@RequestParam("LastFill") String lastFill,
			@ModelAttribute("insertBarrel") @Valid InsertBarrel insertBarrel,
			BindingResult result) {
		if (result.hasErrors()) {
		return "inserted";
	}
		
		Barrel barrel = new Barrel(content,amount,birthdate_of_barrel, death_of_barrel,lastFill);
		barrelstock.saveBarrel(barrel);
		return "redirect:/BarrelList";
	}

	@RequestMapping("/inserted")
	public String inserted(ModelMap modelMap) {
		modelMap.addAttribute("insertBarrel", new InsertBarrel());
		return "inserted";
	}

	@RequestMapping(value = "/deleteBarrel", params = "Id")
	public String deleteBarrel(@RequestParam("Id") Long Id, ModelMap modelMap) {

		barrelstock.deleteBarrel(Id);

		return "redirect:/BarrelList";
	}
	@RequestMapping(value = "/putBarrelstogether")
	public String putBarrelsTogether(ModelMap modelMap) {

		barrelstock.getMasterBrewer().zusammenschuetten();

		return "redirect:/BarrelList";
	}

}
