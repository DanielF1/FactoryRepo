package factory.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Units;
import org.springframework.beans.factory.annotation.Autowired;
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
import factory.model.DepartmentRepository;
import factory.model.Ingredient;
import factory.model.LocationRepository;
import factory.model.MaxStore;
import factory.model.Recipe;


@Controller
public class CookBookController {
	
	private final CookBookRepository cookbookrepository;

	BarrelStock barrelstock;
	BottleStock bottlestock;
	private final Inventory<InventoryItem> inventory;
	private final ArticleRepository articlerepository;

	private final DepartmentRepository departmentrepository;
	private final LocationRepository locationRepository;


	List<Ingredient> mapIngredient = new ArrayList<Ingredient>();
	
	
	@Autowired 
	public CookBookController(
			CookBookRepository cookbookrepository, 
			DepartmentRepository departmentrepository, 
			LocationRepository locationRepository,
			Inventory<InventoryItem> inventory,
			ArticleRepository articlerepository)
	{
		this.cookbookrepository = cookbookrepository;
		this.departmentrepository = departmentrepository;
		this.locationRepository = locationRepository;
		this.inventory = inventory;
		this.articlerepository = articlerepository;
	}
	
	
	/*
	 * calculate the maximum store of barrel stock
	 */
	public List<MaxStore> calcMaxStore()
	{
		List<MaxStore> maxstorelist = new ArrayList<MaxStore>();
		
		/*
		 * HashMap: return a sorted list 
		 */
		HashMap<String, List<Barrel>> map = new HashMap<String, List<Barrel>>();


		/*
		 * sort all barrels
		 */
//		for(Department departments : departmentrepository.findAll())
		{ 
//			if(departments.getName().contains("BarrelStock"))
			{
				for (Barrel barrel : BarrelStock.getBarrels()) 
				{
					String content = barrel.getContent();
					
					if (!map.containsKey(content)) 
					{
						map.put(content, new ArrayList<Barrel>());
					}
					
					map.get(content).add(barrel);
				}
			}			
		}

		/*
		 * calculate maximum amount of one sort of barrel content
		 */
		for (String key : map.keySet()) 
		{
			
			List<Barrel> list = map.get(key);
			double maxAmount = 0;
			
			for (Barrel barrel: list)
			{
				
				maxAmount += barrel.getBarrel_volume();
			}
			
			MaxStore maxstore = new MaxStore(key, maxAmount);
			
			maxstorelist.add(maxstore);
			
		}
		
		return maxstorelist;
	}
	
	
	/*
	 * mapping initializes 
	 */
	@RequestMapping(value = "/cookbook", method = RequestMethod.GET)
	public String book(Model model) 
	{
		model.addAttribute("recipes", cookbookrepository.findAll());
		model.addAttribute("barrelstock_store", calcMaxStore());
		model.addAttribute("bottlestock_empty", BottleStock.getEmptybottles());
		model.addAttribute("bottlestock_full", BottleStock.getFullbottles());

		return "cookbook";
	}
	

