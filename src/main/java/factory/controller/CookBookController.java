package factory.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import factory.model.Barrel;
import factory.model.BarrelStock;
import factory.model.Bottle;
import factory.model.BottleStock;
import factory.model.CookBook;
import factory.model.Ingredient;
import factory.model.Recipe;


@Controller
public class CookBookController {
	
	private final CookBook cookbook;
//	private final Stock stock;
//	private Stock2 stock2;
	
	List<Ingredient> mapIngredient = new ArrayList<Ingredient>();
	List<Bottle> mapBottle = new ArrayList<Bottle>();
	List<Barrel> mapBarrel = new ArrayList<Barrel>();
	
	@Autowired 
	public CookBookController(CookBook cookbook)
	{
		this.cookbook = cookbook;
//		this.stock = stock;
//		this.stock2 = stock2;
	}
	
	/*
	 * mapping initialize 
	 */
	@RequestMapping(value = "/cookbook", method = RequestMethod.GET)
	public String Book(Model model) 
	{
		model.addAttribute("recipes", cookbook.findAll());
//		model.addAttribute("stocks", stock.findAll());
//		model.addAttribute("bottleStocks", stock2.findAll());
	
//		System.out.println(cookbook.findAll());
		
		return "cookbook";
	}
	
	
	/*
	 * save new entry
	 */
//	@RequestMapping(value="/seed", params={"save"})
//	public String saveSeedstarter(
//	        final Recipe recipe, final Ingredient ingredient, final BindingResult bindingResult, final ModelMap model) {
//	    if (bindingResult.hasErrors()) {
//	        return "seed";
//	    }
//	    this.mapIngredient.add(ingredient);
//	    this.cookbook.save(recipe);
//	    //model.clear();
//	    return "redirect:/seed";
//	}
	
//    @RequestMapping(value = "add")
//    public String add(Recipe recipe) {
//        return "recipe/add";
//    }
//    
//    @RequestMapping(value = "add", params = {"addOption"})
//    public String addOption(Recipe recipe, BindingResult result) {
//        String name = null;
//		int amount = 0;
//		String unit = null;
//		recipe.getIngredients().add(new Ingredient(name, amount, unit));
//        return "recipe/add";
//    }
//    
//    @RequestMapping(value = "add", method = RequestMethod.POST)
//    public String save(Recipe recipe, BindingResult result) {
//        this.cookbook.save(recipe);
//        return "redirect:/sg/add";
//    }
    
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
		
		cookbook.save(new Recipe(name, mapIngredient));
		mapIngredient = new ArrayList<Ingredient>();
		
