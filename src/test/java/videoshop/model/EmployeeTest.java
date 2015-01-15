package videoshop.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;

import videoshop.AbstractIntegrationTests;
import factory.model.Employee;

public class EmployeeTest extends AbstractIntegrationTests{

	@Autowired
	UserAccountManager userAccountManager;
	@Test
	public void testEmployee() {
		Role userRole = new Role("ROLE_WAREHOUSEMAN");
		UserAccount warehouseman = userAccountManager.create("lagerist", "123", userRole);
		userAccountManager.save(warehouseman);
		Employee e = new Employee(warehouseman, "lagerist", "123", "Lagerist","Mueller","Klaus","200","klaus@Mueller.de","Klausstrasse");
	
		assertEquals(warehouseman,e.getUserAccount());
		assertEquals("lagerist",e.getUsername());
		assertEquals("123",e.getPassword());
		assertEquals("Mueller",e.getFamilyname());
		assertEquals("Klaus",e.getFirstname());
		assertEquals("200",e.getSalary());
		assertEquals("klaus@Mueller.de",e.getMail());
		assertEquals("Klausstrasse",e.getAddress());
	}
	
}