	@RequestMapping(value = "/cookbook/addRecipe", method = RequestMethod.POST)
	public String addRecipe(@RequestParam("name") String name, 
							@RequestParam("ingridientName") String ingredientName,
							@RequestParam("ingridientAmount") int ingridientAmount,
							@RequestParam("ingridientUnit") String ingridientUnit) 
	{
		
		Ingredient i1 = new Ingredient(ingredientName, ingridientAmount, ingridientUnit);
//		Ingredient i2 = new Ingredient(ingredientName2, ingridientAmount2, ingridientUnit2);
//		Ingredient i3 = new Ingredient(ingredientName3, ingridientAmount3, ingridientUnit3);
//		Ingredient i4 = new Ingredient(ingredientName4, ingridientAmount4, ingridientUnit4);
//		Ingredient i5 = new Ingredient(ingredientName5, ingridientAmount5, ingridientUnit5);
		
		List<Ingredient> mapIngredient = new ArrayList<Ingredient>();
				
		mapIngredient.add(i1);
//		map1.add(i2);	
//		map1.add(i3);	
//		map1.add(i4);	
//		map1.add(i5);
		
		cookbookrepository.save(new Recipe(name, mapIngredient));
		mapIngredient = new ArrayList<Ingredient>();
		
		return "redirect:/cookbook";
	}
		
	
	/*
	 * check empty bottles
	 */
	public int checkEmptyBottles(int missedBottles, double maxDestillate, Long id, double selectedBottleAmount)
	{
		int existingBottles = 0;
		int neededBottles = 0;
		int missedBottle = missedBottles;
		
		
		/*
		 * calculate the number of needed bottles that we have in stock
		 */
		for(Bottle bottle : BottleStock.getEmptybottles())
		{
			if(bottle.getAmount() == selectedBottleAmount)
			{
				existingBottles ++;
			}
			
		}

		
		
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
	@RequestMapping(value="/cookbook/wedding/{id}", method = RequestMethod.GET)
	public String wedding(@PathVariable("id") Long id, Model model,	@RequestParam("selected_bottle_amount") double selected_bottle_amount)
	{
		int i = 0; 
		int j = 0;
		int not_enough = 0;
		double maxDestillate = 0;
		double selectedBottleAmount = selected_bottle_amount;

		
		/*
		 * calculate, if there are enough ingredients
		 */
		for(Recipe recipe: cookbookrepository.findById(id))
		{
			if(recipe.getId() == id)
			{
				model.addAttribute("selected_name", recipe.getName());
				model.addAttribute("selected_ingredients", recipe.getIngredients());
				
				for(MaxStore maxstore : calcMaxStore())
				{	
					for(Ingredient ingredient : recipe.getIngredients())
					{		
						if(maxstore.getContent().equals(ingredient.getName()))
						{
							double barrelContentAmount = maxstore.getAmount();
							double ingridientDistillateAmount = ingredient.getAmount();
							
							if(ingridientDistillateAmount > barrelContentAmount)
							{
								model.addAttribute("not_enough_" + i, "Nicht genug " + ingredient.getName() + " vorhanden."
										+ " Es fehlen noch " + (ingridientDistillateAmount - barrelContentAmount) + " Liter.");
								
								not_enough++;
								
							} // /if
							
							if((ingridientDistillateAmount < barrelContentAmount) || (ingridientDistillateAmount == barrelContentAmount))
							{
								maxDestillate = maxDestillate + ingridientDistillateAmount;
							}
							
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
					
			
			/*
			 * if enough distillate then check empty bottles
			 */
			if(not_enough == 0)
			{				
				int missedBottles = 0;
				
				if(checkEmptyBottles(missedBottles, maxDestillate, id, selectedBottleAmount) > 0)
				{
					model.addAttribute("not_enough_bottles",  "Nicht genug leere Flaschen vorhanden. Es fehlen noch " 
										+ checkEmptyBottles(missedBottles, maxDestillate, id, selectedBottleAmount) + " Flaschen.");
				}
				
				if(checkEmptyBottles(missedBottles, maxDestillate, id, selectedBottleAmount) == 0)
				{
					for(MaxStore maxstore : calcMaxStore())
					{ 
						for(Recipe selectedRecipe : cookbookrepository.findById(id))
						{	
							for(Ingredient ingredient : selectedRecipe.getIngredients())
							{
								if(maxstore.getContent().equals(ingredient.getName()))
								{
									/*
									 * update barrels
									 */
									double newAmount = ingredient.getAmount();
//									System.out.println("1: " + newAmount);
									
									while(newAmount > 0)
									{
//										for(Department departments : departmentrepository.findAll())
										{ 
//											if(departments.getName().contains("BarrelStock"))
											{
												for (Barrel barrel : BarrelStock.getBarrels()) 
												{
													if((barrel.getContent().equals(maxstore.getContent())) & (barrel.getBarrel_volume() > 0))
													{
		//												System.out.println("2: " + barrel.getContent());
														double barrelAmount = barrel.getBarrel_volume();
		//												System.out.println("3: " + barrelAmount);
														if((barrelAmount - newAmount) <= 0)
														{
															barrel.setContent_amount(0);
		//													System.out.println("4: " + barrel.getAmount());
		//													barrelstock.saveBarrel(barrel);
		
															newAmount = newAmount - barrelAmount;
		//													System.out.println("5: " + newAmount);
														}
														else
														{
															barrel.setContent_amount(barrelAmount - newAmount);
		
		//													System.out.println("6: " + barrel.getAmount());
		//													barrelstock.saveBarrel(barrel);
															newAmount = 0;
															
															break;
														}
													} // /if
												} // /for
											} // /if
										} // /for
									} // /while
												
							
									/*
									 * update empty and full bottles
									 */
									int bottles = 0;
									bottles = (int) (maxDestillate / selectedBottleAmount);

									List<Object> toRemove = new ArrayList<Object>();

									for(Bottle bottle : BottleStock.getEmptybottles())
									{
										if(bottles > 0)
										{
											if(selectedBottleAmount == bottle.getAmount())
											{
												toRemove.add(bottle);
												
												Bottle b1 = new Bottle(recipe.getName(), selectedBottleAmount);
												BottleStock.getFullbottles().add(b1);
												
										    	for(Article article : articlerepository.findAll())
										    	{
										    		if(article.getName().equals(recipe.getName()))
										    		{
										    			System.out.println(article.getName());
										    			Optional<InventoryItem> item = inventory.findByProductIdentifier(article.getIdentifier());
										    	    	
										    			if(!item.isPresent())
										    				continue;
										    			
										    			InventoryItem inventoryItem = item.get();
										    			System.out.println(inventoryItem);
										    			inventoryItem.increaseQuantity(Units.ONE);
										    			System.out.println(inventoryItem.getQuantity());

										    		}
										    	}

											}
										}
										else
										{
											break;
										}
								
										bottles--;
									}
							
									BottleStock.getEmptybottles().removeAll(toRemove);
									
								} // /if
							} // /for
						} // /for
					} // /for
				} // /if		
			} // /if
		} // /if
		
		model.addAttribute("selectedRecipe", cookbookrepository.findById(id));
		model.addAttribute("recipes", cookbookrepository.findAll());
		model.addAttribute("barrelstock_store", calcMaxStore());
		model.addAttribute("bottlestock_empty", BottleStock.getEmptybottles());
		model.addAttribute("bottlestock_full", BottleStock.getFullbottles());
		
		return "cookbook";
	}
	
	
	/*
	 * mapping recipe details
	 */
	@RequestMapping(value="/cookbook/{id}", method = RequestMethod.GET)
	public String recipeDetails(@PathVariable("id") Long id, Model model)
	{

		model.addAttribute("selectedRecipe", cookbookrepository.findById(id));
	
		model.addAttribute("recipes", cookbookrepository.findAll());
		model.addAttribute("barrelstock_store", calcMaxStore());
		model.addAttribute("bottlestock_empty", BottleStock.getEmptybottles());
		model.addAttribute("bottlestock_full", BottleStock.getFullbottles());
		
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
	@RequestMapping(value="/cookbook/buy/bottles", method = RequestMethod.GET)
	public String buyBottles(	@RequestParam("bottlesToBuyAmount") double bottlesToBuyAmount, 
								@RequestParam("bottlesToBuyNumber") int bottlesToBuyNumber, Model model)
	{	
		for(int i = 0; i < bottlesToBuyNumber; i++)
		{
			Bottle b1 = new Bottle("", bottlesToBuyAmount);
			BottleStock.getEmptybottles().add(b1);
		}

		model.addAttribute("recipes", cookbookrepository.findAll());
		model.addAttribute("barrelstock_store", calcMaxStore());
		model.addAttribute("bottlestock_empty", BottleStock.getEmptybottles());
		model.addAttribute("bottlestock_full", BottleStock.getFullbottles());
		
		return "cookbook";
	}
}