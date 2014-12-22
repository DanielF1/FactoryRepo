package factory.controller;


import java.time.LocalDate;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

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

import factory.model.Barrel;
import factory.model.BarrelStock;
import factory.model.Department;
import factory.model.DepartmentRepository;
import factory.model.Employee;
import factory.model.Location;
import factory.model.LocationRepository;
import factory.model.Production;
import factory.model.Still;
import factory.model.WineStock;

@Controller
@PreAuthorize("hasRole('ROLE_BREWER')")
public class DistillationController {
	
	private Timer timer;
	private TimerTask timertask;
	private long distillation = (5) * 2;
//	private long distillation = (1000 * 60 * 60 * 24) * 2;
	private BarrelStock barrelstock;
	private WineStock winestock;
	private Production production;
	private final DepartmentRepository departmentrepository;
	private final LocationRepository locationRepository;
	
	@Autowired 
	public DistillationController(WineStock winestock, BarrelStock barrelstock, Production production, 
			DepartmentRepository departmentrepository, LocationRepository locationRepository)
	{
		this.winestock = winestock;
		this.barrelstock = barrelstock;
		this.production = production;
		this.departmentrepository = departmentrepository;
		this.locationRepository = locationRepository;
	}
	
	
	/*
	 * mapping all stills of currently location
	 */
	@RequestMapping(value = "/distillation", method = RequestMethod.GET)
	public String still(Model model, @LoggedIn Optional<UserAccount> userAccount) 
	{
		for(Location loc : locationRepository.findAll()){
			for(Employee e : loc.getEmployees()){
				if(e.getUserAccount() == userAccount.get()){
					for(Department dep : loc.getDepartments()){
						if(dep.getName().contains("Produktion")){
							production = (Production) dep;
							
							model.addAttribute("stills", production.getStills());
						
							return "distillation";
						}
					}
				}
			}
		}
		
		return "distillation";
	}

	
	/*
	 * stop timer
	 */
	public void stopTimer(Still still)
	{
		System.out.println("still stop: " + still);
		if(timer != null)
		{
			timer.cancel();
			timertask.cancel();
			timertask = null;
			timer = null;
			still.setTimer_stop(true);
			departmentrepository.save(production);
			System.out.println("timer: " + still.isTimer_stop());
		}
	}
	

