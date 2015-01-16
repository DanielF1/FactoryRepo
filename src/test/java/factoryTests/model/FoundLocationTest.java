package factoryTests.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import factory.model.FoundLocation;
import factoryTests.AbstractIntegrationTests;

public class FoundLocationTest extends AbstractIntegrationTests{

	@Test
	public void testFoundLocation() {
	    FoundLocation e = new FoundLocation("Standort 1","Gut",5,23.4);
		assertEquals("Standort 1",e.getLocation());
		assertEquals("Gut",e.getQuality());
		assertEquals(5,e.getAge());
		assertEquals(23.4,e.getAmount(),0.0);
	}
	
}
