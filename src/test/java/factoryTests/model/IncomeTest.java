package factoryTests.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import factory.model.Income;
import factoryTests.AbstractIntegrationTests;

public class IncomeTest extends AbstractIntegrationTests{

	@Test
	public void testIncome() {
	    Income e = new Income("Hans Klausen", LocalDate.of(2014, 1, 12), 4526.90, "Produktkauf");
		assertEquals("Hans Klausen",e.getCustomer());
		assertEquals("2014-01-12",e.getDate().toString());
		assertEquals(4526.90,e.getValue(),0.0);
		assertEquals("Produktkauf",e.getSortOf());
	}
	
}
