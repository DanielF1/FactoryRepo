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
import factory.model.Department;
import factory.model.DepartmentRepository;
import factory.model.Employee;
import factory.model.Expenditure;
import factory.model.ExpenditureRepository;
import factory.model.LagerMatrix;
import factory.model.Location;
import factory.model.LocationRepository;
import factory.model.validation.InsertBarrel;


@Controller
@PreAuthorize("hasRole('ROLE_BARRELMAKER') || hasRole('ROLE_BREWER') || hasRole('ROLE_WAREHOUSEMAN') ||  hasRole('ROLE_SUPERUSER')")
public class BarrelMakerController {
	
	private final LocationRepository locationRepository;
	private final DepartmentRepository departmentRepository;
	private BarrelStock barrelstock;
	private final ExpenditureRepository expenditureRepository;
	private LagerMatrix lagerFurLeereFasser;
	private LagerMatrix lagerFurVolleFasser;
	boolean check = false;

	
	@Autowired
	public BarrelMakerController(	LocationRepository locationRepository,
									DepartmentRepository departmentRepository, 
									BarrelStock barrelstock,
									ExpenditureRepository expenditureRepository){
		this.locationRepository = locationRepository;
		this.barrelstock = barrelstock;
		this.departmentRepository = departmentRepository;
		this.expenditureRepository = expenditureRepository;
		this.lagerFurLeereFasser = new LagerMatrix(departmentRepository,barrelstock,"FLL-R");
		this.lagerFurVolleFasser = new LagerMatrix(departmentRepository,barrelstock,"FLV-R");
	}

	/*
	 Die double Zahlen werden auf 2 Nachkommastellen gerundet
	 */

	public double Runden2Dezimal(double x) { 
		double Ergebnis;
		Ergebnis = (double) (int) (x*100)/100;
		return Ergebnis; 
		}
	
	public void alterBerechnen(){
		List<Barrel> allBarrels = barrelstock.getBarrels();
		for (Barrel barrel: allBarrels)
		{
			barrel.setAge(barrel.getAge());
		}
		departmentRepository.save(barrelstock);
		
	}
	
	/*
	 Hier wird der Engelanteil jedes Fasses berechnet, weil die Inhalte des Fasses jedes Jahr um 3% verringert
	 */
	public void engelAnteilBesuechtigen(){
		List<Barrel> allBarrels = barrelstock.getBarrels();
		int datecount = 0;
	
		for (Barrel barrel: allBarrels)
		{
			if (barrel.getLastFill().compareTo(LocalDate.now().minusDays(365))<0)
			{
				
				while (barrel.getLastFill().compareTo(LocalDate.now())<0){
					datecount++;
					barrel.setLastFill(barrel.getLastFill().plusDays(1));
				}
			barrel.setLastFill(LocalDate.now().minusDays(datecount));
			}
			int Jahre=datecount/365; //wie lang destillat zu letzten mal in fässern erfüllt wurde
			for (int i = 1; i <= Jahre; i++)
			{
				barrel.setContent_amount(0.97*barrel.getContent_amount());

			}

			barrel.setContent_amount(Runden2Dezimal(barrel.getContent_amount()));
			System.out.println(barrel.getContent_amount());
		}
		departmentRepository.save(barrelstock);

	}
	

	/*
	 Die Fassliste wird an dem Browser angezeigt
	 */
	
	@RequestMapping("/BarrelList")
	public String barrel(ModelMap modelMap, @LoggedIn Optional<UserAccount> userAccount) {
		System.out.print("11111111111111");
		
		for(Location loc : locationRepository.findAll()){
			for(Employee e : loc.getEmployees()){
				if(e.getUserAccount() == userAccount.get()){
					for(Department dep : loc.getDepartments()){
						if(dep.getName().contains("Fasslager")){
							barrelstock = (BarrelStock) dep;
							if (!check){
								engelAnteilBesuechtigen();
								check = true;
							}
							modelMap.addAttribute("BarrelList", barrelstock.getBarrels());
							return "BarrelList";
						}
					}
				}
			}
		}

		return "BarrelList";
	}

