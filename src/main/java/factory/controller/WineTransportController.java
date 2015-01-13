package factory.controller;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import factory.model.Department;
import factory.model.Location;
import factory.model.Production;
import factory.model.ProductionManagement;
import factory.model.ProductionMonth;
import factory.model.Still;
import factory.model.WineStock;
import factory.model.WineTransport;
import factory.model.WineTransportRepository;
import factory.repository.DepartmentRepository;
import factory.repository.LocationRepository;
import factory.repository.ProductionManagementRepository;

@Controller
public class WineTransportController {

	WineStock winestock;
	Production production;
	ProductionMonth production_month;
	ProductionManagement production_management;
	
	int convert_month = 0;
	
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
	

	/*
	 * initialize months
	 */
	private String[][] initializeMonths(String[][] months) 
	{
		months[0][0] = "April"; 	months[0][1] = "30";
		months[1][0] = "Mai";		months[1][1] = "31";
		months[2][0] = "Juni";		months[2][1] = "30";
		months[3][0] = "Juli";		months[3][1] = "31";
		months[4][0] = "August";	months[4][1] = "31";
		months[5][0] = "September";	months[5][1] = "30";
		months[6][0] = "Oktober";	months[6][1] = "31";
		months[7][0] = "November";	months[7][1] = "30";
		months[8][0] = "Dezember";	months[8][1] = "31";
		months[9][0] = "Januar";	months[9][1] = "31";
		months[10][0] = "Februar";	months[10][1] = "28";
		months[11][0] = "März";		months[11][1] = "31";

		
		/*
		 * check, if this year is a leap year
		 */
		if(LocalDate.now().getMonth().ordinal() > 3)
		{
			if(LocalDate.now().plusYears(1L).isLeapYear())
			{
				months[10][0] = "Februar";	months[10][1] = "29";
				
			} else {
				
				months[10][0] = "Februar";	months[10][1] = "28";
			}
		}
		
		return months;	
	}
	
	
	/*
	 * get the actually month and convert them to the same month in array list
	 */
	private int convertActuallyMonth() 
	{
		int actually_month = LocalDate.now().getMonth().getValue();
		
		switch (actually_month) {
		 case 1:  convert_month = 9;
		          break;
		 case 2:  convert_month = 10;
		          break;
		 case 3:  convert_month = 11;
		          break;
		 case 4:  convert_month = 0;
		          break;
		 case 5:  convert_month = 1;
		          break;
		 case 6:  convert_month = 2;
		          break;
		 case 7:  convert_month = 3;
		          break;
		 case 8:  convert_month = 4;
		          break;
		 case 9:  convert_month = 5;
		          break;
		 case 10: convert_month = 6;
		          break;
		 case 11: convert_month = 7;
		          break;
		 case 12: convert_month = 8;
		          break;
		 default: break;
     }
		
		return convert_month;
	}
	
	
	/*
	 * calculate the amount of wine, 
	 * that results through the still production
	 */
	public void processThroughStills(int convert_month, String[][] months)
	{
		/*
		 * calculate the maximum amount of distillate that
		 * the actually number of stills can produce every month
		 */
		double max_still_amount = 0;
		double max_production_a_month = 0;
		double max_stock_amount = 0;
//		ProductionMonth pre_productionMonth = null;
		
		for(Location location : locationRepository.findAll()){
			for(ProductionManagement production_management : productionManagementRepository.findAll()){
				if(location.getName().equals(production_management.getLocation_name())){
					for(Department department : location.getDepartments()){	
						
						if(department.getName().contains("Produktion")){
							
							production = (Production) department;
							
							for(int i = convert_month; i < months.length; i++)
//							for(int i = 1; i < months.length; i++)
							{
								double length_of_month = Double.parseDouble(months[i][1]);
								ProductionMonth production_month = production_management.getProduction_month().get(i);
								
								/*
								 * calculate maximum amount that all stills can produce
								 */
								for(Still still : production.getStills())
								{
									max_still_amount += still.getAmount() * 0.8;
								}
								
								max_production_a_month = (length_of_month / 2) * max_still_amount;
								System.out.println("max_production_a_month " + max_production_a_month);

//								if((production_month.getMax_winestock_amount() - max_production_a_month) < 0){
//									production_month.setMax_winestock_amount(0);
//									max_stock_amount = production_month.getMax_winestock_amount();
//								} else {
//									if(i < 10){
//										ProductionMonth production_month = production_management.getProduction_month().get(i + 1);
//										production_month.setMax_winestock_amount(production_month.getMax_winestock_amount()
//												+ (production_month.getMax_winestock_amount() - max_production_a_month));
//										
//										productionMonth.setMax_winestock_amount(productionMonth.getMax_winestock_amount() - max_production_a_month);	
//										max_stock_amount = max_production_a_month;
//									}
//								}
								
								production_month.setProcessing_through_stills(max_production_a_month);
								
								for(Location loc : locationRepository.findAll()){
									for(Department dep : loc.getDepartments()){	
										if(dep.getName().contains("Weinlager")){
									
											winestock = (WineStock) dep;
											
											if((winestock.getAmount() - max_production_a_month) < 0){
												winestock.setAmount(0);
											} else {
												winestock.setAmount(winestock.getAmount() - max_production_a_month);
											}
										}
									}
								}
								
								production_month.setMax_winestock_amount(winestock.getAmount());
								
								departmentrepository.save(winestock);
								productionManagementRepository.save(production_management);
								System.out.println("productionMonth+ " + production_month.getMax_winestock_amount());
								
								
								max_still_amount = 0;
								max_production_a_month = 0;
								max_stock_amount = 0;
							}
							
//							productionManagementRepository.save(production_management);
							
						}
					}
				}
			}
		}	
	}
	
	

	
	
