package factory.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import factory.model.AdminTasksManager;
import factory.model.Employee;
import factory.model.EmployeeRepository;
import factory.model.ExpenditureRepository;
import factory.model.IncomeRepository;
import factory.model.Location;
import factory.model.LocationRepository;


@Controller
@PreAuthorize("hasRole('ROLE_ADMIN') ||  hasRole('ROLE_SUPERUSER')")
class AdminController {

	private final LocationRepository locationRepository;
	private final EmployeeRepository employeeRepository;
	private final AdminTasksManager adminTasksManager;
	private final ExpenditureRepository expenditureRepository;
	private final IncomeRepository incomeRepository;
	
	@Autowired
	public AdminController(	LocationRepository locationRepository, 
							EmployeeRepository employeeRepository,  
							AdminTasksManager adminTasksManager,
							ExpenditureRepository expenditureRepository,
							IncomeRepository incomeRepository) {

		this.locationRepository = locationRepository;
		this.employeeRepository = employeeRepository;
		this.adminTasksManager = adminTasksManager;
		this.expenditureRepository = expenditureRepository;
		this.incomeRepository = incomeRepository;
	}


		/**
		 * Mapping einer Liste von allen Locations, die im Location-Repository gespeichert sind
		 * 
		 * @return HTML-Seite, auf der alle relevanten Informationen angezeigt werden
		 */
	 	@RequestMapping(value = "/adminLocList", method = RequestMethod.GET)
	 	public String standortUebersicht(ModelMap modelMap){
	    	
	 		modelMap.addAttribute("locations", locationRepository.findAll());
		return "adminLocList";
		}

		/**
		 * Mapping für Formular zur Erstellung einer neuen Location
		 * 		
		 * @param location Objekt der Klasse Location wird übergeben für Validierung
		 * @return HTML-Seite, auf der die Informationen abgefragt werden
		 */
		@RequestMapping(value="/addLocation", method=RequestMethod.GET)
	    public String addLocation(Location location) {
	        return "addLocation";
	    }
	
		/**
		 * Mapping für Validierung und Abspeicherung eines neuen Location-Objektes
		 * 
		 * @param location Objekt, das überprüft wird
		 * @param bindingResult Ergebnis der Validierung
		 * @param name Name der neuen Location
		 * @param address Straße der neuen Location
		 * @param city PLZ/Ort der neuen Location
		 * @param telefon Telefonnummer der neuen Location
		 * @param mail Mail der neuen Location
		 * @return Bei fehlerhafter Eingabe Umleitung auf Formular, sonst Umleitung auf Locationübersicht
		 */
	    @RequestMapping(value="/addLocation", method=RequestMethod.POST)
	    public String addLocation(		@Valid Location location,
	    								BindingResult bindingResult,
	    								@RequestParam ("name") String name,
	    								@RequestParam ("address") String address,
	    								@RequestParam ("city") String city,
	    								@RequestParam ("telefon") String telefon,
	    								@RequestParam ("mail") String mail) {
	    	
	    	if (bindingResult.hasErrors()) {
	            return "addLocation";
	        }
	    	
	    	adminTasksManager.addLocation(name, address, city, telefon, mail);
	    		    	
	    	return "redirect:/adminLocList";
	    }
	    
	    /**
	     * Mapping für Formular zur Bearbeitung einer bestehenden Location
	     * 
	     * @param id Identifier der Location
	     * @param location Objekt der Klasse Location für Validierung
	     * @return HTML-Seite, auf der die Informationen abgefragt werden
	     */
	    @RequestMapping(value="/editLocation/{id}", method = RequestMethod.GET)
		public String editLocations(@PathVariable Long id, Model model, Location location){
			
			model.addAttribute("location" ,locationRepository.findOne(id));
			
			return "editLocation";
		}
		
	    /**
	     * Mapping für Validierung und Abspeicherung eines bestehenden Location-Objektes
	     * 
	     * @param location Objekt, das überprüft wird
	     * @param bindingResult Ergebnis der Validierung
		 * @param name Name der Location
		 * @param address Straße der Location
		 * @param city PLZ/Ort der Location
		 * @param telefon Telefonnummer der Location
		 * @param mail Mail der Location
	     * @param id Identifier der Location
	     * @return Umleitung auf Locationübersicht
	     */
		@RequestMapping(value="/editLocation", method = RequestMethod.POST)
		public String editLocation(	@Valid Location location,
									BindingResult bindingResult,
									@RequestParam("name") String name,
									@RequestParam("address") String address,
									@RequestParam("city") String city,
									@RequestParam("telefon")String telefon,
									@RequestParam("mail") String mail,
									@RequestParam("id") Long id){
			
			if (bindingResult.hasErrors()) {
	            return "editLocation";
	        }
			adminTasksManager.editLocation(name, address, city, telefon, mail, id);
			
			return "redirect:/adminLocList";
		}

