package factory.model;

import java.util.ArrayList;
import java.util.List;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminTasksManager {

	private final LocationRepository locationRepository;
	private final DepartmentRepository departmentRepository;
	private final EmployeeRepository employeeRepository;
	private final UserAccountManager userAccountManager;
	
	@Autowired
	public AdminTasksManager(	LocationRepository locationRepository,
								DepartmentRepository departmentRepository,
								EmployeeRepository employeeRepository,
								UserAccountManager userAccountManager) {
		
		this.locationRepository = locationRepository;
		this.departmentRepository = departmentRepository;
		this.employeeRepository = employeeRepository;
		this.userAccountManager = userAccountManager;
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
	
	
	public void addEmployee(String username,
							String password,
							Long id, 
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
		
			Employee warehouseman = new Employee(warehousemanAccount, workplace, name, firstname, salary, mail, address);
			employeeRepository.save(warehouseman);
			Location loc1 = locationRepository.findOne(id);
			loc1.getEmployees().add(warehouseman);
			locationRepository.save(loc1);
			break;
		
		case "Braumeister":
			UserAccount brewerAccount = userAccountManager.create(username, password, new Role("ROLE_BREWER"));
			userAccountManager.save(brewerAccount);
		
			Employee brewer = new Employee(brewerAccount, workplace, name, firstname, salary, mail, address);
			employeeRepository.save(brewer);
			Location loc2 = locationRepository.findOne(id);
			loc2.getEmployees().add(brewer);
			locationRepository.save(loc2);
			break;
			
		case "Fassbinder":
			UserAccount barrelmakerAccount = userAccountManager.create(username, password, new Role("ROLE_BARRELMAKER"));
			userAccountManager.save(barrelmakerAccount);
		
			Employee barrelmaker = new Employee(barrelmakerAccount, workplace, name, firstname, salary, mail, address);
			employeeRepository.save(barrelmaker);
			Location loc3 = locationRepository.findOne(id);
			loc3.getEmployees().add(barrelmaker);
			locationRepository.save(loc3);
			break;
			
		case "Verkäufer":
			UserAccount salesmanAccount = userAccountManager.create(username, password, new Role("ROLE_SALESMAN"));
			userAccountManager.save(salesmanAccount);
		
			Employee salesman = new Employee(salesmanAccount, workplace, name, firstname, salary, mail, address);
			employeeRepository.save(salesman);
			Location loc4 = locationRepository.findOne(id);
			loc4.getEmployees().add(salesman);
			locationRepository.save(loc4);
			break;
			
		case "Weinbauer":
			UserAccount winegrowerAccount = userAccountManager.create(username, password, new Role("ROLE_WINEGROWER"));
			userAccountManager.save(winegrowerAccount);
		
			Employee winegrower = new Employee(winegrowerAccount, workplace, name, firstname, salary, mail, address);
			employeeRepository.save(winegrower);
			Location loc5 = locationRepository.findOne(id);
			loc5.getEmployees().add(winegrower);
			locationRepository.save(loc5);
			break;
		}
	}
	
	
	public void editEmployee(	Long id, 
								String workplace, 
								String name, 
								String firstname, 
								String salary, 
								String mail, 
								String address){
		
		Employee employee = employeeRepository.findOne(id);
		employee.setWorkplace(workplace);
		employee.setName(name);
		employee.setFirstname(firstname);
		employee.setSalary(salary);
		employee.setMail(mail);
		employee.setAddress(address);
		
		employeeRepository.save(employee);
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
}