	/*
	 * mapping wine stock view
	 */
	@RequestMapping(value = "/winestocks", method = RequestMethod.GET)
	public String winestocks(Model model)
	{	
		/*
		 * initialize months
		 */
		String[][] months = new String[12][2];
		months = initializeMonths(months);

		
		/*
		 * get the actually month and convert them to the same month in array list
		 */
		convert_month = convertActuallyMonth();
       

        /*
         * fill a temp list of locations with a production
         */
        List<Object> temp_location_list = new ArrayList<>();
        List<Object> test = new ArrayList<>();
         
		for(Location loc : locationRepository.findAll()){		
			for(Department dep : loc.getDepartments()){
				if(dep.getName().contains("Produktion")){
					temp_location_list.add(loc);
				}
			}
		}

		for(Object location : temp_location_list){
			for(ProductionManagement management : productionManagementRepository.findAll()){
				if(((Location) location).getName().equals(management.getLocation_name())){
					
					test.add(location);
					
					System.out.println("temp_location_list+ " + temp_location_list);		
				}
			}
		}
		
		temp_location_list.removeAll(test);

		
		if(temp_location_list.size() > 0)
		{
			for(Object location : temp_location_list)
			{					
				/*
				 * create and initialize production month
				 */
				List<ProductionMonth> initialize_all = new ArrayList<>();
				for(int i = 0; i < months.length; i++)
				{
					production_month = new ProductionMonth(months[i][0], Integer.parseInt(months[i][1]), 0, 0, 0, 0); 
					initialize_all.add(production_month);
				}
				
				/*
				 * create and initialize production management
				 */
				ProductionManagement production_management = new ProductionManagement(((Location) location).getName(), 
						((Location) location).getAddress(),	((Location) location).getCity(), ((Location) location).getTelefon(), 
						((Location) location).getMail(), initialize_all, LocalDate.now().getYear());
	
				
				productionManagementRepository.save(production_management);	
			}
			
			temp_location_list.clear();
		}	
	
		System.out.println("temp_location_list## " + temp_location_list);
			
		processThroughStills(convert_month, months);
		
		
			for(ProductionManagement production_management : productionManagementRepository.findAll()){
				for(ProductionMonth pro : production_management.getProduction_month()){
					System.out.println("PRO "  + pro.getMax_winestock_amount());
				}
			}
	
		
		model.addAttribute("production_management", productionManagementRepository.findAll());
		
		return "winestocks";
	}


