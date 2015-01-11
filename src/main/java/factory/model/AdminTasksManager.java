package factory.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Managerklasse, in der alle Aktionen durchgeführt werden, die für den Admin relevant sind.
 */
@Component
public class AdminTasksManager {

	private final LocationRepository locationRepository;
	private final DepartmentRepository departmentRepository;
	private final EmployeeRepository employeeRepository;
	private final UserAccountManager userAccountManager;
	private final CustomerRepository customerRepository;
	private final ExpenditureRepository expenditureRepository;
	private final IncomeRepository incomeRepository;
	
	@Autowired
	public AdminTasksManager(	LocationRepository locationRepository,
								DepartmentRepository departmentRepository,
								EmployeeRepository employeeRepository,
								UserAccountManager userAccountManager,
								CustomerRepository customerRepository,
								ExpenditureRepository expenditureRepository,
								IncomeRepository incomeRepository) {
		
		this.locationRepository = locationRepository;
		this.departmentRepository = departmentRepository;
		this.employeeRepository = employeeRepository;
		this.userAccountManager = userAccountManager;
		this.customerRepository = customerRepository;
		this.expenditureRepository = expenditureRepository;
		this.incomeRepository = incomeRepository;
	}
	
	/**
	 * Erzeugt einen neuen Standort und fügt ihn zum Repository hinzu
	 * 
	 * @param name ist der Name des neuen Standortes
	 * @param address ist die Straße des neuen Standortes
	 * @param city  ist PLZ/Ort des neuen Standortes
	 * @param telefon ist die Telefonnummer des neuen Standortes
	 * @param mail ist die E-Mail des neuen Standortes
	 */
	public void addLocation(String name,String address, String city, String telefon, String mail){
		
		List<Employee> emp = new ArrayList<Employee>();
    	List<Department> dep = new ArrayList<Department>();
    
    	Location location = new Location(name, address, city, telefon, mail, emp, dep);	
    	locationRepository.save(location);
	}
	
	/**
	 * Daten eines bestehenden Standortes werden geändert und abgespeichert
	 * 
	 * @param name ist der Name der geändert wird
	 * @param address ist die Straße die geändert wird
	 * @param city ist die PLZ/Ort der geändert wird
	 * @param telefon ist die Telefonnummer die geändert wird
	 * @param mail ist die E-Mail die geändert wird
	 * @param id Identifier des zu ändernden Standortes
	 */
	public void editLocation(String name,String address, String city, String telefon, String mail, Long id){
		
		Location location = locationRepository.findOne(id);
		location.setName(name);
		location.setAddress(address);
		location.setCity(city);
		location.setTelefon(telefon);
		location.setMail(mail);
		
		locationRepository.save(location);
	}
	
	/**
	 * Löscht einen Standort aus dem Repository und die darin enthaltenen Listen
	 * 
	 * @param id Identifier des zu löschenden Standortes
	 */
	public void deleteLocation(Long id){
		
		Location location = locationRepository.findOne(id);
		List<Department> l1 = location.getDepartments();
		l1.clear();
		List<Employee> l2 = location.getEmployees();
		l2.clear();
		locationRepository.delete(location);
	}
	
