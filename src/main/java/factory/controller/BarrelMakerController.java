package factory.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import factory.model.Barrel;
import factory.model.BarrelStock;
import factory.model.LocationRepository;
import factory.model.validation.InsertBarrel;


@Controller
@PreAuthorize("hasRole('ROLE_BARRELMAKER') || hasRole('ROLE_BREWER') || hasRole('ROLE_WAREHOUSEMAN')  ||  hasRole('ROLE_SUPERUSER')")
public class BarrelMakerController {
	
	private final LocationRepository locationRepository;
	
	@Autowired
	public BarrelMakerController(LocationRepository locationRepository){
		this.locationRepository = locationRepository;
	}
	
	@RequestMapping("/BarrelList")
	public String barrel(ModelMap modelMap, @LoggedIn Optional<UserAccount> userAccount) {
		System.out.print("11111111111111");
		
//		for(Location loc : locationRepository.findAll()){
//			for(Employee e : loc.getEmployees()){
//				if(e.getUserAccount() == userAccount.get()){
//					for(Department dep : loc.getDepartments()){
//						if(dep.getName().contains("Fasslager")){
//							BarrelStock barrelstock = (BarrelStock) dep;
//							modelMap.addAttribute("BarrelList", barrelstock.getBarrels());
//		modelMap.addAttribute("BarrelList", BarrelStock.getBarrels());
//		return "BarrelList";
//						}
//					}
//				}
//			}
//		}
//		

		modelMap.addAttribute("BarrelList", BarrelStock.getBarrels());

		return "BarrelList";
	}

	@RequestMapping("/insertBarrel")
	public String insertBarrel(
			@RequestParam("Content") String content,
			@RequestParam("Content_amount") double content_amount,
			@RequestParam("Barrel_volume") String barrel_volume,
			@ModelAttribute("insertBarrel") @Valid InsertBarrel insertBarrel,
			BindingResult result) {
		if (result.hasErrors()) {
		return "inserted";
	}
		
		Barrel barrel = new Barrel(content,content_amount,Double.parseDouble(barrel_volume),LocalDate.parse("0000-01-01"),
				LocalDate.now(),LocalDate.now().plusDays(2),LocalDate.parse("0000-01-01"), "");
		BarrelStock.getBarrels().add(barrel);
		return "redirect:/BarrelList";
	}

	@RequestMapping("/inserted")
	public String inserted(ModelMap modelMap) {
		modelMap.addAttribute("insertBarrel", new InsertBarrel());
		return "inserted";
	}

	@RequestMapping(value = "/deleteBarrel/{index}", method = RequestMethod.GET)
	public String deleteBarrel(@PathVariable("index") int index, ModelMap modelMap) {

		Barrel barrel = BarrelStock.getBarrels().get(index-1);
		// Fass ist leer
		if (barrel.getDeath_of_barrel().compareTo(LocalDate.now())<=0)
		{
			// Fass Ablaufdateum ist in der Vergangenheit
			if (barrel.getContent().equals("")) {
				System.out.println("Size List: " + BarrelStock.getBarrels().size());
				BarrelStock.getBarrels().remove(index-1);
				System.out.println("Size List: " + BarrelStock.getBarrels().size());
			}
			else{
				Barrel newbarrel = new Barrel("", 0,0,barrel.getManufacturing_date(),LocalDate.now(),LocalDate.now().plusDays(2), LocalDate.now(), "");
				newbarrel.setContent(barrel.getContent());
				newbarrel.setContent_amount(barrel.getContent_amount());
				newbarrel.setBarrel_volume(barrel.getBarrel_volume());
				newbarrel.setPosition(barrel.getPosition());
				barrel.setContent("");
				barrel.setContent_amount(0);
				BarrelStock.getBarrels().remove(index-1);
				BarrelStock.getBarrels().add(newbarrel);
		}
		}

		return "redirect:/BarrelList";
	}
	@RequestMapping(value = "/putBarrelstogether")
	public String putBarrelsTogether(ModelMap modelMap) {

		HashMap<String, List<Barrel>> map = new HashMap<String, List<Barrel>>();
		
		// Nur barrels mit Inhalt einer Art und GLEICHEN ALTER finden

		for (Barrel barrel : BarrelStock.getBarrels()) {
			
			if (barrel.getDeath_of_barrel().compareTo(LocalDate.now())>=0
					&&barrel.getLastFill().compareTo(LocalDate.now().minusDays(365))<=0  // LastFill = LastFillDate
					&&!barrel.getContent().equals("")
					&&barrel.getContent_amount()!=0){
				
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
					int alter = barrel.getAlter();
				if (!alterMap.containsKey(alter)) {
					alterMap.put(alter, new ArrayList<Barrel>());
					}
					alterMap.get(alter).add(barrel);	
			}

			for (Integer key1 : alterMap.keySet()) {
				
				double hilfsFass = 0;
				for (Barrel barrel : alterMap.get(key1)) {
					hilfsFass += barrel.getContent_amount();
					barrel.setContent_amount(0);
				}
				System.out.println(hilfsFass);
				for (Barrel barrel : alterMap.get(key1)) {
					
					double volume = barrel.getBarrel_volume();
					if (hilfsFass < volume)
						volume = hilfsFass;
					barrel.setContent_amount(volume);
					System.out.println(barrel.getContent_amount());
					hilfsFass -= volume;
					if (barrel.getContent_amount()==0)
						barrel.setContent("");
					barrel.setLastFill(LocalDate.now());
//					BarrelStock.getBarrels().add(barrel);
				}
			}
			}
	

		return "redirect:/BarrelList";
	}
	
	@RequestMapping(value = "/fassZuordnen")
	public String fassZuordnen(ModelMap modelMap){
		List<Barrel> allBarrels = BarrelStock.getBarrels();
		int FLL_RegalNr = 1;
		int FLV_RegalNr = 1;
		int FLL_Platz = 1;
		int FLV_Platz = 1;
		int MAX_PLATZ_IM_REGAL = 5;
		for (Barrel barrel:allBarrels)
		{
			if (barrel.getContent().equals("")){
				barrel.setPosition("FLL-R" + FLL_RegalNr +"-"+ FLL_Platz);	
				if (FLL_Platz < MAX_PLATZ_IM_REGAL){
					FLL_Platz++;
				}
				else{
					FLL_Platz = 1;
					FLL_RegalNr++;
				}
				
			}
			if (!barrel.getContent().equals("")){
				barrel.setPosition("FLV-R" + FLV_RegalNr +"-"+ FLV_Platz);
				if (FLV_Platz < MAX_PLATZ_IM_REGAL){
					FLV_Platz++;
				}
				else{
					FLV_Platz = 1;
					FLV_RegalNr++;
				}
			}
			
		}
		
		return "redirect:/BarrelList";
	}
}
