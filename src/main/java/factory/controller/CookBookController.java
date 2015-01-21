package factory.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Units;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import factory.model.Article;
import factory.model.ArticleRepository;
import factory.model.Barrel;
import factory.model.BarrelForTransport;
import factory.model.BarrelStock;
import factory.model.BarrelTransport;
import factory.model.Bottle;
import factory.model.BottleStock;
import factory.model.Department;
import factory.model.Employee;
import factory.model.Expenditure;
import factory.model.FoundLocation;
import factory.model.Ingredient;
import factory.model.Location;
import factory.model.MaxBarrelStore;
import factory.model.MaxBottleStore;
import factory.model.Recipe;
import factory.model.WineStock;
import factory.repository.BarrelTransportRepository;
import factory.repository.CookBookRepository;
import factory.repository.DepartmentRepository;
import factory.repository.ExpenditureRepository;
import factory.repository.LocationRepository;
import factory.repository.WineTransportRepository;


@Controller
@PreAuthorize("hasRole('ROLE_BREWER') ||  hasRole('ROLE_SUPERUSER')")
public class CookBookController {
	
	BarrelStock barrelstock;
	BottleStock bottlestock;
	WineStock winestock;
	
	private final Inventory<InventoryItem> inventory;
	private final ArticleRepository articlerepository;
	private final LocationRepository locationRepository;
	private final DepartmentRepository departmentrepository;
	private final BarrelTransportRepository barrel_transport_repository;
	private final ExpenditureRepository expenditureRepository;
	private final WineTransportRepository wineTransportRepository;
	private final CookBookRepository cookbookrepository;

	List<Ingredient> mapIngredient = new ArrayList<Ingredient>();
	List<FoundLocation> foundLocation = new ArrayList<FoundLocation>();
	
	Long seconds = 1L;
	
	
	@Autowired 
	public CookBookController(
			CookBookRepository cookbookrepository, 
			LocationRepository locationRepository,
			Inventory<InventoryItem> inventory,
			ArticleRepository articlerepository, 
			DepartmentRepository departmentrepository,
			BarrelTransportRepository barrel_transport_repository,
			ExpenditureRepository expenditureRepository,
			WineTransportRepository wineTransportRepository)
	{
		this.cookbookrepository = cookbookrepository;
		this.locationRepository = locationRepository;
		this.inventory = inventory;
		this.articlerepository = articlerepository;
		this.departmentrepository = departmentrepository;
		this.barrel_transport_repository = barrel_transport_repository;
		this.expenditureRepository = expenditureRepository;
		this.wineTransportRepository = wineTransportRepository;
	}
	