		/**
		 * Mapping um Location zu löschen
		 * 
		 * @param id Identifier der Location die gelöscht werden soll
		 * @return Umleitung auf Locationübersicht
		 */
		@RequestMapping(value="/deleteLocation/{id}", method = RequestMethod.GET)
		public String deleteLocation(@PathVariable Long id, Model model){
			
			adminTasksManager.deleteLocation(id);
			
			return "redirect:/adminLocList";
		}
		
		
		/**
		 * Mapping für Übersicht der Employees einer Location
		 * 
		 * @param id Identifier der Location
		 * @return HTML-Seite mit Übersicht der Employees
		 */
		 @RequestMapping(value="/employees/{id}", method = RequestMethod.GET)
		 public String editEmployees(@PathVariable Long id, Model model){	
			
			model.addAttribute("location", locationRepository.findOne(id));
			 
			return "employees";
			}
		
		/**
		 * Mapping für Formular zur Bearbeitung eines Employee
		 * 
		 * @param id Identifier des Employee
		 * @param employee Objekt der Klasse Employee für Validierung
		 * @return HTML-Seite, auf der die Informationen abgefragt werden
		 */
		@RequestMapping(value="/editEmployee/{id}", method = RequestMethod.GET)
		public String editEmployee(@PathVariable Long id, Model model, Employee employee){
				
			model.addAttribute("employee", employeeRepository.findOne(id));
			
			return "editEmployee";
		} 
		
		/**
		 * Mapping für Validierung und Abspeicherung eines bestehenden Employee-Objektes
		 * 
		 * @param employee Objekt, das überprüft wird
		 * @param bindingResult Ergebnis der Validierung
		 * @param username Benutzername des Employee
		 * @param password Passwort des Employee
		 * @param workplace Arbeitsplatz des Employee
		 * @param familyname Familienname des Employee
		 * @param firstname Vorname des Employee
		 * @param salary Gehalt des Employee
		 * @param mail E-Mail des Employee 
		 * @param address Addresse des Employee
		 * @return Bei fehlerhafter Eingabe Umleitung auf Formular, sonst Umleitung auf Employeeübersicht
		 */
		@RequestMapping(value="/editEmployee", method = RequestMethod.POST)
		public String editEmployee(	@Valid Employee employee,
									BindingResult bindingResult,
									@RequestParam("username") String username,
									@RequestParam("password") String password,
									@RequestParam("workplace") String workplace,
									@RequestParam("familyname") String familyname,
									@RequestParam("firstname") String firstname,
									@RequestParam("salary") String salary,
									@RequestParam("mail") String mail,
									@RequestParam("address") String address
									){
			
			if (bindingResult.hasErrors()) {
	            return "editEmployee";
	        }
			adminTasksManager.editEmployee(username, familyname, firstname, salary, mail, address);
			
			return "redirect:/employeeList";
			}
		 
		/**
		 * Mapping für Formular zur Erstellung eines neuen Employee-Objektes
		 * 	
		 * @param employee Objekt, das überprüft wird
		 * @return HTML-Seite, auf der die Informationen abgefragt werden
		 */
		@RequestMapping(value="/addEmployee", method=RequestMethod.GET)
	    public String addEmployee( Model model, Employee employee) {
			
			model.addAttribute("locations", locationRepository.findAll());
	        
			return "addEmployee";
	    }
		
