package factory.controller;

import java.util.List;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import factory.model.AdminTasksManager;
import factory.model.Employee;
import factory.model.Expenditure;
import factory.model.Location;
import factory.repository.EmployeeRepository;
import factory.repository.ExpenditureRepository;
import factory.repository.IncomeRepository;
import factory.repository.LocationRepository;


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
		 * @param modelMap bereitgestellt von Spring
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
	     * @param model bereitgestellt von Spring
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
		 * @param model bereitgestellt von Spring
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
		 * @param model bereitgestellt von Spring
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
		 * @param model bereitgestellt von Spring
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
		 * @param model bereitgestellt von Spring
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
		 * @param model bereitgestellt von Spring
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
		 * @param model bereitgestellt von Spring
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
		 * @param modelMap bereitgestellt von Spring
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
	     * Mapping für Übersicht der Einnahmen und Ausgaben als Tabelle und Diagramm
	     * 
	     * @param modelMap bereitgestellt von Spring
	     * @return HTML-Seite auf der alle relevanten Informationen angezeigt werden
	     */
	    @RequestMapping(value = "/accountancy", method = RequestMethod.GET)
		public String accountancyOverview(ModelMap modelMap){

//	    	double income = adminTasksManager.summUpIncome();
//	    	double expenditure = adminTasksManager.summUpExpenditure();
	    	double jan14in = adminTasksManager.summUpIncomeForMonth(1, 2014);
	    	double feb14in = adminTasksManager.summUpIncomeForMonth(2, 2014);
	    	double mar14in = adminTasksManager.summUpIncomeForMonth(3, 2014);
	    	double apr14in = adminTasksManager.summUpIncomeForMonth(4, 2014);
	    	double may14in = adminTasksManager.summUpIncomeForMonth(5, 2014);
	    	double jun14in = adminTasksManager.summUpIncomeForMonth(6, 2014);
	    	double jul14in = adminTasksManager.summUpIncomeForMonth(7, 2014);
	    	double aug14in = adminTasksManager.summUpIncomeForMonth(8, 2014);
	    	double sep14in = adminTasksManager.summUpIncomeForMonth(9, 2014);
	    	double oct14in = adminTasksManager.summUpIncomeForMonth(10, 2014);
	    	double nov14in = adminTasksManager.summUpIncomeForMonth(11, 2014);
	    	double dec14in = adminTasksManager.summUpIncomeForMonth(12, 2014);
	    	double jan15in = adminTasksManager.summUpIncomeForMonth(1, 2015);
	    	
	    	double jan14ex = adminTasksManager.summUpExpenditureForMonth(1, 2014);
	    	double feb14ex = adminTasksManager.summUpExpenditureForMonth(2, 2014);
	    	double mar14ex = adminTasksManager.summUpExpenditureForMonth(3, 2014);
	    	double apr14ex = adminTasksManager.summUpExpenditureForMonth(4, 2014);
	    	double may14ex = adminTasksManager.summUpExpenditureForMonth(5, 2014);
	    	double jun14ex = adminTasksManager.summUpExpenditureForMonth(6, 2014);
	    	double jul14ex = adminTasksManager.summUpExpenditureForMonth(7, 2014);
	    	double aug14ex = adminTasksManager.summUpExpenditureForMonth(8, 2014);
	    	double sep14ex = adminTasksManager.summUpExpenditureForMonth(9, 2014);
	    	double oct14ex = adminTasksManager.summUpExpenditureForMonth(10, 2014);
	    	double nov14ex = adminTasksManager.summUpExpenditureForMonth(11, 2014);
	    	double dec14ex = adminTasksManager.summUpExpenditureForMonth(12, 2014);
	    	double jan15ex = adminTasksManager.summUpExpenditureForMonth(1, 2015);
	    	
	    	modelMap.addAttribute("incjan14", jan14in);
	    	modelMap.addAttribute("incfeb14", feb14in);
	    	modelMap.addAttribute("incmar14", mar14in);
	    	modelMap.addAttribute("incapr14", apr14in);
	    	modelMap.addAttribute("incmay14", may14in);
	    	modelMap.addAttribute("incjun14", jun14in);
	    	modelMap.addAttribute("incjul14", jul14in);
	    	modelMap.addAttribute("incaug14", aug14in);
	    	modelMap.addAttribute("incsep14", sep14in);
	    	modelMap.addAttribute("incoct14", oct14in);
	    	modelMap.addAttribute("incnov14", nov14in);
	    	modelMap.addAttribute("incdec14", dec14in);
	    	modelMap.addAttribute("incjan15", jan15in);
	    	
	    	modelMap.addAttribute("expjan14", jan14ex);
	    	modelMap.addAttribute("expfeb14", feb14ex);
	    	modelMap.addAttribute("expmar14", mar14ex);
	    	modelMap.addAttribute("expapr14", apr14ex);
	    	modelMap.addAttribute("expmay14", may14ex);
	    	modelMap.addAttribute("expjun14", jun14ex);
	    	modelMap.addAttribute("expjul14", jul14ex);
	    	modelMap.addAttribute("expaug14", aug14ex);
	    	modelMap.addAttribute("expsep14", sep14ex);
	    	modelMap.addAttribute("expoct14", oct14ex);
	    	modelMap.addAttribute("expnov14", nov14ex);
	    	modelMap.addAttribute("expdec14", dec14ex);
	    	modelMap.addAttribute("expjan15", jan15ex);
	    	
	    	String jan14 = "Januar 14";
	    	modelMap.addAttribute("jan14", jan14);
	    	String feb14 = "Februar 14";
	    	modelMap.addAttribute("feb14", feb14);
	    	String mar14 = "März 14";
	    	modelMap.addAttribute("mar14", mar14);
	    	String apr14 = "April 14";
	    	modelMap.addAttribute("apr14", apr14);
	    	String may14 = "Mai 14";
	    	modelMap.addAttribute("may14", may14);
	    	String jun14 = "Juni 14";
	    	modelMap.addAttribute("jun14", jun14);
	    	String jul14 = "Juli 14";
	    	modelMap.addAttribute("jul14", jul14);
	    	String aug14 = "August 14";
	    	modelMap.addAttribute("aug14", aug14);
	    	String sep14 = "September 14";
	    	modelMap.addAttribute("sep14", sep14);
	    	String oct14 = "Oktober 14";
	    	modelMap.addAttribute("oct14", oct14);
	    	String nov14 = "November 14";
	    	modelMap.addAttribute("nov14", nov14);
	    	String dec14 = "Dezember 14";
	    	modelMap.addAttribute("dec14", dec14);
	    	String jan15 = "Januar 15";
	    	modelMap.addAttribute("jan15", jan15);
	    	
			return "accountancy";
		}
	    
	    /**
	     * Mapping für Übersicht aller Incomes, die im Income-Repository gespeichert sind
	     * 
	     * @param modelMap bereitgestellt von Spring
	     * @return HTML-Seite auf der alle relevanten Informationen angezeigt werden
	     */
	    @RequestMapping(value = "/income", method = RequestMethod.GET)
		public String incomeOverview(ModelMap modelMap){

	    	modelMap.addAttribute("income", incomeRepository.findAll());
	    	
			return "income";
		}
	    
	    /**
	     * Mapping für gefilterte Übersicht von Expenditures, je nach Ergebnis des searchTerm
	     * 
	     * @param modelMap bereitgestellt von Spring
	     * @param searchTerm Suchkriterium nach dem die Liste der Ausgaben sortiert wird
	     * @param redirectAttrs bereitgestellt von Spring
	     * @return Umleitung auf HTML-Seite mit gefilterter Liste
	     */
	    @RequestMapping(value = "/expenditure", method = RequestMethod.POST)
		public String expendituresSearch(ModelMap modelMap, @RequestParam("sort") String searchTerm, RedirectAttributes redirectAttrs){
	    	
	    	redirectAttrs.addAttribute("term", searchTerm);
	    	
			return "redirect:/expenditure/{term}";
		}
	    
		/**
		 * Mapping für gefilterte Übersicht von Expenditures, je nach Ergebnis des searchTerm
		 * 
		 * @param searchTerm Suchkriterium nach dem die Liste der Ausgaben sortiert wird
		 * @param modelMap bereitgestellt von Spring
		 * @param redirectAttrs bereitgestellt von Spring
		 * @return Umleitung auf HTML-Seite mit gefilterter Liste
		 */
		@RequestMapping(value = "/expenditure/{term}")
		public String showFilteredExpenditures(@PathVariable("term") String searchTerm, ModelMap modelMap, RedirectAttributes redirectAttrs)
		{		
			List<Expenditure> filteredList = adminTasksManager.filterIncome(searchTerm);
			
			modelMap.addAttribute("expenditures", filteredList);
			
			return "expenditure";
		}

	    /**
	     * Mapping für Übersicht aller Expenditures, die im Expenditure-Repository gespeichert sind
	     * 
	     * @param modelMap bereitgestellt von Spring
	     * @return HTML-Seite auf der alle relevanten Informationen angezeigt werden
	     */
	    @RequestMapping(value = "/expenditure", method = RequestMethod.GET)
		public String expenditureOverview(ModelMap modelMap){
	    	
	    	modelMap.addAttribute("expenditures", expenditureRepository.findAll());
	    	
			return "expenditure";
		}
}