	/**
	 * check if there are arrived barrels
	 */
	public void checkArrivedBarrels()
	{
		for(BarrelTransport barreltransport : barrel_transport_repository.findAll())
		{
			System.out.println(".. " + (LocalDateTime.now().compareTo(barreltransport.getGoal_date())));
			if((LocalDateTime.now().compareTo(barreltransport.getGoal_date()) > 0) && (barreltransport.getArrived() == false))
			{
				for(Location loca : locationRepository.findAll()){
					for(Location locTransport : barreltransport.getGoal()){
						if((loca.getName().equals(locTransport.getName())) && (loca.getAddress().contains(locTransport.getAddress()))){
							for(Department dep : loca.getDepartments()){
								if(dep.getName().contains("Fasslager")){
									barrelstock = (BarrelStock) dep;
									{
										for(BarrelForTransport barrel : barreltransport.getBarrels()){
											
											Barrel b1 = new Barrel(barrel.getAge(), barrel.getQuality(), barrel.getContent_amount(),
												barrel.getManufacturing_date(), barrel.getBarrel_volume(), barrel.getBirthdate_of_barrel(), 
												barrel.getDeath_of_barrel(), barrel.getLastFill(), barrel.getPosition());
												
											barrelstock.getBarrels().add(b1);
										}
									}
								} // /if
							} // /for
							
							barreltransport.setArrived(true);
							barrel_transport_repository.save(barreltransport);
							departmentrepository.save(barrelstock);
							
						} // /if
					}// /for
				} // /for
			} // /if
		} // /for
	}
	
	
	/**
	 * sort and calculate the maximum store of barrel stock for every ingredient
	 * 
	 * 
	 * @param userAccount return the logged user account (salespoint)
	 * @return return a sorted list of ingredient with their maximum amount in that stock
	 */
	public List<MaxBarrelStore> calcMaxStore(@LoggedIn Optional<UserAccount> userAccount)
	{
		
		List<MaxBarrelStore> maxstorelist = new ArrayList<MaxBarrelStore>();
		
		/*
		 * HashMap: return a sorted list 
		 */
		HashMap<String, List<Barrel>> map = new HashMap<String, List<Barrel>>();
		

		/*
		 * sort all barrels
		 */
		for(Location loc : locationRepository.findAll()){
			for(Employee e : loc.getEmployees()){
				if(e.getUserAccount() == userAccount.get()){
					for(Department dep : loc.getDepartments()){
						if(dep.getName().contains("Fasslager")){
							barrelstock = (BarrelStock) dep;{
		
							for (Barrel barrel : barrelstock.getBarrels()) 
							{
								String content = barrel.getQuality();
								
								if (!map.containsKey(content)) 
								{
									map.put(content, new ArrayList<Barrel>());
								}
								
								map.get(content).add(barrel);
							}
								
							}
						} // /if
					} // /for
				} // /if
			} // /for
		} // /for

	
		/*
		 * calculate maximum amount of one sort of barrel content
		 */
		for (String key : map.keySet()) 
		{
			
			List<Barrel> list = map.get(key);
			
			HashMap<Integer, List<Barrel>> alterMap = new HashMap<Integer, List<Barrel>>();
			
			for (Barrel barrel : list) 
			{
				int age = barrel.getAge();
				
				if (!alterMap.containsKey(age)) 
				{
					alterMap.put(age, new ArrayList<Barrel>());
				}
				
				alterMap.get(age).add(barrel);	
			}
			
			for (Integer key1: alterMap.keySet())
			{
				double maxAmount = 0;
				
				for (Barrel barrel: alterMap.get(key1))
				{
					maxAmount += barrel.getContent_amount();
			
				}
				
				MaxBarrelStore maxstore = new MaxBarrelStore(key, key1,maxAmount);
			
				maxstorelist.add(maxstore);
			
			}
		}
		
		return maxstorelist;
	}
	
	

	/**
	 * sort and calculate the maximum store of bottles stock for every bottle amount
	 * 
	 * 
	 * @param userAccount return the logged user account (salespoint)
	 * @return return a sorted list of bottles with their maximum count in that stock
	 */
	public List<MaxBottleStore> sortBottle (@LoggedIn Optional<UserAccount> userAccount)
	{
		List<MaxBottleStore> sortBottle = new ArrayList<MaxBottleStore>();
		HashMap<String, List<Bottle>> map = new HashMap<String, List<Bottle>>();
		
		/*
		 * sort all bottles
		 */
		for(Location loc : locationRepository.findAll()){
			for(Employee e : loc.getEmployees()){
				if(e.getUserAccount() == userAccount.get()){
					for(Department dep : loc.getDepartments()){
						if(dep.getName().contains("Flaschenlager")){
							bottlestock = (BottleStock) dep;{
		
								for (Bottle bottle : bottlestock.getBottles()) 
								{
									String name = bottle.getName();
									
									if (!map.containsKey(name)) 
									{
										map.put(name, new ArrayList<Bottle>());
									}
									
									map.get(name).add(bottle);
								}	
							}
						} // /if
					} // /for
				} // /if
			} // /for
		} // /for

		
		/*
		 * calculate maximum amount of one sort of bottle content
		 */
		for (String key : map.keySet()) 
		{
			List<Bottle> list = map.get(key);
			
			HashMap<Double, List<Bottle>> amountMap = new HashMap<Double, List<Bottle>>();
			
			for (Bottle bottle : list) 
			{
				double amount = bottle.getAmount();
				
				if (!amountMap.containsKey(amount)) 
				{
					amountMap.put(amount, new ArrayList<Bottle>());
				}
				
				amountMap.get(amount).add(bottle);	
			}
			
			for (Double key1: amountMap.keySet())
			{
				int bottleAnzahl = 0;
			
				for (Bottle bottle: amountMap.get(key1))
				{
					bottleAnzahl++;
				}
				
				MaxBottleStore maxBottleStore = new MaxBottleStore(key,bottleAnzahl,key1);
			
				sortBottle.add(maxBottleStore);
			
			}
		}
		
		return sortBottle;
	}
	

	
	/**
	 * mapping the first called template
	 * 
	 * 
	 * @param model Spring element for modeling Java code with help of Thymeleaf on templates
	 * @param userAccount return the logged user account (salespoint)
	 * @return return the modeling template
	 */
	@RequestMapping(value = "/cookbook", method = RequestMethod.GET)
	public String book(Model model, @LoggedIn Optional<UserAccount> userAccount) 
	{
		model.addAttribute("recipes", cookbookrepository.findAll());
		checkArrivedBarrels();
		
		return "cookbook";
	}
	