	/*
	 * create wine transport
	 */
	@RequestMapping(value = "/wineTransport", method = RequestMethod.POST)
	public String createWineTransport(@RequestParam("location_start") String location_start, 
									  @RequestParam("location_goal") String location_goal, 
									  @RequestParam("wine_amount") double wine_amount)
	{
		double paresed_wine_amount = wine_amount / 100;
		
		if((location_start != location_goal) && ((location_start != "") || (location_goal != "")))
		{
			boolean transport_is_okay = false;
			
			for(Location location : locationRepository.findAll()){
				if(location.getName().equals(location_start)){
					for(Department department : location.getDepartments()){
						if(department.getName().contains("Weinlager")){
							winestock = (WineStock) department;
			
							convert_month = convertActuallyMonth();
							
							for(ProductionManagement productionManagement : productionManagementRepository.findAll()){
								if(productionManagement.getLocation_name().equals(location.getName())){
									
									ProductionMonth production_month = productionManagement.getProduction_month().get(convert_month);
									
									if(((winestock.getAmount() * 100) - wine_amount) >= 0)
									{
										
										production_month.setWine_transport_amount(production_month.getWine_transport_amount() - paresed_wine_amount);
										production_month.setMax_winestock_amount(production_month.getMax_winestock_amount() - paresed_wine_amount);
										winestock.setAmount(winestock.getAmount() - paresed_wine_amount);
										
										transport_is_okay = true;

										departmentrepository.save(winestock);
										productionManagementRepository.save(productionManagement);
										
										System.out.println("production_month.getMax_winestock_amount()" + production_month.getMax_winestock_amount());
										
									} else {
										System.out.println("nicht genug");
									}
									
								}
							}
						}
					}
				}
			}
	
			if(transport_is_okay){
				for(Location location : locationRepository.findAll()){
					if(location.getName().equals(location_goal)){
						for(Department department : location.getDepartments()){
							if(department.getName().contains("Weinlager")){
								winestock = (WineStock) department;
								
								convert_month = convertActuallyMonth();
								
								for(ProductionManagement productionManagement : productionManagementRepository.findAll()){
									if(productionManagement.getLocation_name().equals(location.getName())){
										
										ProductionMonth production_month = productionManagement.getProduction_month().get(convert_month);
										
										production_month.setWine_transport_amount(production_month.getWine_transport_amount() + paresed_wine_amount);
										production_month.setMax_winestock_amount(production_month.getMax_winestock_amount() + paresed_wine_amount);
										winestock.setAmount(winestock.getAmount() + paresed_wine_amount);
										
										transport_is_okay = false;
										
										departmentrepository.save(winestock);
										productionManagementRepository.save(productionManagement);
										
										System.out.println("production_month.getMax_winestock_amount()" + production_month.getMax_winestock_amount());
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
	
	
	/*
	 * mapping delivery form
	 */
	@RequestMapping(value = "/wine_delivery_form", method = RequestMethod.GET)
	public String wine_dilivery_form(Model model){
		
		List<String> locations = new ArrayList<>();
		String[] months = {"Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"};

		for(Location location : locationRepository.findAll()){
			for(Department department : location.getDepartments()){
				if(department.getName().contains("Weinlager")){
					locations.add(location.getName());
				}
			}	
		}
		
		model.addAttribute("locations", locations);
		model.addAttribute("months", months);
		
		return "wine_delivery_form";
	}
	
	
	/*
	 * start wine delivery
	 */
	@RequestMapping(value = "/startWineDelivery", method = RequestMethod.POST)
	public String wine_dilivery(@RequestParam("wine_delivery_amount") int wine_delivery_amount, 
								@RequestParam("wine_delivery_year") int wine_delivery_year, 
								@RequestParam("wine_delivery_month") String wine_delivery_month,
								@RequestParam("wine_delivery_day") int wine_delivery_day, 
								@RequestParam("location_goal") String location_goal, Model model)
	{
		Location found_location = null;
		String[][] months = new String[12][2];
		months = initializeMonths(months);
		
		for(Location location : locationRepository.findAll()){
			if(location.getName().equals(location_goal)){
				for(Department department : location.getDepartments()){
					if(department.getName().contains("Weinlager")){
						
						winestock = (WineStock) department;
						found_location = location;
						
						winestock.setAmount(winestock.getAmount() + wine_delivery_amount);
						departmentrepository.save(winestock);
						
						System.out.println("winestock.getAmount() " + winestock.getAmount());
					}
				}
			}
		}
		
		ArrayList<String> temp_list = new ArrayList<String>();
		
		if(productionManagementRepository.count() > 0){
			for(ProductionManagement productionManagement : productionManagementRepository.findAll()){
				if(productionManagement.getLocation_name().equals(location_goal)){
		
					for(ProductionMonth production_month : productionManagement.getProduction_month()){
						if(production_month.getName().equals(wine_delivery_month)){
							
							System.out.println("production_month.getWine_delivery_amount() " + production_month.getWine_delivery_amount());
							temp_list.add(location_goal);
							production_month.setWine_delivery_amount(production_month.getWine_delivery_amount() + wine_delivery_amount);
							production_month.setMax_winestock_amount(production_month.getMax_winestock_amount() + wine_delivery_amount);
							
							productionManagementRepository.save(productionManagement);
							System.out.println("production_month.getWine_delivery_amount()+ " + production_month.getWine_delivery_amount());
						
							break;
						}
					}
				}
			}
		}
		System.out.println("temp_list.size() " + temp_list.size());
		
		if(temp_list.size() == 0)
		{
			/*
			 * create and initialize new production months
			 */
			List<ProductionMonth> initialize_all = new ArrayList<>();
			
			for(int i = 0; i < months.length; i++)
			{
				if(months[i][0].equals(wine_delivery_month)){
					ProductionMonth production_month = new ProductionMonth(months[i][0], Integer.parseInt(months[i][1]), 0, wine_delivery_amount, 0, wine_delivery_amount); 
					initialize_all.add(production_month);
				} else {
					ProductionMonth production_month = new ProductionMonth(months[i][0], Integer.parseInt(months[i][1]), 0, 0, 0, 0); 
					initialize_all.add(production_month);
				}
			}
			
			/*
			 * create and initialize new production management
			 */			
			ProductionManagement production_management = new ProductionManagement(found_location.getName(), found_location.getAddress(), found_location.getCity(),
					found_location.getTelefon(), found_location.getMail(), initialize_all, wine_delivery_year);
			
			productionManagementRepository.save(production_management);	
		}
		
		return "redirect:/wine_delivery_form";
	}
}
