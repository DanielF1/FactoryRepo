package factoryTests.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;

import factory.model.Customer;
import factoryTests.AbstractIntegrationTests;

public class CustomerTest extends AbstractIntegrationTests{

	@Autowired
	UserAccountManager userAccountManager;
	@Test
	public void testCustomer() {
		Role userRole = new Role("ROLE_CUSTOMER");
		UserAccount u1 = userAccountManager.create("hansi", "123", userRole);
		userAccountManager.save(u1);
		Customer c1 = new Customer(u1, "hansi", "123", "Dittrich", "Günther", "Hauptstraße 5");		
		assertEquals(u1,c1.getUserAccount());
		assertEquals("hansi",c1.getUsername());
		assertEquals("123",c1.getPassword());
		assertEquals("Dittrich",c1.getFamilyname());
		assertEquals("Günther",c1.getFirstname());
		assertEquals("Hauptstraße 5",c1.getAddress());
	}
	
}