	/**
	 * add new recipe
	 * 
	 * 
	 * @param name name of recipe
	 * @param ingredientQuality quality of first ingredient
	 * @param ingredientAge age of first ingredient
	 * @param ingredientAmount amount of first ingredient
	 * @param ingredientUnit unit of first ingredient
	 * @param ingredientQuality1 quality of second ingredient
	 * @param ingredientAge1 age of second ingredient
	 * @param ingredientAmount1 amount of second ingredient
	 * @param ingredientUnit1 unit of second ingredient
	 * @param ingredientQuality2 quality of third ingredient
	 * @param ingredientAge2 age of third ingredient
	 * @param ingredientAmount2 amount of third ingredient
	 * @param ingredientUnit2 unit of third ingredient
	 * @param ingredientWater last ingredient (water)
	 * @param ingredientAge3 default value
	 * @param ingredientAmount3 amount of water 
	 * @param ingredientUnit3 unit of water
	 * @return return the modeling template
	 */
	@RequestMapping(value = "/cookbook/addRecipe", method = RequestMethod.POST)
	public String addRecipe(@RequestParam("name") String name, 
							@RequestParam("ingredientQuality") String ingredientQuality,
							@RequestParam("ingredientAge") int ingredientAge,
							@RequestParam("ingredientAmount") int ingredientAmount,

							@RequestParam("ingredientUnit") String ingredientUnit,
							
							@RequestParam("ingredientQuality1") String ingredientQuality1,
							@RequestParam("ingredientAge1") int ingredientAge1,
							@RequestParam("ingredientAmount1") int ingredientAmount1,
							@RequestParam("ingredientUnit1") String ingredientUnit1,
							
							@RequestParam("ingredientQuality2") String ingredientQuality2,
							@RequestParam("ingredientAge2") int ingredientAge2,
							@RequestParam("ingredientAmount2") int ingredientAmount2,
							@RequestParam("ingredientUnit2") String ingredientUnit2,
							
							@RequestParam("ingredientWater") String ingredientWater,
							@RequestParam("ingredientAmount3") int ingredientAmount3,
							@RequestParam("ingredientUnit3") String ingredientUnit3,
							Model model) 
	{
		
		Ingredient i1 = new Ingredient(ingredientQuality,  ingredientAge,  ingredientAmount,  ingredientUnit);
		Ingredient i2 = new Ingredient(ingredientQuality1, ingredientAge1, ingredientAmount1, ingredientUnit1);
		Ingredient i3 = new Ingredient(ingredientQuality2, ingredientAge2, ingredientAmount2, ingredientUnit2);
		Ingredient i4 = new Ingredient(ingredientWater, 		0, 		   ingredientAmount3, ingredientUnit3);
		
		List<Ingredient> mapIngredient = new ArrayList<Ingredient>();
				
		mapIngredient.add(i1);
		mapIngredient.add(i2);	
		mapIngredient.add(i3);	
		mapIngredient.add(i4);	
		
		cookbookrepository.save(new Recipe(name, mapIngredient));
		model.addAttribute("error_green", "Rezept erstellt" );
		model.addAttribute("recipes", cookbookrepository.findAll());

		mapIngredient = new ArrayList<Ingredient>();
		checkArrivedBarrels();
		return "cookbook";
	}
		
	

