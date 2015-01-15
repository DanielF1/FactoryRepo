package factory.controller;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

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
import factory.model.Employee;
import factory.model.Location;
import factory.model.Production;
import factory.model.Still;
import factory.model.WineStock;
import factory.repository.DepartmentRepository;
import factory.repository.LocationRepository;

@Controller
@PreAuthorize("hasRole('ROLE_BREWER') ||  hasRole('ROLE_SUPERUSER')")
public class DistillationController {

	private BarrelStock barrelstock;
	private WineStock winestock;
	private Production production;
	private final DepartmentRepository departmentrepository;
	private final LocationRepository locationRepository;
	
	Long seconds = 10L;
	
	@Autowired 
	public DistillationController(DepartmentRepository departmentrepository, LocationRepository locationRepository)
	{

		this.departmentrepository = departmentrepository;
		this.locationRepository = locationRepository;
	}

	
	/**
	 * reserve barrels for stills
	 * 
	 * 
	 * @param stillAmount amount of a still in hectoliter
	 * @param userAccount return the logged user account (salespoint)
	 * @param index index of a still in array
	 */
	public void reserveBarrels(double stillAmount, @LoggedIn Optional<UserAccount> userAccount, int index)
	{
		double still_amount = stillAmount;
		
		for(Location loca : locationRepository.findAll()){
			for(Employee em : loca.getEmployees()){
				if(em.getUserAccount() == userAccount.get()){			
					for(Department dep : loca.getDepartments()){
						if(dep.getName().contains("Fasslager")){
							barrelstock = (BarrelStock) dep;

							for (Barrel barrel : barrelstock.getBarrels())
							{
								if(still_amount > 0)
								{
									if(barrel.getQuality().equals(""))
									{
										if (!barrel.getPosition().equals(""))
										{
											barrel.setPosition("");
										}
										barrel.setQuality("reserviert für Destille " + index);
										
										still_amount = still_amount - barrel.getBarrel_volume();
										
										if(stillAmount == 0)
										{
											break;
										}
										
									}
								}
							}	
							
							departmentrepository.save(barrelstock);
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * check the status of still processing
	 * 
	 * 
	 * @param still Class Still
	 * @return still with new set status
	 */
	public Still stillCaseProcess(Still still)
	{
		LocalDateTime still_process_start_time = still.getStill_process_start_time();
		LocalDateTime still_process_end_time = still.getStill_process_end_time();

		/*
		 * =======================================================================
		 *				(start time == null) && (end time == null)
		 * =======================================================================
		 */
		if((still_process_start_time == null) && (still_process_end_time == null))
		{
			still.setStatus_one(0);
			still.setStatus_two(0);
			
			still_process_start_time = LocalDateTime.now().plusYears(1000L);
			still_process_end_time = LocalDateTime.now().plusYears(1000L);
		}

		/*
		 * =======================================================================
		 *		(local time > start time) && (local time < end time - one day)
		 * =======================================================================
		 */
		
		if((LocalDateTime.now().isAfter(still_process_start_time))
				&& (LocalDateTime.now().isBefore(still_process_end_time.minusSeconds(seconds))))
		{
			still.setStatus_one(1);
			still.setStatus_two(0);
		}
			
		/*
		 * =======================================================================
		 *		(local time > start time + one day) && (local time < end time)
		 * =======================================================================
		 */
		if((LocalDateTime.now().isAfter(still_process_start_time.plusSeconds(seconds)))
				&& (LocalDateTime.now().isBefore(still_process_end_time)))
		{
			still.setStatus_one(2);
			still.setStatus_two(1);
		}
		
		/*
		 * =======================================================================
		 *		(local time > start time + one day) && (local time > end time)
		 * =======================================================================
		 */
		if((LocalDateTime.now().isAfter(still_process_start_time.plusSeconds(seconds)))
				&& (LocalDateTime.now().isAfter(still_process_end_time)))
		{
			still.setStatus_one(2);
			still.setStatus_two(2);
		}	
		
		return still;
	}

	
	/**
	 * call the processing status of all stills
	 * 
	 * 
	 * @param userAccount return the logged user account (salespoint)
	 */
	public void checkStillStatus(@LoggedIn Optional<UserAccount> userAccount) 
	{
		for(Location loc : locationRepository.findAll()){
			for(Employee e : loc.getEmployees()){
				if(e.getUserAccount() == userAccount.get()){
					for(Department dep : loc.getDepartments()){
						if(dep.getName().contains("Produktion")){
							production = (Production) dep;

							for(Still still : production.getStills())
							{
								still = stillCaseProcess(still);
							}
							
							departmentrepository.save(production);
							
						} // /if
					} // /for
				} // /if
			} // /for
		} // /for
	}
	
	
	/**
	 * mapping all stills of location where the user is logged in
	 * 
	 * 
	 * @param model Spring element for modeling Java code with help of Thymeleaf on templates
	 * @param userAccount return the logged user account (salespoint)
	 * @return return the modeling template
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
							
							checkStillStatus(userAccount);
							
							model.addAttribute("stills", production.getStills());
						
							return "distillation";
						}
					}
				}
			}
		}
		
		return "distillation";
	}


	/**
	 * check, if there are enough empty barrels for my still
	 * 
	 * 
	 * @param stillAmount the amount of a still
	 * @param userAccount return the logged user account (salespoint)
	 * @return remainder (still amount - amount of free barrels)
	 */
	public double checkBarrels(double stillAmount, @LoggedIn Optional<UserAccount> userAccount)
	{
		double still_amount = stillAmount;
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
		
		System.out.println("remainder: " + remainder);

		return remainder;
	}
	
	
	/**
	 * check status of still
	 * check if there is enough wine in stock
	 * call reserve barrels function
	 * 
	 * 
	 * @param still class Still
	 * @param userAccount return the logged user account (salespoint)
	 * @param model Spring element for modeling Java code with help of Thymeleaf on templates
	 * @param index index of a still in array
	 */
	public void checkStill(Still still, @LoggedIn Optional<UserAccount> userAccount, Model model, int index)
	{
		/*
		 * check status of still
		 */
		if(((still.getStatus_one() == 1) || (still.getStatus_two() == 1)) || ((still.getStatus_one() == 2) || (still.getStatus_two() == 2)))
		{
			model.addAttribute("error", "Destille " + index + " ist derzeit belegt!");
		}
		else
		{
			model.addAttribute("error_green", "Destille " + index + " ist derzeit frei!");

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
									
									break;
								}
					
								if(checkBarrels((still.getAmount() * 0.8), userAccount) > 0)
								{
									model.addAttribute("error", "Nicht genug Fässer vorhanden. Es fehlen noch Fässer für " 
											+ (checkBarrels((still.getAmount() * 0.8),userAccount)) + " Liter!");
									
									break;
									
								} else {
									
									double stillAmount = still.getAmount()* 0.8 * 100 * 0.75;
	
									winestock.setAmount(winestock.getAmount() - (still.getAmount() * 0.8));
									
									still.setStatus_one(1);
									still.setStatus_two(0);
									still.setStill_process_start_time(LocalDateTime.now());
									still.setStill_process_end_time(LocalDateTime.now().plusSeconds(2 * seconds));
									
									departmentrepository.save(production);
									departmentrepository.save(winestock);
									
									reserveBarrels(stillAmount, userAccount, index);
									}
								} // /else
							} // /if
						} // /for
					} // /if
				} // /for
			} // /for
		} // /else
	}
	
	
	/**
	 * start the checks of different cases that have to proof for still to continue process
	 * 
	 * 
	 * @param model Spring element for modeling Java code with help of Thymeleaf on templates
	 * @param index index of a still in array
	 * @param userAccount return the logged user account (salespoint)
	 * @return return the modeling template
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
							model.addAttribute("stills", production.getStills());
							}
						}
					}
				}
			}
		}

		return "distillation";
	}
	

	/**
	 * check still and if still is ready than fill up distillate
	 * 
	 * 
	 * @param quality quality of distillate
	 * @param one status one of still
	 * @param two status two of still
	 * @param index index of a still in array
	 * @param model Spring element for modeling Java code with help of Thymeleaf on templates
	 * @param userAccount return the logged user account (salespoint)
	 * @return return the modeling template
	 */
	@RequestMapping(value = "/distillation/f{index}")
	public String distillationFill( @RequestParam("quality") String quality,
									@RequestParam("one") String one,
									@RequestParam("two") String two,
									@PathVariable(value="index") int index, Model model,
									@LoggedIn Optional<UserAccount> userAccount)
	{
		int status_one = 0;
		int status_two = 0;
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
							final_distillate = ((still.getAmount() * 0.8) * 0.75) * 100;
							
							}
						}
					}
				}
			}
		}
		
		if((status_one == 2) && (status_two == 2))
		{
			System.out.println("1: " + final_distillate);
			
			for(Location loc : locationRepository.findAll()){
				for(Employee e : loc.getEmployees()){
					if(e.getUserAccount() == userAccount.get()){
						for(Department dep : loc.getDepartments()){
							if(dep.getName().contains("Produktion")){
								production = (Production) dep;{
									
								Still still = production.getStills().get(index - 1);	
					
								still.setStatus_one(0);
								still.setStatus_two(0);
								still.setStill_process_start_time(null);
								still.setStill_process_end_time(null);
								
								departmentrepository.save(production);
								}
							}
						}
					}
				}
			}
			
			for(Location loc : locationRepository.findAll()){
				for(Employee e : loc.getEmployees()){
					if(e.getUserAccount() == userAccount.get()){
						for(Department dep : loc.getDepartments()){
							if(dep.getName().contains("Fasslager")){
								barrelstock = (BarrelStock) dep;{
								for (Barrel barrel : barrelstock.getBarrels()){
			
									if(barrel.getQuality().equals("reserviert für Destille " + index))
									{									
										double barrelAmount = barrel.getBarrel_volume();
										
										if((barrelAmount - final_distillate) <= 0)
										{
											double amound = barrel.getBarrel_volume();
											barrel.setContent_amount(amound);
											barrel.setQuality(quality);
											barrel.setManufacturing_date(LocalDate.now());
											barrel.setLastFill(LocalDate.now());
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
								
								model.addAttribute("error_green", "Destille erfolgreich geleert.");
								}
							} // /if
						} // /for
					} // /if
				} // /for
			} // /for
		} // /if

		if((status_one == 0) && (status_two == 0))
		{
			model.addAttribute("error", "Die Destille ist leer.");
		}

		if((status_one == 1) || (status_two == 1))
		{
			model.addAttribute("error", "Die Destillation ist noch im Gang.");
		}
	

		model.addAttribute("stills", production.getStills());
		return "distillation";
	}
	
	
	/**
	 * add a new still
	 * 
	 * 
	 * @param new_still_amount amount of the new still
	 * @param userAccount return the logged user account (salespoint)
	 * @return return the modeling template
	 */
	@RequestMapping(value = "/addNewStill", method = RequestMethod.POST)
	public String addNewStill( @RequestParam("new_still_amount") int new_still_amount,	@LoggedIn Optional<UserAccount> userAccount)
	{
		for(Location loc : locationRepository.findAll()){
			for(Employee e : loc.getEmployees()){
				if(e.getUserAccount() == userAccount.get()){
					for(Department dep : loc.getDepartments()){
						if(dep.getName().contains("Produktion")){
							production = (Production) dep;
							
							Still s1 = new Still(new_still_amount, 0, 0, null, null);
							
							production.getStills().add(s1);
							
							departmentrepository.save(production);
						}
					}
				}
			}
		}
			
		return "redirect:/distillation";
	}
	

	/**
	 * delete still
	 * 
	 * 
	 * @param index index of a still in array
	 * @param userAccount return the logged user account (salespoint)
	 * @param model Spring element for modeling Java code with help of Thymeleaf on templates
	 * @return the modeling template
	 */
	@RequestMapping(value = "/deleteStill/{index}", method = RequestMethod.GET)
	public String deleteStill(@PathVariable(value="index") int index, @LoggedIn Optional<UserAccount> userAccount, Model model) 
	{	
		for(Location loc : locationRepository.findAll()){
			for(Employee e : loc.getEmployees()){
				if(e.getUserAccount() == userAccount.get()){
					for(Department dep : loc.getDepartments()){
						if(dep.getName().contains("Produktion")){
							production = (Production) dep;
							
							Still still = production.getStills().get(index - 1);
							
							if((still.getStatus_one() == 0) && (still.getStatus_two() == 0))
							{
								production.getStills().remove(still);
								
								departmentrepository.save(production);
							} else {
								model.addAttribute("error", "Destille muss vorher geleert werden.");
							}

						}
					}
				}
			}
		}
		
		return "redirect:/distillation";
	}
}