	/*
	 Hier wird ein Fass oder werden mehrere Fässer in der Fassliste hinzugefügt. 
	 */
	@RequestMapping("/insertBarrel")
	public String insertBarrel(
			@RequestParam("Barrel_volume") String barrel_volume,
			@RequestParam("Barrel_anzahl") int barrel_anzahl,
			@ModelAttribute("insertBarrel") @Valid InsertBarrel insertBarrel,
			BindingResult result) {
		if (result.hasErrors()) {
		return "inserted";
	}
		//Barrel (age, quality, content_amount, manufacturing_date,barrel_volume, birth_of_barrel,death_of_barrel,position )
		for (int i=1;i<=barrel_anzahl;i++)
		{
		Barrel barrel = new Barrel(0,"",0, LocalDate.parse("0000-01-01"),Double.parseDouble(barrel_volume),
				LocalDate.now(),LocalDate.now().plusDays(2),LocalDate.parse("0000-01-01"), "");
		barrelstock.getBarrels().add(barrel);
		}
		
		double totalprice = 20;
		
		expenditureRepository.save(new Expenditure(LocalDate.now(), totalprice, "Fassherstellung"));
		
//		for(Location loc : locationRepository.findAll()){
//			for(Department department : loc.getDepartments()){
//				if(department.getName().contains("Rechnungswesen")){
//		
//					Accountancy acc = (Accountancy) department;
//					acc.getExpenditures().add(exp);
//				
//				}//if
//			}//for
//		}//for
		

		departmentRepository.save(barrelstock);
		return "redirect:/BarrelList";
	}
	/*
	 Falls die Eingaben des Formular, um Fässer hinzuzufügen, nicht richtig sind,
	 wird das Formular mit Fehlermeldungen wieder angezeigt
	 */
	@RequestMapping("/inserted")
	public String inserted(ModelMap modelMap) {
		modelMap.addAttribute("insertBarrel", new InsertBarrel());
		return "inserted";
	}
	/*
	 Wenn das Fass zu alt ist, wird es entfernt
	 */
	@RequestMapping(value = "/deleteBarrel/{index}", method = RequestMethod.GET)
	public String deleteBarrel(@PathVariable("index") int index, ModelMap modelMap) {

		Barrel barrel = barrelstock.getBarrels().get(index-1);
		// Fass ist leer
		if (barrel.getDeath_of_barrel().compareTo(LocalDate.now())<=0)
		{
			// Fass Ablaufdateum ist in der Vergangenheit
			if (barrel.getContent_amount() ==0) {
				System.out.println("Size List: " + barrelstock.getBarrels().size());
				barrelstock.getBarrels().remove(index-1);
				System.out.println("Size List: " + barrelstock.getBarrels().size());
			}
			else{

				Barrel newbarrel = new Barrel(barrel.getAge(),"", 0,barrel.getManufacturing_date(),0,LocalDate.now(),LocalDate.now().plusDays(2), LocalDate.now(), "");
				newbarrel.setQuality(barrel.getQuality());
				newbarrel.setContent_amount(barrel.getContent_amount());
				newbarrel.setBarrel_volume(barrel.getBarrel_volume());
				newbarrel.setPosition(barrel.getPosition());
				barrel.setQuality("");
				barrel.setContent_amount(0);
				barrelstock.getBarrels().remove(index-1);
				barrelstock.getBarrels().add(newbarrel);
		}
		}
		departmentRepository.save(barrelstock);
		return "redirect:/BarrelList";
	}
	
	/*
	 Um den Lagerplatz zu sparen, werden die Inhalte gleichalten Alters der Fässer zusammenschütten 
	 */
	@RequestMapping(value = "/putBarrelstogether")
	public String putBarrelsTogether(ModelMap modelMap) {

		HashMap<String, List<Barrel>> map = new HashMap<String, List<Barrel>>();
		
		// Nur barrels mit Inhalt einer Art und GLEICHEN ALTER finden

		for (Barrel barrel : barrelstock.getBarrels()) {
			
			if (
					!barrel.getQuality().equals("")
					&&barrel.getContent_amount()!=0){
				
			String inhalt = barrel.getQuality();
			
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
					int alter = barrel.getAge();
				if (!alterMap.containsKey(alter)) {
					alterMap.put(alter, new ArrayList<Barrel>());
					}
					alterMap.get(alter).add(barrel);	
			}

			for (Integer key1 : alterMap.keySet()) {
				
				double hilfsFass = 0;
				int fass_anzahl =0;
				for (Barrel barrel: alterMap.get(key1))
				{
					fass_anzahl++;
				}
				if (fass_anzahl>1){
				for (Barrel barrel : alterMap.get(key1)) {
					hilfsFass += barrel.getContent_amount();
					barrel.setContent_amount(0);
				}
				System.out.println(hilfsFass);
				for (Barrel barrel : alterMap.get(key1)) {
					
					double volume = barrel.getBarrel_volume();
					if (hilfsFass < volume)
						volume = hilfsFass;
					barrel.setContent_amount(Runden2Dezimal(volume));
					System.out.println(barrel.getContent_amount());
					hilfsFass -= volume;
					if (barrel.getContent_amount()==0)
					{
						barrel.setQuality("");
						barrel.setAge(0);
						barrel.setManufacturing_date(LocalDate.parse("0000-01-01"));
					}
					barrel.setLastFill(LocalDate.now());
				}
				}
			}
			}
	
		departmentRepository.save(barrelstock);
		return "redirect:/BarrelList";
	}
	
	/*
	 Die Fässer werden im Lager zugeordnet
	 */
	@RequestMapping(value = "/fassZuordnen")
	public String fassZuordnen(){
//		List<Barrel> allBarrels = barrelstock.getBarrels();
//		int FLL_RegalNr = 1;
//		int FLV_RegalNr = 1;
//		int FLL_Platz = 1;
//		int FLV_Platz = 1;
//		int MAX_PLATZ_IM_REGAL = 5;
//		for (Barrel barrel:allBarrels)
//		{
//			if (barrel.getQuality().equals("")){
//				barrel.setPosition("FLL-R" + FLL_RegalNr +"-"+ FLL_Platz);	
//				if (FLL_Platz < MAX_PLATZ_IM_REGAL){
//					FLL_Platz++;
//				}
//				else{
//					FLL_Platz = 1;
//					FLL_RegalNr++;
//				}
//				
//			}
//			if (!barrel.getQuality().equals("")){
//				barrel.setPosition("FLV-R" + FLV_RegalNr +"-"+ FLV_Platz);
//				if (FLV_Platz < MAX_PLATZ_IM_REGAL){
//					FLV_Platz++;
//				}
//				else{
//					FLV_Platz = 1;
//					FLV_RegalNr++;
//				}
//			}
		this.lagerFurVolleFasser.zuordnen(barrelstock.getBarrels());
		departmentRepository.save(barrelstock);
		this.lagerFurLeereFasser.zuordnen(barrelstock.getBarrels());
		departmentRepository.save(barrelstock);
		return "redirect:/BarrelList";
		
//		return "redirect:/BarrelList";
	}
}
