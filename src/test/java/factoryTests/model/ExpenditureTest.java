package factoryTests.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import factory.model.Expenditure;
import factoryTests.AbstractIntegrationTests;

public class ExpenditureTest extends AbstractIntegrationTests{

	@Test
	public void testExpenditure() {
		
		Expenditure e = new Expenditure(LocalDate.parse("2014-03-03"),200,"Salary");
		assertEquals("2014-03-03",e.getDate().toString());
		assertEquals(200,e.getValue(),0.0);
		assertEquals("Salary",e.getSortOf());
		
	}
}
