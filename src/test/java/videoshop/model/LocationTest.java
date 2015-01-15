package videoshop.model;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;

import videoshop.AbstractIntegrationTests;
import factory.model.Department;
import factory.model.Employee;
import factory.model.Location;
import factory.repository.DepartmentRepository;
import factory.repository.EmployeeRepository;

public class LocationTest extends AbstractIntegrationTests{
	@Autowired
	UserAccountManager userAccountManager;
	EmployeeRepository employeeRepository;
	DepartmentRepository departmentRepository;

	@SuppressWarnings("null")
	@Test
	public void testLocation() {

		List<Employee> employees = null;
		List<Department> departments = null;
		Location a = new Location("Standort 1","Klausstrasse 1", "0999 Klaushausen", "81906666", "klaus@klaus.de", employees, departments);
		assertEquals("Standort 1",a.getName());
		assertEquals("Klausstrasse 1",a.getAddress());
		assertEquals("0999 Klaushausen",a.getCity());
		assertEquals("81906666",a.getTelefon());
		assertEquals("klaus@klaus.de",a.getMail());
		
	}
	
}
