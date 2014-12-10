package factory.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

//		barrelstock.deleteBarrel(Id);
		Barrel barrel = barrelstock.findOneBarrel(Id);
		// Fass ist leer
		if (barrel.getDeath_of_barrel().compareTo(LocalDate.now())<=0)
		{
			// Fass Ablaufdateum ist in der Vergangenheit
			if (barrel.getContent().equals("")) {
				barrelstock.deleteOne(Id);
			}
			else{
				Barrel newbarrel = new Barrel("", 0,LocalDate.now(),LocalDate.now().plusDays(2), LocalDate.now());
				newbarrel.setContent(barrel.getContent());
				newbarrel.setAmount(barrel.getAmount());
				barrel.setContent("");
				barrel.setAmount(0);
				barrelstock.deleteOne(barrel.getId());
				barrelstock.saveBarrel(newbarrel);
		}
		}

		return "redirect:/BarrelList";
	}
	@RequestMapping(value = "/putBarrelstogether")
	public String putBarrelsTogether(ModelMap modelMap) {

//		barrelstock.getMasterBrewer().zusammenschuetten();

		Iterable<Barrel> allBarrels = barrelstock.getAllBarrels();
		HashMap<String, List<Barrel>> map = new HashMap<String, List<Barrel>>();
		// Nur barrels mit Inhalt einer Art und GLEICHEN ALTER finden

		for (Barrel barrel : allBarrels) {
			if (barrel.getDeath_of_barrel().compareTo(LocalDate.now())>=0&&!barrel.getContent().equals("")){
			String inhalt = barrel.getContent();
			if (!map.containsKey(inhalt)) {
				map.put(inhalt, new ArrayList<Barrel>());
			}
			map.get(inhalt).add(barrel);
			}
		}

		for (String key : map.keySet()) {
			List<Barrel> list = map.get(key);
		
			// String zu Date, oder Alter oder ... ändern
			HashMap<Integer, List<Barrel>> alterMap = new HashMap<Integer, List<Barrel>>();
			for (Barrel barrel : list) {
				// bearbeiten Alter finden 
				if (barrel.getAlter()>=1){
				int alter = barrel.getAlter();
				
				if (!alterMap.containsKey(alter)) {
					alterMap.put(alter, new ArrayList<Barrel>());
				}
					alterMap.get(alter).add(barrel);	
				}
			}

			for (Integer key1 : alterMap.keySet()) {
//				schuette(alterMap.get(key1));
				double hilfsFass = 0;
				for (Barrel barrel : alterMap.get(key1)) {
					hilfsFass += barrel.getAmount();
					barrel.setAmount(0);
				}
				for (Barrel barrel : alterMap.get(key1)) {
					// Konstante definieren
					double amount = 30;
					if (hilfsFass < amount)
						amount = hilfsFass;
					barrel.setAmount(amount);
					hilfsFass -= amount;
					if (barrel.getAmount()==0)
						barrel.setContent("");
					barrelstock.saveBarrel(barrel);
				}
			}
			}
	

		return "redirect:/BarrelList";
	}

}
