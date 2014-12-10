package videoshop.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import videoshop.AbstractIntegrationTests;
import factory.model.Barrel;

public class BarrelTest extends AbstractIntegrationTests{

	@Test
	public void testAlter() {
//		fail("Not yet implemented");
		Barrel barrel = new Barrel("Destillat D", 12 ,LocalDate.parse("2007-12-04"),LocalDate.parse("2014-12-30"), LocalDate.parse("2014-12-09"));
		assertEquals(0,barrel.getAlter());
		Barrel barrel1 = new Barrel("Destillat D", 12 ,LocalDate.parse("2014-12-04"),LocalDate.parse("2014-12-30"), LocalDate.now().minusDays(365));
		assertEquals(1,barrel1.getAlter());
	}

}
