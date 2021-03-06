package factory.controller;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import factory.model.Department;
import factory.model.Location;
import factory.model.Production;
import factory.model.ProductionADay;
import factory.model.ProductionManagement;
import factory.model.Still;
import factory.model.WineStock;
import factory.model.WineTransport;
import factory.model.validation.DeliveryForm;
import factory.repository.DepartmentRepository;
import factory.repository.LocationRepository;
import factory.repository.ProductionManagementRepository;
import factory.repository.WineTransportRepository;

@Controller
@PreAuthorize("hasRole('ROLE_BREWER') ||  hasRole('ROLE_SUPERUSER') ||  hasRole('ROLE_WINEGROWER')")
public class WineTransportController {

	WineStock winestock;
	Production production;
	Long seconds = 10L;
	
	private final LocationRepository locationRepository;
	private final DepartmentRepository departmentrepository;
	private final ProductionManagementRepository productionManagementRepository;
	private final WineTransportRepository wineTransportRepository;
	
	@Autowired 
	public WineTransportController(LocationRepository locationRepository, DepartmentRepository departmentrepository,
			ProductionManagementRepository productionManagementRepository, WineTransportRepository wineTransportRepository)
	{
		this.locationRepository = locationRepository;
		this.departmentrepository = departmentrepository;
		this.productionManagementRepository = productionManagementRepository;
		this.wineTransportRepository = wineTransportRepository;
	}
	


	/**
	 * initialize an array of months and check if this year is a leap year
	 * 
	 * 
	 * @param months an array of strings
	 * @return an array of strings
	 */
	private String[][] initializeMonths(String[][] months) 
	{
		months[0][0] = "Januar";	months[0][1] = "31";
		months[1][0] = "Februar";	months[1][1] = "28";
		months[2][0] = "März";		months[2][1] = "31";
		months[3][0] = "April"; 	months[3][1] = "30";
		months[4][0] = "Mai";		months[4][1] = "31";
		months[5][0] = "Juni";		months[5][1] = "30";
		months[6][0] = "Juli";		months[6][1] = "31";
		months[7][0] = "August";	months[7][1] = "31";
		months[8][0] = "September";	months[8][1] = "30";
		months[9][0] = "Oktober";	months[9][1] = "31";
		months[10][0] = "November";	months[10][1] = "30";
		months[11][0] = "Dezember";	months[11][1] = "31";


		
		/*
		 * check, if this year is a leap year
		 */
	
		if(LocalDate.now().isLeapYear())
		{
			months[1][0] = "Februar";	months[1][1] = "29";
			
		} else {
			
			months[1][0] = "Februar";	months[1][1] = "28";
		}

		return months;	
	}
	

