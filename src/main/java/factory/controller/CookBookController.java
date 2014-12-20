package factory.controller;

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
import factory.model.BarrelStock;
import factory.model.Bottle;
import factory.model.BottleStock;
import factory.model.CookBookRepository;
import factory.model.Department;
import factory.model.DepartmentRepository;
import factory.model.Employee;
import factory.model.Ingredient;
import factory.model.Location;
import factory.model.LocationRepository;
import factory.model.MaxBottleStore;
import factory.model.MaxStore;
import factory.model.Recipe;


@Controller
@PreAuthorize("hasRole('ROLE_BREWER')")
public class CookBookController {
	
	private final CookBookRepository cookbookrepository;

	BarrelStock barrelstock;
	BottleStock bottlestock;
	private final Inventory<InventoryItem> inventory;
	private final ArticleRepository articlerepository;
	private final LocationRepository locationRepository;


	List<Ingredient> mapIngredient = new ArrayList<Ingredient>();
	
	
	@Autowired 
	public CookBookController(
			CookBookRepository cookbookrepository, 
			BarrelStock barrelstock,
			BottleStock bottlestock,
			DepartmentRepository departmentrepository, 
			LocationRepository locationRepository,
			Inventory<InventoryItem> inventory,
			ArticleRepository articlerepository)
	{
		this.cookbookrepository = cookbookrepository;
		this.barrelstock = barrelstock;
		this.bottlestock = bottlestock;
		this.locationRepository = locationRepository;
		this.inventory = inventory;
		this.articlerepository = articlerepository;
	}
	
	
	/*
	 * calculate the maximum store of barrel stock
	 */
	public List<MaxStore> calcMaxStore(@LoggedIn Optional<UserAccount> userAccount)
	{
		
		List<MaxStore> maxstorelist = new ArrayList<MaxStore>();
		
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
				
				MaxStore maxstore = new MaxStore(key, key1,maxAmount);
			
				maxstorelist.add(maxstore);
			
			}
		}
		
		return maxstorelist;
	}
	
	
	/*
	 * calculate the maximum store of bottle stock
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
	
	
	/*
	 * mapping initializes 
	 */
	@RequestMapping(value = "/cookbook", method = RequestMethod.GET)
	public String book(Model model, @LoggedIn Optional<UserAccount> userAccount) 
	{
		model.addAttribute("recipes", cookbookrepository.findAll());
		model.addAttribute("barrelstock_store", calcMaxStore(userAccount));
		model.addAttribute("bottlestock_store", sortBottle(userAccount));

		return "cookbook";
	}
	

	/*
	 * add recipe
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
							
							@RequestParam("ingredientQuality3") String ingredientQuality3,
							@RequestParam("ingredientAge3") int ingredientAge3,
							@RequestParam("ingredientAmount3") int ingredientAmount3,
							@RequestParam("ingredientUnit3") String ingredientUnit3) 
	{
		
		Ingredient i1 = new Ingredient(ingredientQuality,  ingredientAge,  ingredientAmount,  ingredientUnit);
		Ingredient i2 = new Ingredient(ingredientQuality1, ingredientAge1, ingredientAmount1, ingredientUnit1);
		Ingredient i3 = new Ingredient(ingredientQuality2, ingredientAge2, ingredientAmount2, ingredientUnit2);
		Ingredient i4 = new Ingredient(ingredientQuality3, ingredientAge3, ingredientAmount3, ingredientUnit3);
		
		List<Ingredient> mapIngredient = new ArrayList<Ingredient>();
				
		mapIngredient.add(i1);
		mapIngredient.add(i2);	
		mapIngredient.add(i3);	
		mapIngredient.add(i4);	
		
		cookbookrepository.save(new Recipe(name, mapIngredient));
		
		mapIngredient = new ArrayList<Ingredient>();
		
		return "redirect:/cookbook";
	}
		
	
	/*
	 * check empty bottles
	 */
	public int checkEmptyBottles(int missedBottles, double maxDestillate, Long id, double selectedBottleAmount,
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
							bottlestock = (BottleStock) dep;{
		
							for (Bottle bottle : bottlestock.getBottles()) 
							{
								if (bottle.getName().equals(""))
								{
									if(bottle.getAmount() == selectedBottleAmount)
									{
										existingBottles ++;
									}
								}
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
		neededBottles = (int) (maxDestillate / selectedBottleAmount);

		/*
		 * check, if there is a lack of empty bottles
		 */
		if(existingBottles < neededBottles)
		{
			missedBottle = (int) (neededBottles - existingBottles);
		}

		return missedBottle;
	}
	
	
	/*
	 * pick a recipe and create new Cognac, 
	 * if the inventory checks send their okay 
	 */
	@RequestMapping(value="/wedding/{id}", method = RequestMethod.GET)
	public String wedding(@PathVariable("id") Long id, Model model,	@RequestParam("selected_bottle_amount") double selected_bottle_amount,
			@LoggedIn Optional<UserAccount> userAccount)
	{
		int i = 0; 
		int j = 0;
		int not_enough = 0;
		double maxDestillate = 0;
		double selectedBottleAmount = selected_bottle_amount;

		
		/*
		 * calculate, if there are enough ingredients
		 */
		for(Recipe recipe: cookbookrepository.findAll())
		{
			if(recipe.getId() == id)
			{		
				for(Ingredient ingredient : recipe.getIngredients()){
					System.out.println("ingredient: " + ingredient);
					double ingridientDistillateAmount = ingredient.getAmount();
					
					for(MaxStore maxstore : calcMaxStore(userAccount)){	
						System.out.println("maxstore: " + maxstore);
						if((ingredient.getQuality().equals(maxstore.getQuality())) && (ingredient.getAge() == maxstore.getAge())){
							System.out.println("maxstore.getQuality(): " + maxstore.getQuality());
							System.out.println("ingredient.getQuality(): " + ingredient.getQuality());
							
							System.out.println("maxstore.getAge(): " + maxstore.getAge());
							System.out.println("maxstore.getAge(): " + ingredient.getAge());
							
							double barrelContentAmount = maxstore.getAmount();
							
							System.out.println("barrelContentAmount: " + barrelContentAmount);
							System.out.println("ingridientDistillateAmount: " + ingridientDistillateAmount);
							
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
							
//						if(!maxstore.getContent().contains(ingredient.getName()))
//						{
//							model.addAttribute("not_exist_" + j, "Die Zutat '" + ingredient.getName() + "' ist nicht vorhanden.");
//							not_enough++;
//							j++;
//							
//							System.out.println("out: " + ingredient.getName());
//							System.out.println("out: " + maxstore.getContent());
//							
//
//						}
					} // /for
				} // /for
						
			} // /if
				
			model.addAttribute("selected_name", recipe.getName());
			model.addAttribute("selected_ingredients", recipe.getIngredients());
			
			/*
			 * if enough distillate then check empty bottles
			 */
			if(not_enough == 0)
			{				
				int missedBottles = 0;
				
				/*
				 * missed bottles > 0
				 */
				if(checkEmptyBottles(missedBottles, maxDestillate, id, selectedBottleAmount, userAccount) > 0)
				{
					model.addAttribute("not_enough_bottles",  "Nicht genug leere Flaschen vorhanden. Es fehlen noch " 
								+ checkEmptyBottles(missedBottles, maxDestillate, id, selectedBottleAmount, userAccount) + " Flaschen.");
				}
				
				/*
				 * missed bottles = 0
				 */
				if(checkEmptyBottles(missedBottles, maxDestillate, id, selectedBottleAmount, userAccount) == 0){
					for(MaxStore maxstore : calcMaxStore(userAccount)){ 
						for(Recipe selectedRecipe : cookbookrepository.findById(id)){	
							for(Ingredient ingredient : selectedRecipe.getIngredients()){
								if(maxstore.getQuality().equals(ingredient.getQuality()) && (maxstore.getAge() == ingredient.getAge())){
											
									/*
									 * update barrels
									 */
									double newAmount = ingredient.getAmount();
									
									while(newAmount > 0)
									{
										for(Location loc : locationRepository.findAll()){
											for(Employee e : loc.getEmployees()){
												if(e.getUserAccount() == userAccount.get()){
													for(Department dep : loc.getDepartments()){
														if(dep.getName().contains("Fasslager")){
															barrelstock = (BarrelStock) dep;{
										
															for (Barrel barrel : barrelstock.getBarrels()){
																		
																if((barrel.getQuality().equals(maxstore.getQuality())) && (barrel.getBarrel_volume() > 0))
																{
																	double barrelAmount = barrel.getBarrel_volume();
				
																	if((barrelAmount - newAmount) <= 0)
																	{
																		barrel.setContent_amount(0);
		
					//													barrelstock.saveBarrel(barrel);
					
																		newAmount = newAmount - barrelAmount;
																	}
																	else
																	{
																		barrel.setContent_amount(barrelAmount - newAmount);
		
					//													barrelstock.saveBarrel(barrel);
																		newAmount = 0;
																		
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
									int bottles = 0;
									bottles = (int) (maxDestillate / selectedBottleAmount);

									List<Object> toRemove = new ArrayList<Object>();
									
									for(Location loc : locationRepository.findAll()){
										for(Employee e : loc.getEmployees()){
											if(e.getUserAccount() == userAccount.get()){
												for(Department dep : loc.getDepartments()){
													if(dep.getName().contains("Flaschenlager")){
														bottlestock = (BottleStock) dep;{
															
													
														for(Bottle bottle : bottlestock.getBottles())
														{
															if(bottle.getName().equals(""))
															{
																if(bottles > 0)
																{
																	if(selectedBottleAmount == bottle.getAmount())
																	{
																		toRemove.add(bottle);
												
																		Bottle b1 = new Bottle(recipe.getName(), selectedBottleAmount);
																		bottlestock.getBottles().add(b1);
												
																		for(Article article : articlerepository.findAll())
																		{
																			if(article.getName().equals(recipe.getName()))
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
																} // /if
																else
																{
																	break;
																}
								
																bottles--;
															} // /if
														} // /for
														}
													} // /if
												} // /for
											} // /if
										} // /for
									} // /for
									
									bottlestock.getBottles().removeAll(toRemove);
		
								} // /if
							} // /for
						} // /for
					} // /for
				} // /if		
			} // /if
		} // /for
		
		model.addAttribute("selectedRecipe", cookbookrepository.findById(id));
		model.addAttribute("recipes", cookbookrepository.findAll());
		model.addAttribute("barrelstock_store", calcMaxStore(userAccount));
		model.addAttribute("bottlestock_store", sortBottle(userAccount));

		return "cookbook";
	}
	
	
	/*
	 * mapping recipe details
	 */
	@RequestMapping(value="/cookbook/{id}", method = RequestMethod.GET)
	public String recipeDetails(@PathVariable("id") Long id, Model model, @LoggedIn Optional<UserAccount> userAccount)
	{

		model.addAttribute("selectedRecipe", cookbookrepository.findById(id));
	
		model.addAttribute("recipes", cookbookrepository.findAll());
		model.addAttribute("barrelstock_store", calcMaxStore(userAccount));
		model.addAttribute("bottlestock_store", sortBottle(userAccount));
		
		return "cookbook";
	}
	
	
	/*
	 * delete recipe
	 */
	@RequestMapping(value = "/cookbook/delete/{id}", method = RequestMethod.GET)
	public String deleteTrue(@PathVariable(value="id") Long id, RedirectAttributes redirectAttrs) 
	{	
		String delete = "true";
		redirectAttrs.addAttribute("id", id);
		redirectAttrs.addAttribute("term", delete);
		return "redirect:/cookbook/delete/{id}/{term}";
	}
	
	@RequestMapping(value = "/cookbook/delete/{id}/{term}")
	public String removeRecipe(@PathVariable Long id)
	{		
		cookbookrepository.delete(id);
		return "redirect:/cookbook";
	}

	
	/*
	 * buy bottles
	 */
	@RequestMapping(value="/buy/bottles", method = RequestMethod.GET)
	public String buyBottles(	@RequestParam("bottlesToBuyAmount") double bottlesToBuyAmount, 
								@RequestParam("bottlesToBuyNumber") int bottlesToBuyNumber, Model model, 
								@LoggedIn Optional<UserAccount> userAccount)
	{	
		for(int i = 0; i < bottlesToBuyNumber; i++)
		{
			for(Location loc : locationRepository.findAll()){
				for(Employee e : loc.getEmployees()){
					if(e.getUserAccount() == userAccount.get()){
						for(Department dep : loc.getDepartments()){
							if(dep.getName().contains("Flaschenlager")){
								bottlestock = (BottleStock) dep;{
									
								Bottle b1 = new Bottle("", bottlesToBuyAmount);
								bottlestock.getBottles().add(b1);
								
								}
							} // /if
						} // /for
					} // /if
				} // /for
			} // /for
		} // /for
		
		model.addAttribute("recipes", cookbookrepository.findAll());
		model.addAttribute("barrelstock_store", calcMaxStore(userAccount));
		model.addAttribute("bottlestock_store", sortBottle(userAccount));
		
		return "cookbook";
	}
}