	/**
	 * check, if there are enough empty bottles 
	 * 
	 * 
	 * @param missedBottles value of a lack of empty bottles
	 * @param maxDestillate amount of distillate that i have to produce
	 * @param waterAmount amount of ingredient "water"
	 * @param selectedBottleAmount amount of selected bottle type
	 * @param userAccount return the logged user account (salespoint)
	 * @return return count of missed empty bottles
	 */
	public int checkEmptyBottles(int missedBottles, double maxDestillate, double waterAmount, double selectedBottleAmount,
								@LoggedIn Optional<UserAccount> userAccount)
	{
		int existingBottles = 0;
		int neededBottles = 0;
		int missedBottle = missedBottles;
		
		
		/*
		 * calculate the number of needed bottles that we have in stock
		 */
		for(Location loc : locationRepository.findAll()){
			for(Employee e : loc.getEmployees()){
				if(e.getUserAccount() == userAccount.get()){
					for(Department dep : loc.getDepartments()){
						if(dep.getName().contains("Flaschenlager")){
							bottlestock = (BottleStock) dep;
		
							for (Bottle bottle : bottlestock.getBottles()) 
							{
								if ((bottle.getName().equals("")) && (bottle.getAmount() == selectedBottleAmount))
								{
									existingBottles ++;
								}
							}
						} // /if
					} // /for
				} // /if
			} // /for
		} // /for

		
		
		/*
		 * calculate the necessary bottles
		 */
		neededBottles = (int) ((maxDestillate + waterAmount) / selectedBottleAmount);

		/*
		 * check, if there is a lack of empty bottles
		 */
		if(existingBottles < neededBottles)
		{
			missedBottle = neededBottles - existingBottles;
		}

		return missedBottle;
	}
	
	
	
