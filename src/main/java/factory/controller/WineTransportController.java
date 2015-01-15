package factory.controller;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import factory.model.Department;
import factory.model.Location;
import factory.model.Production;
import factory.model.ProductionADay;
import factory.model.ProductionManagement;
import factory.model.ProductionMonth;
import factory.model.Still;
import factory.model.WineStock;
import factory.model.WineTransport;
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
	 * initialize an array of months and check if there is a leap year
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
		DateTime now = new DateTime().now();
		int left_days = Days.daysBetween(now.toLocalDate(), final_date.toLocalDate()).getDays();
		double max_still_amount = 0;
		
		List<WineTransport> transport_list = new ArrayList<>();
		
		/*
		 * add all transports to a temporary list
		 */
		for(WineTransport wine_transport : wineTransportRepository.findAll()){
			transport_list.add(wine_transport);
		}
		System.out.println("wine_transport " + productionManagementRepository.count());
		
		for(Location location : locationRepository.findAll()){
			for(Department department : location.getDepartments()){	
				if(department.getName().contains("Produktion")){
					
					production = (Production) department;
					
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
							
				double max_production_a_day = (max_still_amount * 0.8) / 2;
				
				productionADay.setWine_amount_for_production(productionADay.getWine_amount_for_production()
						+ wine_amound_at_the_end_of_day);
	
				
				for(WineTransport wine_transport : transport_list){	
					
					LocalDate today = productionADay.getDate();
					LocalDate transport_day_start = wine_transport.getStart_date().toLocalDate();
					LocalDate transport_day_end = wine_transport.getGoal_date().toLocalDate();
						
					if((today.equals(transport_day_end)) && (productionADay.getLocation_name().equals(wine_transport.getGoal()))){

							if(wine_transport.getStarting_point().equals("Weinbauer")){
								productionADay.setWine_delivery_at_that_day(productionADay.getWine_delivery_at_that_day()
										+ wine_transport.getAmount());

							} else {
								productionADay.setWine_transport_at_that_day_in(productionADay.getWine_transport_at_that_day_in()
										+ wine_transport.getAmount());
							}
							
							productionADay.setWine_amount_for_production((productionADay.getWine_amount_for_production() + productionADay.getWine_delivery_at_that_day()
									+ productionADay.getWine_transport_at_that_day_in()) - productionADay.getWine_transport_at_that_day_out());
					}
					
				}
				
				wine_amound_at_the_end_of_day = productionADay.getWine_amount_for_production() - max_production_a_day;
				
				if(wine_amound_at_the_end_of_day < 0){
					wine_amound_at_the_end_of_day = 0;
				}
				
				
			}
			
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
	 * @return the modeling template
	 */
	@RequestMapping(value = "/wineTransport", method = RequestMethod.POST)
	public String createWineTransport(@RequestParam("location_start") String location_start, 
									  @RequestParam("location_goal") String location_goal, 
									  @RequestParam("wine_amount") double wine_amount)
	{
		double wine_amount_in_hectoliter = wine_amount / 100;
		
		if(location_start.equals("")){
			System.out.println("error stage 1");
		} else if(location_goal.equals("")){
			System.out.println("error stage 2");
		} else if(location_start.equals(location_goal)){
			System.out.println("error stage 3");
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
		
		return "wine_delivery_form";
	}
	
	

	/**
	 * start the wine delivery
	 * 
	 * 
	 * @param wine_delivery_amount amount of wine that have to be transported
	 * @param wine_delivery_year goal date : year
	 * @param wine_delivery_month goal date : month
	 * @param wine_delivery_day goal date : day
	 * @param location_goal name of a location where the transport have to travel
	 * @param model Spring element for modeling Java code with help of Thymeleaf on templates
	 * @return the modeling template
	 */
	@RequestMapping(value = "/startWineDelivery", method = RequestMethod.POST)
	public String wine_dilivery(@RequestParam("wine_delivery_amount") int wine_delivery_amount, 
								@RequestParam("wine_delivery_year") int wine_delivery_year, 
								@RequestParam("wine_delivery_month") int wine_delivery_month,
								@RequestParam("wine_delivery_day") int wine_delivery_day, 
								@RequestParam("location_goal") String location_goal, Model model)
	{
		String[][] months = new String[12][2];
		months = initializeMonths(months);
		
		int max_days_of_month = Integer.parseInt(months[wine_delivery_month][1]);
		
		if(wine_delivery_day >= max_days_of_month){
			
			System.out.println("error");
			
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