	/**
	 * Neuer Mitarbeiter wird in das Mitarbeiter-Repository und einen Standort eingetragen
	 * 
	 * @param username Benutzername des neuen Mitarbeiters
	 * @param password Passwort des neuen Mitarbeiters
	 * @param location Standort, in den er eingefügt wird
	 * @param workplace Arbeitsplatz des neuen Mitarbeiters
	 * @param name Familienname des neuen Mitarbeiters
	 * @param firstname Vorname des neuen Mitarbeiters
	 * @param salary Gehalt des neuen Mitarbeiters
	 * @param mail E-Mail des neuen Mitarbeiters
	 * @param address Addresse des neuen Mitarbeiters
	 */
	public void addEmployee(String username,
							String password,
							String location, 
							String workplace, 
							String name,
							String firstname,
							String salary,
							String mail,
							String address){
		
		switch(workplace){
		case "Lagerist":
			UserAccount warehousemanAccount = userAccountManager.create(username, password, new Role("ROLE_WAREHOUSEMAN"));
			userAccountManager.save(warehousemanAccount);
		
			Employee warehouseman = new Employee(warehousemanAccount, username, password, workplace, name, firstname, salary, mail, address);
			employeeRepository.save(warehouseman);
			Location loc1 = locationRepository.findByName(location);
			loc1.getEmployees().add(warehouseman);
			locationRepository.save(loc1);
			break;
		
		case "Braumeister":
			UserAccount brewerAccount = userAccountManager.create(username, password, new Role("ROLE_BREWER"));
			userAccountManager.save(brewerAccount);
		
			Employee brewer = new Employee(brewerAccount, username, password, workplace, name, firstname, salary, mail, address);
			employeeRepository.save(brewer);
			Location loc2 = locationRepository.findByName(location);
			loc2.getEmployees().add(brewer);
			locationRepository.save(loc2);
			break;
			
		case "Fassbinder":
			UserAccount barrelmakerAccount = userAccountManager.create(username, password, new Role("ROLE_BARRELMAKER"));
			userAccountManager.save(barrelmakerAccount);
		
			Employee barrelmaker = new Employee(barrelmakerAccount, username, password, workplace, name, firstname, salary, mail, address);
			employeeRepository.save(barrelmaker);
			Location loc3 = locationRepository.findByName(location);
			loc3.getEmployees().add(barrelmaker);
			locationRepository.save(loc3);
			break;
			
		case "Verkäufer":
			UserAccount salesmanAccount = userAccountManager.create(username, password, new Role("ROLE_SALESMAN"));
			userAccountManager.save(salesmanAccount);
		
			Employee salesman = new Employee(salesmanAccount, username, password, workplace, name, firstname, salary, mail, address);
			employeeRepository.save(salesman);
			Location loc4 = locationRepository.findByName(location);
			loc4.getEmployees().add(salesman);
			locationRepository.save(loc4);
			break;
			
		case "Weinbauer":
			UserAccount winegrowerAccount = userAccountManager.create(username, password, new Role("ROLE_WINEGROWER"));
			userAccountManager.save(winegrowerAccount);
		
			Employee winegrower = new Employee(winegrowerAccount, username, password, workplace, name, firstname, salary, mail, address);
			employeeRepository.save(winegrower);
			Location loc5 = locationRepository.findByName(location);
			loc5.getEmployees().add(winegrower);
			locationRepository.save(loc5);
			break;
		}
	}
	
	/**
	 * Daten eines bestehenden Mitarbeites werden geändert und abgespeichert
	 * 
	 * @param username Benutzername des Mitarbeites
	 * @param familyname Familienname des Mitarbeites
	 * @param firstname Vorname des Mitarbeites
	 * @param salary Gehalt des Mitarbeites
	 * @param mail E-Mail des Mitarbeites
	 * @param address Addresse des Mitarbeites
	 */
	public void editEmployee(	String username, 
								String familyname, 
								String firstname, 
								String salary, 
								String mail, 
								String address){
		
		Employee employee = employeeRepository.findByUsername(username);
		
		employee.setFamilyname(familyname);
		employee.setFirstname(firstname);
		employee.setSalary(salary);
		employee.setMail(mail);
		employee.setAddress(address);
		
		employeeRepository.save(employee);
	}
	
	
	/**
	 * Löscht einen Mitarbeiter aus der Mitarbeiterliste eines Standortes und aus dem Mitarbeiter-Repository
	 * 
	 * @param id Identifier des zu löschenden Mitarbeiters
	 */
	public void dismissEmployee(Long id){
		Employee employee = employeeRepository.findOne(id);
		
		for(Location loc : locationRepository.findAll()){
			for(Employee emp : loc.getEmployees()){
				if(emp.equals(employee)){
					List<Employee> l1 = loc.getEmployees();
					l1.remove(employee);
					userAccountManager.disable(employee.getUserAccount().getIdentifier());
					employeeRepository.delete(employee);
					break;
				}
			}
		}
	}
	