	/**
	 * pick a recipe and create new Cognac, if the inventory checks send their okay 
	 * 
	 * 
	 * @param id the id of selected recipe
	 * @param model Spring element for modeling Java code with help of Thymeleaf on templates
	 * @param selected_bottle_amount amount of the selected bottle
	 * @param userAccount return the logged user account (salespoint)
	 * @return return the modeling template
	 */
	@RequestMapping(value="/wedding/{id}", method = RequestMethod.GET)
	public String wedding(@PathVariable("id") Long id, Model model,	@RequestParam("selected_bottle_amount") double selected_bottle_amount,
			@LoggedIn Optional<UserAccount> userAccount)
	{
		int i = 0; 
		int not_enough = 0;
		double maxDestillate = 0;
		double selectedBottleAmount = selected_bottle_amount;
		double waterAmount = 0;
		
		/*
		 * calculate, if there are enough ingredients
		 */
		List<Object> temp = new ArrayList<>();
		
		for(Recipe recipe: cookbookrepository.findAll()){
			if(recipe.getId() == id){
				
				for(Ingredient ingredient : recipe.getIngredients())
				{
					temp.add(ingredient);
					
					if(ingredient.getQuality().equalsIgnoreCase("Wasser"))
					{
						waterAmount = ingredient.getAmount();
					}
				}
				
				
				model.addAttribute("selected_name", recipe.getName());
				model.addAttribute("selected_ingredients", recipe.getIngredients());
				
				for(MaxBarrelStore maxstore : calcMaxStore(userAccount)){			
					for(Ingredient ingredient : recipe.getIngredients()){

						double ingridientDistillateAmount = ingredient.getAmount();
						double barrelContentAmount = maxstore.getAmount();			
						
											
						if((maxstore.getQuality().equals(ingredient.getQuality())) && (maxstore.getAge() == ingredient.getAge()))
						{
							temp.remove(ingredient);

							if(ingridientDistillateAmount > barrelContentAmount)
							{
								model.addAttribute("not_enough_" + i, "Nicht genug Destillat des Alters " + ingredient.getAge() + " vorhanden."
										+ " Es fehlen noch " + (ingridientDistillateAmount - barrelContentAmount) + " Liter.");
								
								not_enough++;
								
							} // /if
							
							if((ingridientDistillateAmount < barrelContentAmount) || (ingridientDistillateAmount == barrelContentAmount))
							{
							 	maxDestillate = maxDestillate + ingridientDistillateAmount;
							} // /if
							
							i++;
							break;
							
						} // /if

					} // /for	
				} // /for
				
				if((temp.size() - 1) >= 0)
				{
					temp.remove(temp.size() - 1);
				}

				model.addAttribute("not_exist", temp);
				
				if(temp.size() > 0)
				{
					double searchContentAmount = 0;
					int e = 0;
					
					for(Object ingredient : temp){
						searchContentAmount = ((Ingredient) ingredient).getAmount();
						
						for(Location location : locationRepository.findAll()){
							for(Department department : location.getDepartments()){
								if(department.getName().contains("Fasslager")){
									barrelstock = (BarrelStock) department;{
									for (Barrel barrel : barrelstock.getBarrels())
									{
										if((barrel.getQuality().equals(((Ingredient) ingredient).getQuality())) 
												&& (barrel.getAge() == ((Ingredient) ingredient).getAge()))
										{
											/*
											 * ingredient amount > barrel content amount
											 */
											if((((Ingredient) ingredient).getAmount() > barrel.getContent_amount()))
											{
												searchContentAmount = searchContentAmount - barrel.getContent_amount();
												
												model.addAttribute("found" + e, "Die Zutat, der Qualität " + ((Ingredient) ingredient).getQuality()
														+ " und des Alters " + ((Ingredient) ingredient).getAge() + ", wurde im Standort "
														+ location.getName() + " gefunden.");
												
												FoundLocation fl = new FoundLocation(location.getName(), 
														((Ingredient) ingredient).getQuality(), ((Ingredient) ingredient).getAge(), ((Ingredient) ingredient).getAmount());
												
												foundLocation.add(fl);
												System.out.println("> " + foundLocation);
												e++;
											}
											
											
											/*
											 * ingredient amount <= barrel content amount
											 */
											if((((Ingredient) ingredient).getAmount() < barrel.getContent_amount()) 
													|| (((Ingredient) ingredient).getAmount() == barrel.getContent_amount()))
											{
												model.addAttribute("found" + e, "Die Zutat, der Qualität " + ((Ingredient) ingredient).getQuality()
														+ " und des Alters " + ((Ingredient) ingredient).getAge() + ", wurde im Standort "
														+ location.getName() + " gefunden.");
												
												FoundLocation fL = new FoundLocation(location.getName(), 
														((Ingredient) ingredient).getQuality(), ((Ingredient) ingredient).getAge(), ((Ingredient) ingredient).getAmount());
												
												foundLocation.add(fL);
												
												System.out.println("<= " + foundLocation);
												searchContentAmount = 0;
												
												e++;
												
											} // /if
										}
									}
									}
								}
							}
						}
					}
				}
					
				/*
				 * if enough distillate then check empty bottles
				 */
				if((not_enough == 0) && (temp.size()  == 0))
				{				
					int missedBottles = 0;
				
					/*
					 * missed bottles > 0
					 */
					if(checkEmptyBottles(missedBottles, maxDestillate, waterAmount, selectedBottleAmount, userAccount) > 0)
					{
						model.addAttribute("not_enough_bottles",  "Nicht genug leere Flaschen vorhanden. Es fehlen noch " 
									+ checkEmptyBottles(missedBottles, maxDestillate, waterAmount, selectedBottleAmount, userAccount) + " Flaschen.");
					}
				
					/*
					 * missed bottles = 0
					 */				
					if(checkEmptyBottles(missedBottles, maxDestillate, waterAmount, selectedBottleAmount, userAccount) == 0){
						
						int bottles = 0;
						bottles = (int) ((maxDestillate + waterAmount) / selectedBottleAmount);
						
						for(MaxBarrelStore maxstore : calcMaxStore(userAccount)){ 
							for(Recipe selectedRecipe : cookbookrepository.findById(id)){	
								for(Ingredient ingredient : selectedRecipe.getIngredients())
								{
									if(maxstore.getQuality().equals(ingredient.getQuality()) && (maxstore.getAge() == ingredient.getAge()))
									{
											
										/*
										 * update barrels
										 */
										double newAmount = ingredient.getAmount();
									
										if(newAmount > 0)
										{
											for(Location loc : locationRepository.findAll()){
												for(Employee e : loc.getEmployees()){
													if(e.getUserAccount() == userAccount.get()){
														for(Department dep : loc.getDepartments()){
															if(dep.getName().contains("Fasslager")){
																barrelstock = (BarrelStock) dep;{
																for (Barrel barrel : barrelstock.getBarrels())
																{		
																	if((barrel.getQuality().equals(ingredient.getQuality())) && (barrel.getAge() == ingredient.getAge()))
																	{
																		double barrelAmount = barrel.getContent_amount();
				
																		if((barrelAmount - newAmount) <= 0)
																		{
																			barrel.setContent_amount(0);
																			barrel.setQuality("");
																						
																			newAmount = newAmount - barrelAmount;
																		}
																		else
																		{
																			barrel.setContent_amount(barrelAmount - newAmount);
																			newAmount = 0;
																			departmentrepository.save(barrelstock);
																			
																			break;
																		}
																	} // /if
																} // /for
																}
															} // /if
														} // /for
													} // /if
												} // /for
											} // /for
										} // /while
												
							
										/*
										 * update bottles
										 */
										List<Object> toRemove = new ArrayList<>();
										List<Bottle> toAdd = new ArrayList<Bottle>();
									
										for(Location loc : locationRepository.findAll()){
											for(Employee e : loc.getEmployees()){
												if(e.getUserAccount() == userAccount.get()){
													for(Department dep : loc.getDepartments()){
														if(dep.getName().contains("Flaschenlager")){
															bottlestock = (BottleStock) dep;{
											
															for(Bottle bottle : bottlestock.getBottles())
															{
																if(bottles > 0)
																{	
																	if((bottle.getName().isEmpty()) && (selectedBottleAmount == bottle.getAmount()))
																	{
																		toRemove.add(bottle);
										
																		Bottle b1 = new Bottle(recipe.getName(), selectedBottleAmount, 0.0);
																		toAdd.add(b1);
											
																		bottles--;

																		for(Article article : articlerepository.findAll())
																		{
																			if((article.getName().equals(recipe.getName())) && (article.getVolumen() == selectedBottleAmount))
																			{
																				Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
										    	    		
																				if(!item.isPresent())
																					continue;
										    			
																				InventoryItem inventoryItem = item.get();
																				inventoryItem.increaseQuantity(Units.ONE);
																				inventory.save(inventoryItem);
																			
																			} // /if
																		} // /for
																		
																	} // /if
																} // /for
															} // /while
															
															bottlestock.getBottles().addAll(toAdd);
															bottlestock.getBottles().removeAll(toRemove);
															departmentrepository.save(bottlestock);
															}
														} // /if
													} // /for
												} // /if
											} // /for
										} // /for
				
									} // /if
								} // /for
							} // /for
						} // /for
					} // /if		
				} // /if
		
			} // /if
		} // /for
		checkArrivedBarrels();
		model.addAttribute("selectedRecipe", cookbookrepository.findById(id));
		model.addAttribute("recipes", cookbookrepository.findAll());

		return "cookbook";
	}
	
	