		/**
		 * Mapping für Validierung und Abspeicherung eines neuen Employee-Objektes
		 * 
		 * @param employee Objekt das überprüft wird
		 * @param bindingResult Ergebnis der Validierung
		 * @param username Benutzername des neuen Employee
		 * @param password Passowrt des neuen Employee
		 * @param location Standort für den neuen Employee
		 * @param workplace Arbeitsplatz des neuen Employee
		 * @param familyname Familienname des neuen Employee
		 * @param firstname Vorname des neuen Employee
		 * @param salary Gehalt des neuen Employee
		 * @param mail E-Mail des neuen Employee
		 * @param address Addresse des neuen Employee
		 * @return Bei fehlerhafter Eingabe Umleitung auf Formular, sonst Umleitung auf Employeeübersicht
		 */
		@RequestMapping(value="/addEmployee", method=RequestMethod.POST)
	    public String addedEmployee(	@Valid Employee employee,
	    								BindingResult bindingResult,
	    								@RequestParam ("username") String username,
	    								@RequestParam ("password") String password,
										@RequestParam ("location") String location,
	    								@RequestParam ("workplace") String workplace,
	    								@RequestParam ("familyname") String familyname,
	    								@RequestParam ("firstname") String firstname,
	    								@RequestParam ("salary") String salary,
	    								@RequestParam ("mail") String mail,
	    								@RequestParam ("address") String address,
	    								Model model) {
	    	
			model.addAttribute("locations", locationRepository.findAll());
	
			if (bindingResult.hasErrors()) {
	            return "addEmployee";
	        }
			adminTasksManager.addEmployee(username, password, location, workplace, familyname, firstname, salary, mail, address);
			
	    	return "redirect:/employeeList";
	    }
	
		/**
		 * Mapping für Übersicht der Departments einer Location
		 * 
		 * @param id Identifier der Location
		 * @return HTML-Seite mit Übersicht der Departments
		 */
		@RequestMapping(value="/editDepartments/{id}", method = RequestMethod.GET)
		public String editDepartments(@PathVariable Long id, Model model){	
			
			model.addAttribute("location", locationRepository.findOne(id));
			
			return "editDepartments";
			}
	
		/**
		 * Mapping für Validierung und Abspeicherung eines neuen Department-Objektes
		 * 
		 * @param id Identifier der Location
		 * @param sort Department-Bezeichnung
		 * @return Umleitung auf Locationübersicht
		 */
		@RequestMapping(value="/addDepartment", method=RequestMethod.POST)
		public String addedDepartment(	@RequestParam ("id") Long id,
		   								@RequestParam ("sort") String sort) {
						
			String result = adminTasksManager.addDepartment(id, sort);
				
			return result;
		}

		
		/**
		 * Mapping einer Liste aller Employees, die im Employee-Repository gespeichert sind
		 * 
		 * @return HTML-Seite auf der alle relevanten Informationen angezeigt werden
		 */
	    @RequestMapping(value = "/employeeList", method = RequestMethod.GET)
		public String employeeList(ModelMap modelMap){
	    	
	    	modelMap.addAttribute("employees", employeeRepository.findAll());
	    	
			return "employeeList";
		}
  
	    /**
	     * Mapping um Employee zu löschen
	     * 
	     * @param id Identifier des zu löschenden Employee
	     * @return Umleitung auf Employeeübersicht
	     */
	    @RequestMapping(value = "/dismissEmployee/{id}", method = RequestMethod.GET)
		public String dismissEmployee(@PathVariable Long id){
	    	
	    	adminTasksManager.dismissEmployee(id);
	    	
			return "redirect:/employeeList";
		}
	    
	    /**
	     * Mapping für Übersicht der Einnahmen und Ausgaben
	     * 
	     * @return HTML-Seite auf der alle relevanten Informationen angezeigt werden
	     */
	    @RequestMapping(value = "/accountancy", method = RequestMethod.GET)
		public String accountancyOverview(ModelMap modelMap){

	    	double income = adminTasksManager.summUpIncome();
	    	double expenditure = adminTasksManager.summUpExpenditure();
	    	
	    	modelMap.addAttribute("income", income);
	    	modelMap.addAttribute("expenditure", expenditure);
	    	
			return "accountancy";
		}
	    
	    /**
	     * Mapping für Übersicht aller Incomes, die im Income-Repository gespeichert sind
	     * 
	     * @return HTML-Seite auf der alle relevanten Informationen angezeigt werden
	     */
	    @RequestMapping(value = "/income", method = RequestMethod.GET)
		public String incomeOverview(ModelMap modelMap){

	    	modelMap.addAttribute("income", incomeRepository.findAll());
	    	
			return "income";
		}
	    
	    /**
	     * Mapping für Übersicht aller Expenditures, die im Expenditure-Repository gespeichert sind
	     * 
	     * @return HTML-Seite auf der alle relevanten Informationen angezeigt werden
	     */
	    @RequestMapping(value = "/expenditure", method = RequestMethod.GET)
		public String expenditureOverview(ModelMap modelMap){
	    	
	    	modelMap.addAttribute("expenditures", expenditureRepository.findAll());
	    	
			return "expenditure";
		}
}
