package factory.controller;


import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.scheduling.config.Task;
import org.springframework.security.access.prepost.PreAuthorize;
=======
>>>>>>> origin/master
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import factory.model.Barrel;
import factory.model.BarrelStock;
import factory.model.Department;
import factory.model.DepartmentRepository;
import factory.model.Employee;
import factory.model.Location;
import factory.model.LocationRepository;
import factory.model.Production;
import factory.model.Still;

@Controller
//@PreAuthorize("hasRole('ROLE_BREWER')")
public class DistillationController {
	
	private Timer timer;
	private TimerTask timertask;
	private long distillation = (5) * 2;
//	private long distillation = (1000 * 60 * 60 * 24) * 2;
	BarrelStock barrelstock;
	private final DepartmentRepository departmentrepository;
	private final LocationRepository locationRepository;
	
	@Autowired 
	public DistillationController(BarrelStock barrelstock, DepartmentRepository departmentrepository, LocationRepository locationRepository)
	{
		this.barrelstock = barrelstock;
		this.departmentrepository = departmentrepository;
		this.locationRepository = locationRepository;
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
	
	public double checkBarrels(int i, @LoggedIn Optional<UserAccount> userAccount)
	{
		int still_amount = i;
		double max_barrel_amount = 0;
		double remainder = 0;
		for(Location loc : locationRepository.findAll()){
			for(Employee e : loc.getEmployees()){
				if(e.getUserAccount() == userAccount.get()){
					for(Department dep : loc.getDepartments()){
						if(dep.getName().contains("Fasslager")){
							barrelstock = (BarrelStock) dep;{
							for (Barrel barrel : barrelstock.getBarrels()) 
		{
			if(barrel.getQuality().equals(""))
			{
				max_barrel_amount += barrel.getBarrel_volume();
			}
		}
							}}}}}}
		
		remainder = (still_amount * 100 * 0.75) - max_barrel_amount;
		
		System.out.println("barrels2: " + remainder);

		
		return remainder;
	}
	
	
	@RequestMapping(value = "/distillation/{index}", method = RequestMethod.POST)
	public String distillation(Model model, @PathVariable(value="index") int index, @LoggedIn Optional<UserAccount> userAccount) 
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
			
			for(Location location : locationRepository.findAll())
			{
				for(Department department : location.getDepartments())
				{
					if(department.getName().contains("Weinlager"))
					{
						if(location.getWineStockDepartment().getAmount() < still.getAmount())
						{
							model.addAttribute("error", "Nicht genug Wein vorhanden. Es fehlen noch " + (still.getAmount() - location.getWineStockDepartment().getAmount()) + " Hektoliter!");
						}
						else
						{	
							if(checkBarrels(still.getAmount(), userAccount) > 0)
							{
								model.addAttribute("error", "Nicht genug Fässer vorhanden. Es fehlen noch Fässer für " 
										+ (checkBarrels(still.getAmount(),userAccount) * 0.01) + " Hektoliter!");
							}
							else
							{
								location.getWineStockDepartment().setAmount(location.getWineStockDepartment().getAmount() - still.getAmount());
//								locationRepository.save(location);
								
								still.setStatus_one(false);
								startTimer(1000, still);
							}
						}
					}
				}
			}
		}
		
		model.addAttribute("stills", Production.getStills());

		return "distillation";
	}
	
	@RequestMapping(value = "/distillation/f{index}")
	public String distillationFill(@RequestParam("quality") String quality, @PathVariable(value="index") int index, Model model)
	{
		Still still = Production.getStills().get(index);
		
//		if((still.getStatus_one() == false) & (still.getStatus_two() == false))
		{
			double final_distillate = (still.getAmount() * 0.75) * 100; 
			System.out.println("1: " + final_distillate);
			for (Barrel barrel : barrelstock.getBarrels()) 
			{
				if(barrel.getQuality().equals(""))
				{
					
					double barrelAmount = barrel.getBarrel_volume();
					
					if((barrelAmount - final_distillate) <= 0)
					{
						double amound = barrel.getBarrel_volume();
						barrel.setContent_amount(amound);
						System.out.println("2: " + barrel.getBarrel_volume());		
						System.out.println("2: " + barrel.getContent_amount());	
						final_distillate = final_distillate - barrelAmount;
					}
					else
					{
						barrel.setContent_amount(barrelAmount - final_distillate);
						System.out.println("3: " + barrel.getContent_amount());
						break;
					}
					System.out.println("1Y: " + final_distillate);
				}
			}
		}
		
		System.out.println(quality);
		model.addAttribute("stills", Production.getStills());
		return "distillation";
	}
}
