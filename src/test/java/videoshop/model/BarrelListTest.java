package videoshop.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import videoshop.AbstractIntegrationTests;
import factory.model.BarrelStock;


public class BarrelListTest extends AbstractIntegrationTests {
	
	
	
	@Test
	public void testSize() {
		
		assertEquals(12, BarrelStock.getBarrels().size() );
		

	}

}
