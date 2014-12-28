package factory.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountIdentifier;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminTasksManager {

	private final LocationRepository locationRepository;
	private final DepartmentRepository departmentRepository;
	private final EmployeeRepository employeeRepository;
	private final UserAccountManager userAccountManager;
	private final CustomerRepository customerRepository;
	
	@Autowired
	public AdminTasksManager(	LocationRepository locationRepository,
								DepartmentRepository departmentRepository,
								EmployeeRepository employeeRepository,
								UserAccountManager userAccountManager,
								CustomerRepository customerRepository) {
		
		this.locationRepository = locationRepository;
		this.departmentRepository = departmentRepository;
		this.employeeRepository = employeeRepository;
		this.userAccountManager = userAccountManager;
		this.customerRepository = customerRepository;
	}
	
	public void addLocation(String name,String address, String city, String telefon, String mail){
		
		List<Employee> emp = new ArrayList<Employee>();
    	List<Department> dep = new ArrayList<Department>();
    
    	Location location = new Location(name, address, city, telefon, mail, emp, dep);	
    	locationRepository.save(location);
	}
	
	
	public void editLocation(String name,String address, String city, String telefon, String mail, Long id){
		
		Location location = locationRepository.findOne(id);
		location.setName(name);
		location.setAddress(address);
		location.setCity(city);
		location.setTelefon(telefon);
		location.setMail(mail);
		
		locationRepository.save(location);
	}
	
	public void deleteLocation(Long id){
		
		Location location = locationRepository.findOne(id);
		List<Department> l1 = location.getDepartments();
		l1.clear();
		List<Employee> l2 = location.getEmployees();
		l2.clear();
		locationRepository.delete(location);
	}
	
	
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
}
