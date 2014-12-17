package factory.controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import factory.model.ArticleRepository;
import factory.model.Barrel;
import factory.model.BarrelStock;
import factory.model.CookBookRepository;
import factory.model.Department;
import factory.model.DepartmentRepository;
import factory.model.Ingredient;
import factory.model.LocationRepository;
import factory.model.Production;
import factory.model.Still;
import factory.model.WineStock;

@Controller
public class DistillationController {
	
	private Timer timer;
	private TimerTask timertask;
	private long distillation = (5) * 2;
//	private long distillation = (1000 * 60 * 60 * 24) * 2;
	private final DepartmentRepository departmentrepository;
	
	
	@Autowired 
	public DistillationController(DepartmentRepository departmentrepository)
	{
		this.departmentrepository = departmentrepository;
	}
	
	@RequestMapping(value = "/distillation", method = RequestMethod.GET)
	public String still(Model model) 
	{
		model.addAttribute("stills", Production.getStills());

		return "distillation";
	}
	
	/*
	 * start timer
	 */
	public void startTimer(long intervall, Still still)
	{
		if(timer == null)
		{
			timer = new Timer();
			timertask = new TimerTask()
			{
				public void run()
				{
					timerAction(still);
				}
			};
			timer.schedule(timertask, 0, intervall);
		}
	}	

	/*
	 * stop timer
	 */
	public void stopTimer()
	{
		if(timer != null)
		{
			timer.cancel();
			timertask.cancel();
			timertask = null;
			timer = null;

		}
	}

	/*
	 * task
	 */
	public void timerAction(Still still)
	{	
		if(distillation == 5)
		{
			still.setStatus_two(false);
		}
		
		if(distillation == 0)
		{
			stopTimer();
			distillation = 5*2; 
			
		}
		
		System.out.println("timer: " + distillation);
		distillation -= 1;
		
	}
	
	public double checkBarrels(int i)
	{
		int still_amount = i;
		double max_barrel_amount = 0;
		double remainder = 0;
		
		for (Barrel barrel : BarrelStock.getBarrels()) 
		{
			if(barrel.getContent().equals(""))
			{
				max_barrel_amount += barrel.getBarrel_amount();
			}
		}
		
		remainder = (still_amount * 100) - max_barrel_amount;
		
		System.out.println("barrels2: " + remainder);

		
		return remainder;
	}
	
	
	@RequestMapping(value = "/distillation/process/{index}", method = RequestMethod.POST)
	public String distillation(Model model, @PathVariable(value="index") int index, WineStock winestock) 
	{
		
		Still still = Production.getStills().get(index - 1);

		/*
		 * check status of still
		 */
		if((still.getStatus_one() == false) || (still.getStatus_two() == false))
		{
			model.addAttribute("error", "Destille " + index + " ist derzeit belegt!");
		}
		else
		{
			model.addAttribute("error", "Destille " + index + " ist derzeit Koral!");
			
			/*
			 * check wine store
			 */
			if(winestock.getAmount() < still.getAmount())
			{
				model.addAttribute("error", "Nicht genug Wein vorhanden. Es fehlen noch" + (still.getAmount() - winestock.getAmount()) + " Hektoliter!");
			}
			else
			{	
				if(checkBarrels(still.getAmount()) > 0)
				{
					model.addAttribute("error", "Nicht genug Fässer vorhanden. Es fehlen noch Fässer für " 
							+ (checkBarrels(still.getAmount()) * 0.01) + " Hektoliter!");
				}
				else
				{
					winestock.setAmount(winestock.getAmount() - still.getAmount());
					still.setStatus_one(false);
					startTimer(1000, still);
				}
			}
		}
		
		model.addAttribute("stills", Production.getStills());

		return "distillation";
	}
}