	/**
	 * mapping recipe details if that one is selected 
	 * 
	 * 
	 * @param id the id of selected recipe
	 * @param model Spring element for modeling Java code with help of Thymeleaf on templates
	 * @param userAccount return the logged user account (salespoint)
	 * @return return the modeling template
	 */
	@RequestMapping(value="/cookbook/{id}", method = RequestMethod.GET)
	public String recipeDetails(@PathVariable("id") Long id, Model model, @LoggedIn Optional<UserAccount> userAccount)
	{
		model.addAttribute("selectedRecipe", cookbookrepository.findById(id));
		model.addAttribute("recipes", cookbookrepository.findAll());
	
		return "cookbook";
	}
	
	

	/**
	 * create new path for delete a recipe
	 * 
	 * 
	 * @param id the id of selected recipe
	 * @param redirectAttrs Spring element for redirecting the URL path
	 * @return new URL path
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteTrue(@PathVariable(value="id") Long id, RedirectAttributes redirectAttrs) 
	{	
		String delete = "true";
		redirectAttrs.addAttribute("id", id);
		redirectAttrs.addAttribute("term", delete);
		return "redirect:/{id}/{term}";
	}
	
	
	
	/**
	 * delete selected recipe
	 * 
	 * 
	 * @param id the id of selected recipe
	 * @return return the modeling template
	 */
	@RequestMapping(value = "/{id}/{term}")
	public String removeRecipe(@PathVariable Long id,Model model)
	{		
		cookbookrepository.delete(id);
		checkArrivedBarrels();
		model.addAttribute("error_green", "Rezept wurde gelöscht" );
		model.addAttribute("recipes", cookbookrepository.findAll());
		return "cookbook";
//		return "redirect:/cookbook";
	}

	