	/**
	 * Daten eines Kunden werden geändert und gespeichert
	 * 
	 * @param username Benutzername des Kunden
	 * @param familyname Familienname des Kunden
	 * @param firstname Vorname des Kunden
	 * @param address Addresse des Kunden
	 */
	public void editCustomer(	String username,  
								String familyname, 
								String firstname, 
								String address){

		Customer customer = customerRepository.findByUsername(username);
		customer.setFamilyname(familyname);
		customer.setFirstname(firstname);
		customer.setAddress(address);
		customerRepository.save(customer);
	}
		
	/**
	 * Fügt einem Standort eine neue Abteilung hinzu, wenn diese nicht schon vorhanden ist
	 * 
	 * @param id Identifier des Standortes
	 * @param sort Art der Abteilung, die hinzugefügt werden soll
	 * @return Weiterleitung auf die Standortübersicht
	 */
	public String addDepartment(Long id, String sort){
		
		Department department = new Department(sort);
		Location location = locationRepository.findOne(id);
		
		for(Department dep : location.getDepartments()){
			if(dep.getName().equals(department.getName())){
				return "redirect:/adminLocList";
			}
		}	
			departmentRepository.save(department);
			location.getDepartments().add(department);
			locationRepository.save(location);	
		return "redirect:/adminLocList";
	}

	/**
	 * Daten eines Mitarbeiters werden geändert und im Mitarbeiter-Repository abgespeichert
	 * 
	 * @param username Benutzername des Mitarbeiters
	 * @param workplace Arbeitsplatz des Mitarbeiters
	 * @param familyname Familienname des Mitarbeiters
	 * @param firstname Vorname des Mitarbeiters
	 * @param mail E-Mail des Mitarbeiters
	 * @param address Addresse des Mitarbeiters
	 */
	public void editEmployeeData(	String username, 
									String workplace,
									String familyname, 
									String firstname, 
									String mail, 
									String address){
		
		Employee employee = employeeRepository.findByUsername(username);
		employee.setWorkplace(workplace);
		employee.setFamilyname(familyname);
		employee.setFirstname(firstname);
		employee.setMail(mail);
		employee.setAddress(address);
		
		employeeRepository.save(employee);
	}
		
	/**
	 * Daten eines Kunden werden geändert und im Kunden-Repository abgespeichert
	 * 
	 * @param username Benutzername des Kunden
	 * @param familyname Familienname des Kunden
	 * @param firstname Vorname des Kunden
	 * @param address Addresse des Kunden
	 */
	public void editCustomerData(	String username,
									String familyname, 
									String firstname, 
									String address){
		
		Customer customer = customerRepository.findByUsername(username);
    	customer.setFamilyname(familyname);
    	customer.setFirstname(firstname);
    	customer.setAddress(address);
    	
    	customerRepository.save(customer);	
	}
	
	
	/**
	 * Summiert alle Gehälter der Arbeiter und fügt für einen festgelegten Zeitraum Ausgaben in Höhe dieser Summe hinzu
	 */
	public void EmployeeExpenditures(){
		
		double totalSalary = 0;
		
		for(Employee emp : employeeRepository.findAll()){
			
			double sal = Double.parseDouble(emp.getSalary());
			totalSalary += sal;
		}//for
		
		for(int i=1; i<13; i++){
			if(LocalDate.now().isBefore(LocalDate.of(2015, i, 1))){
				break;
			}
			expenditureRepository.save(new Expenditure(LocalDate.of(2015, i, 1), totalSalary, "Gehalt"));
		}
	}
	
	
	/**
	 * Summiert alle Einkommen, die sich im Income-Repository befinden, auf
	 * 
	 * @return Summe aller Einkommen als double
	 */
	public double summUpIncome(){
		double summ = 0;
		
		for(Income i : incomeRepository.findAll()){
			double inc = i.getValue();
			summ += inc;
		}
		
		return summ;
	}
	
	/**
	 * Summiert alle Ausgaben, die sich im Expenditure-Repository befinden, auf
	 * 
	 * @return Summe aller Ausgaben als double
	 */
	public double summUpExpenditure(){
		double summ = 0;
		
		for(Expenditure i : expenditureRepository.findAll()){
			double inc = i.getValue();
			summ += inc;
		}
		
		return summ;
	}
}