	/*
	 * calculate the amount of wine, 
	 * that results through the still production
	 */
	public void processThroughStills()
	{
		productionManagementRepository.deleteAll();
		
		DateTime final_date = new DateTime(2015, 4, 1, 0, 0, 0, 0);
		new DateTime();
		DateTime now = DateTime.now();
		int left_days = Days.daysBetween(now.toLocalDate(), final_date.toLocalDate()).getDays();
		double max_still_amount = 0;
		
		List<WineTransport> transport_list = new ArrayList<>();
		
		/*
		 * add all transports to a temporary list
		 */
		for(WineTransport wine_transport : wineTransportRepository.findAll()){
			transport_list.add(wine_transport);
		}

		/*
		 * search for productions
		 */
		for(Location location : locationRepository.findAll()){
			for(Department department : location.getDepartments()){	
				if(department.getName().contains("Produktion")){
					
					production = (Production) department;
					
					/*
					 * create default day
					 */
					ProductionADay default_day = new ProductionADay(null, null, 0, 0, 0, 0);
					
					List<ProductionADay> default_day_list = new ArrayList<ProductionADay>();
					default_day_list.add(default_day);
					
					ProductionManagement production_management = new ProductionManagement(location.getName(), default_day_list);
					
					/*
					 * create production days until first April
					 */
					for(int i = 0; i < left_days; i++){
						LocalDate today = LocalDate.now().plusDays(i);
						
						ProductionADay productionADay = new ProductionADay(today, location.getName(), 0, 0, 0, 0);
						production_management.getProduction_a_day().add(productionADay);
						
					}	
					
					production_management.getProduction_a_day().remove(0);
					productionManagementRepository.save(production_management);
					
				}		
			}
		}

		double wine_amound_at_the_end_of_day = 0;
		int i = 1;
		
		for(ProductionManagement production_management : productionManagementRepository.findAll()){	

			/*
			 * calculate maximum amount of stills
			 */
			for(Location location : locationRepository.findAll()){
				if(location.getName().equals(production_management.getLocation_name())){
					for(Department department : location.getDepartments()){	
						if(department.getName().contains("Produktion")){
							
							production = (Production) department;

							for(Still still : production.getStills()){
								max_still_amount = max_still_amount + still.getAmount();
							}
						}
					}
				}
			}
			
			for(ProductionADay productionADay : production_management.getProduction_a_day()){

				double max_still_production_a_day = (max_still_amount * 0.8) / 2;
				double max_export_wine_amount = 0;
				double max_inport_wine_amount = 0;
				double max_delivery_wine_amount = 0;
				
				
				for(WineTransport wine_transport : transport_list){
					LocalDate today = productionADay.getDate();
					LocalDate transport_day_start = wine_transport.getStart_date().toLocalDate();

					if((!wine_transport.getStarting_point().equals("Weinbauer")) && (transport_day_start.equals(today))
							&& (wine_transport.getStarting_point().equals(productionADay.getLocation_name()))){
						max_export_wine_amount += wine_transport.getAmount();	
					}
				}
				
				for(WineTransport wine_transport : transport_list){
					LocalDate today = productionADay.getDate();
					LocalDate transport_day_end = wine_transport.getGoal_date().toLocalDate();

					if((!wine_transport.getStarting_point().equals("Weinbauer")) && (transport_day_end.equals(today))
							&& (wine_transport.getGoal().equals(productionADay.getLocation_name()))){
						max_inport_wine_amount += wine_transport.getAmount();
						
					}
				}
				
				for(WineTransport wine_transport : transport_list){
					LocalDate today = productionADay.getDate();
					LocalDate transport_day_end = wine_transport.getGoal_date().toLocalDate();
					
					if((wine_transport.getStarting_point().equals("Weinbauer")) && (transport_day_end.equals(today))
							&& (wine_transport.getGoal().equals(productionADay.getLocation_name()))){
						max_delivery_wine_amount += wine_transport.getAmount();
						
					}
				}
				
				
				System.out.println("max_export_wine_amount " + max_export_wine_amount);
				System.out.println("max_inport_wine_amount " + max_inport_wine_amount);
				System.out.println("max_delivery_wine_amount " + max_delivery_wine_amount);
				
				productionADay.setWine_delivery_at_that_day(max_delivery_wine_amount);
				productionADay.setWine_transport_at_that_day_in(max_inport_wine_amount);
				productionADay.setWine_transport_at_that_day_out(max_export_wine_amount);
				productionADay.setWine_amount_for_production(wine_amound_at_the_end_of_day + max_inport_wine_amount 
						+ max_delivery_wine_amount - max_export_wine_amount);
				
				
				wine_amound_at_the_end_of_day = wine_amound_at_the_end_of_day + max_delivery_wine_amount 
						+ max_inport_wine_amount - max_export_wine_amount - max_export_wine_amount - (max_still_production_a_day);
				System.out.println("wine_amound_at_the_end_of_day " + wine_amound_at_the_end_of_day);
				
				if(wine_amound_at_the_end_of_day < 0){
					wine_amound_at_the_end_of_day = 0;
				}
				
				max_export_wine_amount = 0;
				max_inport_wine_amount = 0;
				max_delivery_wine_amount = 0;
				
				i++;
			}
			
			i = 0;
			max_still_amount = 0;
			wine_amound_at_the_end_of_day = 0;
			productionManagementRepository.save(production_management);
		}

	}
	


