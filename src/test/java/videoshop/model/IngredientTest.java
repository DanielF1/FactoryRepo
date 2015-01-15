package videoshop.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import videoshop.AbstractIntegrationTests;
import factory.model.Ingredient;

public class IngredientTest extends AbstractIntegrationTests{

	@Test
	public void testIngredient() {
		Ingredient i = new Ingredient("Gut", 4, 4, "Liter");
		assertEquals("Gut",i.getQuality());
		assertEquals(4,i.getAge());
		assertEquals(4,i.getAmount(),0.0);
		assertEquals("Liter",i.getUnit());
	}
	
}