		return "redirect:/cookbook";
	}
		
	
	/*
	 * check empty bottles
	 */
	public int checkEmptyBottles(int missedBottles, double maxDestillate, Long id)
	{
		int bottles = 0;
		int missedBottle = missedBottles;
		
//		for(BottleStock bottlestock : stock2.findAll())
//		{
//			for(Bottle bottle : bottlestock.getEmptybottles())
//			{
//				double amount = bottle.getAmount();
//				int quantity_empty = bottlestock.getQuantity_empty();
//				int quantity_full = bottlestock.getQuantity_full();
//				
//				/*
//				 * calculate the necessary bottles
//				 */
//				bottles = (int) (maxDestillate / amount);
//				System.out.println("need-bottles: " + bottles);
//				
//				/*
//				 * check, if there is a lack of empty bottles
//				 * 
//				 * if true: send error message
//				 * else: create new Cognac and update the new resources 
//				 */
//				if(quantity_empty < bottles)
//				{
//					missedBottle = (int) (bottles - quantity_empty);
//					
//				} // /if
//				else
//				{
//					for(BarrelStock barrelstock : stock.findAll())
//					{
//						if(barrelstock.getName().equals("Lager A"))
//						{
//							for(Barrel barrel : barrelstock.getBarrels())
//							{ 
//								for(Recipe selectedRecipe : cookbook.findById(id))
//								{	
//									for(Ingredient ingredient : selectedRecipe.getIngredients())
//									{
//										if(barrel.getContent().equals(ingredient.getName()))
//										{
//											/*
//											 * update barrels
//											 */
//											double newAmount = barrel.getAmount() - ingredient.getAmount();
//											barrel.setAmount(newAmount);
//								
//											/*
//											 * update empty bottles
//											 */
//											bottlestock.setQuantity_empty(quantity_empty - bottles);
//											bottlestock.setQuantity_full(quantity_full + bottles);
//											
//											stock2.save(bottlestock);
//											
//										} // /if
//									} // /for
//								} // /for
//							} // /for
//						} // /if
//					} // /for	
//				} // /else
//
//			} // /for
//		} // /for
		
		return missedBottle;
	}
	
	
	/*
	 * pick a recipe and create a new Cognac, 
	 * if the inventory checks send their okay 
	 */
	@RequestMapping(value="/cookbook/wedding/{id}", method = RequestMethod.GET)
	public String wedding(@PathVariable("id") Long id, Model model)
	{
		int i = 0; 
		int not_enough = 0;
		double maxDestillate = 0;

		/*
		 * calculate, if there is enough ingredients, or not
		 */
//		for(Recipe recipe: cookbook.findById(id))
//		{
//			if(recipe.getId() == id)
//			{
//				
//				int ingredientsCheck = recipe.getIngredients().size();
//				System.out.println("Max_Ingredients: " + ingredientsCheck);
//				
//				
//				model.addAttribute("selected_name", recipe.getName());
//				model.addAttribute("selected_ingredients", recipe.getIngredients());
//				
//				for(BarrelStock barrelstock : stock.findAll())
//				{
//					for(Barrel barrel : barrelstock.getBarrels())
//					{	
//						for(Ingredient ingredient : recipe.getIngredients())
//						{		
//							if(barrel.getContent().equals(ingredient.getName()))
//							{
//								double barrelContentAmount = barrel.getAmount();
//								double ingridientDistillateAmount = ingredient.getAmount();
//								
//								if(ingridientDistillateAmount > barrelContentAmount)
//								{
//									model.addAttribute("not_enough_" + i, "Nicht genug " + ingredient.getName() + " vorhanden."
//											+ " Es fehlen noch " + (ingridientDistillateAmount - barrelContentAmount) + " Liter.");
//									
//									not_enough++;
//									
//								} // /if
//								
//								if((ingridientDistillateAmount < barrelContentAmount) || (ingridientDistillateAmount == barrelContentAmount))
//								{
//									maxDestillate = maxDestillate + ingridientDistillateAmount;
//									System.out.println("maxDestillate: " + maxDestillate);
//								}
//								
//								i++;
//								break;
//								
//							} // /if
//							
////							if(!ingredient.getName().equals(barrel.getContent()))
////							{
////								model.addAttribute("not_exist_" + j, "Die Zutat '" + ingredient.getName() + "' ist nicht vorhanden.");
////								not_enough++;
////								j++;
////								
////								System.out.println("out: " + ingredient.getName().equals(barrel.getContent().isEmpty()));
////								System.out.println("out: " + ingredient.getName());
////								System.out.println("out: " + barrel.getContent());
////							}
//							
//		
//						} // /for
//					} // /for
//						
//				} // /for
//					
//				/*
//				 * if enough distillate then check empty bottles
//				 */
//				if(not_enough == 0)
//				{
//					int missedBottles = 0;
//					
//					if(checkEmptyBottles(missedBottles, maxDestillate, id) > 0)
//					{
//						model.addAttribute("not_enough_bottles",  "Nicht genug leere Flaschen vorhanden. Es fehlen noch " 
//											+ checkEmptyBottles(missedBottles, maxDestillate, id) + " Flaschen.");
//					}
//					
//				} // /if
//			} // /if		
//		} // /for
		
		model.addAttribute("selectedRecipe", cookbook.findById(id));
		model.addAttribute("recipes", cookbook.findAll());
//		model.addAttribute("stocks", stock.findAll());
//		model.addAttribute("bottleStocks", stock2.findAll());
		
		return "cookbook";
	}
	
	
	/*
	 * mapping recipe details
	 */
	@RequestMapping(value="/cookbook/recipe/{id}", method = RequestMethod.GET)
	public String recipeDetails(@PathVariable("id") Long id, Model model)
	{

		model.addAttribute("selectedRecipe", cookbook.findById(id));
		
		model.addAttribute("recipes", cookbook.findAll());
//		model.addAttribute("stocks", stock.findAll());
//		model.addAttribute("bottleStocks", stock2.findAll());
		
		return "cookbook";
	}
	


	
//	@RequestMapping(value = "/cookbook/search", method = RequestMethod.POST)
//	public String searchTerm(@RequestParam("searchTerm") String searchTerm, RedirectAttributes redirectAttrs)
//	{	
//		redirectAttrs.addAttribute("term", searchTerm);
//		return "redirect:/coocbook/search/{term}";
//	}
//	
//	@RequestMapping(value = "/cookbook/search/{term}")
//	public String rearchResult(@PathVariable("term") String searchTerm, ModelMap modelMap, RedirectAttributes redirectAttrs)
//	{		
//		List<Recipe> resultList = new ArrayList<>();
//		for(Recipe recipe : cookbook.findAll())
//		{
//			if(recipe.getName().contains(searchTerm))
//				{
//					resultList.add(recipe);
//				}		
//		}
//		
//		modelMap.addAttribute("results", resultList);
//		
//		return "cookbook/search";
//	}
	
	/*
	 * delete entry
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
		cookbook.delete(id);
		return "redirect:/cookbook";
	}

}