	/**
	 * mapping the wine stock
	 * 
	 * 
	 * @param model Spring element for modeling Java code with help of Thymeleaf on templates
	 * @return the modeling template
	 */
	@RequestMapping(value = "/winestocks", method = RequestMethod.GET)
	public String winestocks(Model model)
	{	

		processThroughStills();
	
		model.addAttribute("production_management", productionManagementRepository.findAll());
		
		return "winestocks";
	}



	/**
	 * create an wine transport
	 * 
	 * 
	 * @param location_start name of a location where the transport start
	 * @param location_goal name of a location where the transport have to travel
	 * @param wine_amount amount of wine that have to be transported
	 * @param model Spring element for modeling Java code with help of Thymeleaf on templates
	 * @return the modeling template
	 */
	@RequestMapping(value = "/wineTransport", method = RequestMethod.POST)
	public String createWineTransport(@RequestParam("location_start") String location_start, 
									  @RequestParam("location_goal") String location_goal, 
									  @RequestParam("wine_amount") double wine_amount, Model model)
	{
		double wine_amount_in_hectoliter = wine_amount / 100;
		
		if(location_start.equals("") && location_start.equals("Start")){
			model.addAttribute("error", "Eingabe noch einmal überprüfen.");
		} else if(location_goal.equals("")){
			model.addAttribute("error", "Eingabe noch einmal überprüfen.");
		} else if(location_start.equals(location_goal)){
			model.addAttribute("error", "Eingabe noch einmal überprüfen.");
		} else {
			
			boolean transport_is_okay = false;

			for(Location location : locationRepository.findAll()){
				if(location.getName().equals(location_start)){
					for(Department department : location.getDepartments()){
						if(department.getName().contains("Weinlager")){
							
							winestock = (WineStock) department;
							
							for(ProductionManagement productionManagement : productionManagementRepository.findAll()){
								if(productionManagement.getLocation_name().equals(location.getName())){
									for(ProductionADay productionADay : productionManagement.getProduction_a_day()){
										if(productionADay.getDate().equals(LocalDate.now())){
											if((productionADay.getWine_amount_for_production() - wine_amount_in_hectoliter) >= 0){
												
												transport_is_okay = true;

//												productionADay.setWine_amount_for_production(
//														productionADay.getWine_amount_for_production() - wine_amount_in_hectoliter);
												
												WineTransport wine_transport = new WineTransport(location_start, location_goal, wine_amount_in_hectoliter, 
														LocalDateTime.now(), LocalDateTime.now().plusSeconds(seconds), true);
												
												winestock.setAmount(winestock.getAmount() - wine_amount_in_hectoliter);
												wineTransportRepository.save(wine_transport);
												
											} else {
												System.out.println("error stage 4");
											}
											
										}
									}
									
									
//									productionManagementRepository.save(productionManagement);
									departmentrepository.save(winestock);
								}
							} // /for
							
						}
					}
				}
			} // /for

	
			if(transport_is_okay){
				for(Location location : locationRepository.findAll()){
					if(location.getName().equals(location_goal)){
						for(Department department : location.getDepartments()){
							if(department.getName().contains("Weinlager")){
								
								winestock = (WineStock) department;
						
								for(ProductionManagement productionManagement : productionManagementRepository.findAll()){
									if(productionManagement.getLocation_name().equals(location.getName())){
										for(ProductionADay productionADay : productionManagement.getProduction_a_day()){
											if(productionADay.getDate().equals(LocalDate.now())){
									
												transport_is_okay = false;
												
//												productionADay.setWine_amount_for_production(
//														productionADay.getWine_amount_for_production() + wine_amount_in_hectoliter);
												
												winestock.setAmount(winestock.getAmount() + wine_amount_in_hectoliter);
												
											
											}
										}
//										productionManagementRepository.save(productionManagement);
										departmentrepository.save(winestock);
									}
								}
							
							}
						}
					}
				}
			}
			model.addAttribute("error_green", "Erfolgreich versendet.");
		}
		
		return "redirect:/winestocks";
		
	}
	
	
	/**
	 * mapping the delivery template
	 * 
	 * 
	 * @param model Spring element for modeling Java code with help of Thymeleaf on templates
	 * @return the modeling template
	 */
	@RequestMapping(value = "/wine_delivery_form", method = RequestMethod.GET)
	public String wine_dilivery_form(Model model){
		
		List<String> locations = new ArrayList<>();

		for(Location location : locationRepository.findAll()){
			for(Department department : location.getDepartments()){
				if(department.getName().contains("Weinlager")){
					locations.add(location.getName());
				}
			}	
		}
		
		model.addAttribute("locations", locations);
		model.addAttribute("DeliveryForm", new DeliveryForm());
		
		return "wine_delivery_form";
	}
	
	