	/*
	 * task
	 */
	public void timerAction(Still still)
	{	System.out.println("still task: " + still);
		if(distillation == 5)
		{
			still.setStatus_two(false);
			departmentrepository.save(production);
			System.out.println("timer_two: " + still.getStatus_two());
			
		}
		
		if(distillation == 0)
		{
			stopTimer(still);
			distillation = 5 * 2; 		
		}
		
		System.out.println("timer: " + distillation);
		distillation -= 1;	
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
					still.setStatus_one(false);
					departmentrepository.save(production);
					System.out.println("still start: " + still);
				}
			};
			timer.schedule(timertask, 0, intervall);
		}
	}	

	
	/*
	 * check barrels
	 */
	public double checkBarrels(double d, @LoggedIn Optional<UserAccount> userAccount)
	{
		double still_amount = d;
		double max_barrel_amount = 0;
		double remainder = 0;
		
		for(Location loc : locationRepository.findAll()){
			for(Employee e : loc.getEmployees()){
				if(e.getUserAccount() == userAccount.get()){
					for(Department dep : loc.getDepartments()){
						if(dep.getName().contains("Fasslager")){
							barrelstock = (BarrelStock) dep;{
							for (Barrel barrel : barrelstock.getBarrels()){
								
								if(barrel.getQuality().equals(""))
								{
									max_barrel_amount += barrel.getBarrel_volume();
								}
							}
							}
						}
					}
				}
			}
		}
		
		remainder = (still_amount * 100 * 0.75) - max_barrel_amount;
		
		System.out.println("barrels2: " + remainder);

		return remainder;
	}
	
	
	/*
	 * check still
	 */
	public void checkStill(Still still, @LoggedIn Optional<UserAccount> userAccount, Model model, int index)
	{
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
			for(Location loc : locationRepository.findAll()){
				for(Employee e : loc.getEmployees()){
					if(e.getUserAccount() == userAccount.get()){
						for(Department dep : loc.getDepartments()){
							if(dep.getName().contains("Weinlager")){
								winestock = (WineStock) dep;{
						
								if(winestock.getAmount() < (still.getAmount() * 0.8))
								{
									model.addAttribute("error", "Nicht genug Wein vorhanden. Es fehlen noch " 
											+ ((still.getAmount() * 0.8) - winestock.getAmount()) + " Hektoliter!");
								}
								else
								{	
									if(checkBarrels((still.getAmount() * 0.8), userAccount) > 0)
									{
										model.addAttribute("error", "Nicht genug Fässer vorhanden. Es fehlen noch Fässer für " 
												+ (checkBarrels((still.getAmount() * 0.8),userAccount)) + " Liter!");
									}
									else
									{System.out.println("wine 1: " + winestock.getAmount());
										winestock.setAmount(winestock.getAmount() - (still.getAmount() * 0.8));
										departmentrepository.save(winestock);
										startTimer(1000, still);
									}
								} // /else
								}	
							} // /if
						} // /for
					} // /if
				} // /for
			} // /for
		} // /else
	}
	
	
	/*
	 * distillation
	 */
	@RequestMapping(value = "/distillation/{index}", method = RequestMethod.POST)
	public String distillation(Model model, @PathVariable(value="index") int index, @LoggedIn Optional<UserAccount> userAccount) 
	{
		for(Location loc : locationRepository.findAll()){
			for(Employee e : loc.getEmployees()){
				if(e.getUserAccount() == userAccount.get()){
					for(Department dep : loc.getDepartments()){
						if(dep.getName().contains("Produktion")){
							production = (Production) dep;{
								
							Still still = production.getStills().get(index - 1);
							checkStill(still, userAccount, model, index);
							
							departmentrepository.save(production);
							
							model.addAttribute("stills", production.getStills());
							}
						}
					}
				}
			}
		}

		return "distillation";
	}
	
	
	/*
	 * fill
	 */
	@RequestMapping(value = "/distillation/f{index}")
	public String distillationFill( @RequestParam("quality") String quality,
									@RequestParam("one") String one,
									@RequestParam("two") String two,
									@PathVariable(value="index") int index, Model model,
									@LoggedIn Optional<UserAccount> userAccount)
	{
		boolean status_one = false, status_two = false, timer_stop = false;
		double final_distillate = 0;
		
		for(Location loc : locationRepository.findAll()){
			for(Employee e : loc.getEmployees()){
				if(e.getUserAccount() == userAccount.get()){
					for(Department dep : loc.getDepartments()){
						if(dep.getName().contains("Produktion")){
							production = (Production) dep;{
								
							Still still = production.getStills().get(index - 1);
							
							status_one = still.getStatus_one();
							status_two = still.getStatus_two();
							timer_stop = still.isTimer_stop();
							final_distillate = ((still.getAmount() * 0.8) * 0.75) * 100;
							
							}
						}
					}
				}
			}
		}
		System.out.println("still 1: " + status_one);
		System.out.println("still 2: " + status_two);
		
		if((status_one == false) && (status_two == false) && (timer_stop == true))
		{
			System.out.println("1: " + final_distillate);
			
			for(Location loc : locationRepository.findAll()){
				for(Employee e : loc.getEmployees()){
					if(e.getUserAccount() == userAccount.get()){
						for(Department dep : loc.getDepartments()){
							if(dep.getName().contains("Fasslager")){
								barrelstock = (BarrelStock) dep;{
								for (Barrel barrel : barrelstock.getBarrels()){
			
									if(barrel.getQuality().equals(""))
									{									
										double barrelAmount = barrel.getBarrel_volume();
										
										if((barrelAmount - final_distillate) <= 0)
										{
											double amound = barrel.getBarrel_volume();
											barrel.setContent_amount(amound);
											barrel.setQuality(quality);
											barrel.setManufacturing_date(LocalDate.now());
											final_distillate = final_distillate - barrelAmount;
																		
											System.out.println("2: " + barrel.getBarrel_volume());		
											System.out.println("2: " + barrel.getContent_amount());	
										}
										else
										{
											barrel.setContent_amount(final_distillate);
											barrel.setQuality(quality);
											barrel.setManufacturing_date(LocalDate.now());
											
											departmentrepository.save(barrelstock);
											
											System.out.println("3: " + barrel.getContent_amount());
											break;
										}
										System.out.println("1Y: " + final_distillate);
									}
								}
								}
							} // /if
						} // /for
					} // /if
				} // /for
			} // /for
		} // /if
		else
		{
			if((status_one == true) && (status_two == true) && (timer_stop == false))
			{
				model.addAttribute("error", "Die Destille ist leer.");
			}
			else
			{
				model.addAttribute("error", "Die Destillation ist noch im Gang.");
			}
		}
		
		System.out.println(quality);
		model.addAttribute("stills", production.getStills());
		return "distillation";
	}
}
