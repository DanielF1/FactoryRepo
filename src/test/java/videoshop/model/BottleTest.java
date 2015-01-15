package videoshop.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import videoshop.AbstractIntegrationTests;
import factory.model.Bottle;


public class BottleTest extends AbstractIntegrationTests {

	
	@Test
	public void testBottle() {
		Bottle b = new Bottle("Courvoisier Napoleon", 0.7, 0.0);
		assertEquals("Courvoisier Napoleon",b.getName());
		assertEquals(0.7,b.getAmount(),0.0);
		assertEquals(0.0,b.getPrice(),0.0);

	}

}
