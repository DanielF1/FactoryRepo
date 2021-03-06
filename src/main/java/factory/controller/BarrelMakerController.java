package factory.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import factory.model.Employee;
import factory.model.Expenditure;
import factory.model.LagerMatrix;
import factory.model.Location;
import factory.model.validation.InsertBarrel;
import factory.repository.DepartmentRepository;
import factory.repository.ExpenditureRepository;
import factory.repository.LocationRepository;


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
	String checkwhether ="";

	
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

	/**
	 * Die double Zahlen werden auf 2 Nachkommastellen gerundet
	 * 
	 * @param x zu rundender Doublewert
	 * @return gerundeter Doublewert
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
	
	/**
	 * Hier wird der Engelanteil jedes Fasses berechnet, 
	 * weil die Inhalte des Fasses jedes Jahr um 3% verringert
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
			int Jahre=datecount/365; //wie lang destillat zum letzten mal in fässern erfüllt wurde
			for (int i = 1; i <= Jahre; i++)
			{
				barrel.setContent_amount(0.97*barrel.getContent_amount());

			}

			barrel.setContent_amount(Runden2Dezimal(barrel.getContent_amount()));
			System.out.println(barrel.getContent_amount());
		}
		departmentRepository.save(barrelstock);

	}
	

	/**
	 * Die Fassliste wird an dem Browser angezeigt
	 * 
	 * @param modelMap bereitgestellt von Spring
	 * @param userAccount bereitgestellt von Spring
	 * @return HTML-Seite
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
							if ((checkwhether.equals(""))||(checkwhether.equals(barrelstock.getName())))
							{
								if (!check){
									checkwhether=barrelstock.getName();
									engelAnteilBesuechtigen();
									check = true;
								}
							}
							else {
								checkwhether=barrelstock.getName();
								engelAnteilBesuechtigen();
								check=true;
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

	/**
	 * Hier wird ein Fass oder werden mehrere Fässer in der Fassliste hinzugefügt.
	 * 
	 * @param barrel_volume ist das Volume des Fasses
	 * @param barrel_anzahl ist die Anzahl der hinzufügenden Fässer
	 * @return HTML-Seiten
	 */
	@RequestMapping("/insertBarrel")
	public String insertBarrel(
			@RequestParam("Barrel_volume") String barrel_volume,
			@RequestParam("Barrel_anzahl") int barrel_anzahl,
			@ModelAttribute("insertBarrel") @Valid InsertBarrel insertBarrel,
			Model model,
			ModelMap modelMap,
			BindingResult result) {
		if (result.hasErrors()) {
		return "inserted";
	}
		double totalprice = 0;

		//Barrel (age, quality, content_amount, manufacturing_date,barrel_volume, birth_of_barrel,death_of_barrel,position )
		for (int i=1;i<=barrel_anzahl;i++){
			
			Barrel barrel = new Barrel(0,"",0, LocalDate.parse("0000-01-01"),Double.parseDouble(barrel_volume),
										LocalDate.now(),LocalDate.now().plusDays(2),LocalDate.parse("0000-01-01"), "");
				barrelstock.getBarrels().add(barrel);
		
		totalprice += 20;	
		}
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
		if (barrel_anzahl==1)
		{
		model.addAttribute("error_green", "Ein Fass mit dem Volume " + barrel_volume + " wurde hinzugefügt." );}
		else {
			model.addAttribute("error_green", barrel_anzahl +" Fässer mit dem Volume " + barrel_volume + " wurde hinzugefügt." );
		}
		departmentRepository.save(barrelstock);
		modelMap.addAttribute("BarrelList", barrelstock.getBarrels());
		return "BarrelList";
	}
	
	/**
	 * Falls die Eingaben des Formular, um Fässer hinzuzufügen, nicht richtig sind,
	 * wird das Formular mit Fehlermeldungen wieder angezeigt
	 * 
	 * @param modelMap bereitgestellt von Spring
	 * @return HTML-Seite
	 */
	@RequestMapping("/inserted")
	public String inserted(ModelMap modelMap) {
		modelMap.addAttribute("insertBarrel", new InsertBarrel());
		return "inserted";
	}
	
	/**
	 * Wenn das Fass zu alt ist, wird es entfernt
	 * 
	 * @param index Position des Fasses
	 * @param model bereitgestellt von Spring
	 * @return HTML-Seite
	 */
	@RequestMapping(value = "/deleteBarrel/{index}", method = RequestMethod.GET)
	public String deleteBarrel(@PathVariable("index") int index, Model model, ModelMap modelMap) {

		Barrel barrel = barrelstock.getBarrels().get(index-1);
		// Fass ist leer
		if (barrel.getDeath_of_barrel().compareTo(LocalDate.now())<=0)
		{
			// Fass Ablaufdateum ist in der Vergangenheit
			if (barrel.getContent_amount() ==0) {
				barrelstock.getBarrels().remove(index-1);
			}
			else{

				Barrel newbarrel = new Barrel(barrel.getAge(),"", 0,barrel.getManufacturing_date(),0,LocalDate.now(),LocalDate.now().plusDays(2), LocalDate.now(), "");
				newbarrel.setQuality(barrel.getQuality());
				newbarrel.setContent_amount(barrel.getContent_amount());
				newbarrel.setBarrel_volume(barrel.getBarrel_volume());
				barrel.setQuality("");
				barrel.setContent_amount(0);
				barrelstock.getBarrels().remove(index-1);
				barrelstock.getBarrels().add(newbarrel);
		}
			
			model.addAttribute("error_green", "Fass wurde erfolgreich entfernt");
		}
		else
		{
			model.addAttribute("error", "Fass wurde nicht entfernt, da es noch nicht zu alt ist.");

		}
		departmentRepository.save(barrelstock);
		modelMap.addAttribute("BarrelList", barrelstock.getBarrels());
		return "BarrelList";
	}
	
	/**
	 * Um den Lagerplatz zu sparen, werden die Inhalte 
	 * gleichalten Alters der Fässer zusammenschütten 
	 * 
	 * @param modelMap bereitgestellt von Spring
	 * @return HTML-Seite
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
				for (Barrel barrel : alterMap.get(key1))
				{
					fass_anzahl++;
				}
				if (fass_anzahl>1){
				for (Barrel barrel : alterMap.get(key1)) {
					hilfsFass += barrel.getContent_amount();
					barrel.setContent_amount(0);
				}
				System.out.println(hilfsFass);
				Iterable<Barrel> allBarrels = alterMap.get(key1);
				Iterator<Barrel> it= allBarrels.iterator();
				Barrel barrel =null;
				while(it.hasNext())
//				for (Barrel barrel : alterMap.get(key1)) {
				{
					barrel = it.next();
					double volume = barrel.getBarrel_volume();
					if (hilfsFass < volume)
						volume = hilfsFass;
					barrel.setContent_amount(Runden2Dezimal(volume));
					System.out.println(barrel.getContent_amount());
					hilfsFass -= volume;
					barrel.setLastFill(LocalDate.now());
					if (barrel.getContent_amount()==0)
					{
						Barrel barrel1=barrel;
						barrel1.setQuality("");
						barrel1.setAge(0);
						barrel1.setPosition("");
						barrel1.setManufacturing_date(LocalDate.parse("0000-01-01"));
						barrelstock.getBarrels().remove(barrel);
						barrelstock.getBarrels().add(barrel1);
					}
					
				}
				}
			}
			}
	
		departmentRepository.save(barrelstock);
		return "redirect:/BarrelList";
	}
	
	/**
	 * Die Fässer werden im Lager zugeordnet
	 * 
	 * @return HTML-Seite
	 */
	@RequestMapping(value = "/fassZuordnen")
	public String fassZuordnen(Model model, ModelMap modelMap){

		this.lagerFurVolleFasser.zuordnen(barrelstock.getBarrels());
		departmentRepository.save(barrelstock);
		this.lagerFurLeereFasser.zuordnen(barrelstock.getBarrels());
		departmentRepository.save(barrelstock);
		model.addAttribute("error_green", "Fässer wurden zugeordnet." );
		modelMap.addAttribute("BarrelList", barrelstock.getBarrels());
		return "BarrelList";
		
	}
}