	/**
	 * start the wine delivery
	 * 
	 * 
	 * @param DeliveryForm class DeliveryForm
	 * @param bindingResult return result of validation
	 * @param model Spring element for modeling Java code with help of Thymeleaf on templates
	 * @return modeling template
	 */
	@RequestMapping(value = "/startWineDelivery", method = RequestMethod.POST)
	public String wine_dilivery(@ModelAttribute("registrationForm") @Valid DeliveryForm DeliveryForm,
								BindingResult bindingResult,
								Model model){
		
		if (bindingResult.hasErrors()) {
			return "startWineDelivery";
		}
		
		int wine_delivery_amount = DeliveryForm.getWine_delivery_amount();
		int wine_delivery_year = DeliveryForm.getWine_delivery_year();
		int wine_delivery_month = DeliveryForm.getWine_delivery_month();
		int wine_delivery_day = DeliveryForm.getWine_delivery_day();
		String location_goal = DeliveryForm.getLocation_goal();
		
		String[][] months = new String[12][2];
		months = initializeMonths(months);
		
		int max_days_of_month = Integer.parseInt(months[wine_delivery_month][1]);
		
		if(wine_delivery_day >= max_days_of_month){
			model.addAttribute("error", "Eingabe noch einmal überprüfen.");
			
		} else {
		
			for(Location location : locationRepository.findAll()){
				if(location.getName().equals(location_goal)){
					for(Department department : location.getDepartments()){
						if(department.getName().contains("Weinlager")){
							
							winestock = (WineStock) department;
													
							winestock.setAmount(winestock.getAmount() + wine_delivery_amount);
							
	
							LocalDateTime date = LocalDateTime.of(wine_delivery_year, wine_delivery_month, wine_delivery_day, LocalDateTime.now().getHour(), LocalDateTime.now().getMinute());
							
							WineTransport winetransport = new WineTransport("Weinbauer", location_goal, wine_delivery_amount, LocalDateTime.now(), date, true);
							
							wineTransportRepository.save(winetransport);
							departmentrepository.save(winestock);
							
							System.out.println("winestock.getAmount() " + winestock.getAmount());
						}
					}
				}
			}
			
			model.addAttribute("error_green", "Erfolgreich versendet.");
		} // /else
		
		return "redirect:/wine_delivery_form";
	}
	
	
	/**
	 * mapping the wine transport template
	 * 
	 * 
	 * @param model Spring element for modeling Java code with help of Thymeleaf on templates
	 * @return the modeling template
	 */
	@RequestMapping(value = "/wine_transport", method = RequestMethod.GET)
	public String wine_transport(Model model)
	{
		List<WineTransport> wine_transports = new ArrayList<>();
		
		for(WineTransport winetransport : wineTransportRepository.findAll()){
			if(winetransport.getStarting_point() != "Weinbauer"){
				wine_transports.add(winetransport);
			}
		}
		
		model.addAttribute("wine_transport", wine_transports);
		
		return "wine_transport";
	}
	
	
	/**
	 * mapping the delivery transport template
	 * 
	 * 
	 * @param model Spring element for modeling Java code with help of Thymeleaf on templates
	 * @return the modeling template
	 */
	@RequestMapping(value = "/delivery_transport", method = RequestMethod.GET)
	public String delivery_transport(Model model)
	{
		List<WineTransport> delivery_transports = new ArrayList<>();
		
		for(WineTransport winetransport : wineTransportRepository.findAll()){
			if(winetransport.getStarting_point().equals("Weinbauer")){
				delivery_transports.add(winetransport);
			}
		}
		
		model.addAttribute("delivery_transport", delivery_transports);
		
		return "delivery_transport";
	}
}