	/**
	 * function for buy bottles
	 * 
	 * 
	 * @param bottlesToBuyAmount amount of the bottle
	 * @param bottlesToBuyNumber count of bottles
	 * @param model Spring element for modeling Java code with help of Thymeleaf on templates
	 * @param userAccount return the logged user account (salespoint)
	 * @return return the modeling template
	 */
	@RequestMapping(value="/buy/bottles", method = RequestMethod.GET)
	public String buyBottles(	@RequestParam("bottlesToBuyAmount") double bottlesToBuyAmount, 
								@RequestParam("bottlesToBuyNumber") int bottlesToBuyNumber, Model model, 
								@LoggedIn Optional<UserAccount> userAccount)
	{	
		double price = 0;
		double totalprice = 0;
		
	
		for(Location loc : locationRepository.findAll()){
			for(Employee e : loc.getEmployees()){
				if(e.getUserAccount() == userAccount.get()){
					for(Department dep : loc.getDepartments()){
						if(dep.getName().contains("Flaschenlager")){
							bottlestock = (BottleStock) dep;
							
							for(int i = 0; i < bottlesToBuyNumber; i++)
							{
								if(bottlesToBuyAmount == 1){
									price = 0.1;
								}else if(bottlesToBuyAmount == 0.7){
									price = 0.07;
								}else if(bottlesToBuyAmount == 0.3){
									price = 0.03;
								}
								totalprice += price;
								
								
								Bottle b1 = new Bottle("", bottlesToBuyAmount, price);
								bottlestock.getBottles().add(b1);
								departmentrepository.save(bottlestock);
								
							} // /if
						} // /for
					} // /if
				} // /for
			} // /for
		} // /for
		
		totalprice = (double)Math.round(totalprice * 100) / 100;
		expenditureRepository.save(new Expenditure(LocalDate.now(), totalprice, "Flaschenbestellung"));
		
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

		if (bottlesToBuyNumber == 1)
		{
		model.addAttribute("error_green", "Eine Flasche mit dem Volume " + bottlesToBuyAmount + " wurde hinzugefügt." );}
		else {
			model.addAttribute("error_green", bottlesToBuyNumber +" Flaschen mit dem Volume " + bottlesToBuyAmount + " wurde hinzugefügt." );
		}
		model.addAttribute("recipes", cookbookrepository.findAll());
		checkArrivedBarrels();
		return "cookbook";
	}
	
	
	/**
	 * create and send a transport of found ingredients from other location
	 * 
	 * 
	 * @param userAccount return the logged user account (salespoint)
	 * @return return the modeling template
	 */
	@RequestMapping(value = "/createTransport")
	public String transportIngredients(@LoggedIn Optional<UserAccount> userAccount, Model model)
	{

	if(foundLocation.size() > 0){
		for(Location loc : locationRepository.findAll()){
			for(FoundLocation fl : foundLocation){
				if(loc.getName().equals(fl.getLocation())){
					
					double searchAmount = fl.getAmount();					
					List<BarrelForTransport> barrelsForTransport = new ArrayList<>();
					List<Location> starting_point = new ArrayList<>();
					List<Location> goal = new ArrayList<>();
					LocalDateTime start_date = LocalDateTime.now();
					LocalDateTime goal_date = LocalDateTime.now().plusSeconds(seconds);
					
					/*
					 * prepare barrels for transport
					 */
					for(Department dep : loc.getDepartments()){
						if(dep.getName().contains("Fasslager")){
							barrelstock = (BarrelStock) dep;{
							
							for(Barrel barrel : barrelstock.getBarrels()){
								if(barrel.getQuality().equals(fl.getQuality()) && (barrel.getAge() == fl.getAge())){
									
									if(searchAmount > 0)
									{
										searchAmount = searchAmount - barrel.getContent_amount();
										
										BarrelForTransport b1 = new BarrelForTransport(barrel.getAge(), barrel.getQuality(), barrel.getContent_amount(),
												barrel.getManufacturing_date(), barrel.getBarrel_volume(), barrel.getBirthdate_of_barrel(), 
												barrel.getDeath_of_barrel(), barrel.getLastFill(), barrel.getPosition());
										
										barrelsForTransport.add(b1);
										barrelstock.getBarrels().remove(barrel);
										
										departmentrepository.save(barrelstock);
									}

									System.out.println("3 " + barrelstock.getBarrels().size());
									
									break;
								
								} // /if
							} // /for
							}	
						} // /if
					} // /for
					
					
					/*
					 * add starting point for transport
					 */
					starting_point.add(loc);
					
					
					/*
					 * add goal for transport
					 */
					for(Location location : locationRepository.findAll()){
						for(Employee e : location.getEmployees()){
							if(e.getUserAccount() == userAccount.get()){
								goal.add(location);
							}
						}
					}
					
					/*
					 * create a new transport
					 */
					BarrelTransport barreltransport = new BarrelTransport(starting_point, goal, barrelsForTransport, start_date , goal_date);
					model.addAttribute("error_green", "Transport erstellt.");
					model.addAttribute("recipes", cookbookrepository.findAll());
					barrel_transport_repository.save(barreltransport);
				}	
			}
		}
	} else {
		model.addAttribute("error", "Keine benötigten Zutaten in anderen Standorten gefunden, die transportiert werden könnten.");
		model.addAttribute("recipes", cookbookrepository.findAll());
		}

		model.addAttribute("error_green", "Transport erstellt");
		model.addAttribute("recipes", cookbookrepository.findAll());
		return "cookbook";
	}
	
	
	/**
	 * mapping the list of all barrel transports
	 * 
	 *  
	 * @param model Spring element for modeling Java code with help of Thymeleaf on templates
	 * @param userAccount return the logged user account (salespoint)
	 * @return the modeling template
	 */
	@RequestMapping(value = "/barrel_transport", method = RequestMethod.GET)
	public String barrel_transport(Model model, @LoggedIn Optional<UserAccount> userAccount)
	{
		checkArrivedBarrels();
		
		model.addAttribute("barrel_transport", barrel_transport_repository.findAll());
		
		return "barrel_transport";
	}

	
	/**
	 * mapping the wine stock
	 * 
	 * 
	 * @param model Spring element for modeling Java code with help of Thymeleaf on templates
	 * @param userAccount userAccount return the logged user account (salespoint)
	 * @return the modeling template
	 */
	@RequestMapping(value = "/stocks", method = RequestMethod.GET)
	public String stocks(Model model, @LoggedIn Optional<UserAccount> userAccount)
	{
		checkArrivedBarrels();
		
		for(Location loc : locationRepository.findAll()){
			for(Employee e : loc.getEmployees()){
				if(e.getUserAccount() == userAccount.get()){
					for(Department dep : loc.getDepartments()){
						if(dep.getName().contains("Weinlager")){
							winestock = (WineStock) dep;{
							
								model.addAttribute("wine_store", winestock.getAmount());
							}
						} // /if
					} // /for
				} // /if
			} // /for
		} // /for
			
		checkArrivedBarrels();
		
		model.addAttribute("barrelstock_store", calcMaxStore(userAccount));
		model.addAttribute("bottlestock_store", sortBottle(userAccount));
		
		return "stocks";
	}
}