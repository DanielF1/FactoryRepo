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
		Barrel barrel = new Barrel("Destillat A", 5 ,12,LocalDate.parse("2007-12-03"),LocalDate.parse("2014-12-03"),LocalDate.parse("2014-12-03"), LocalDate.parse("2014-12-03"), "");
		assertEquals(7,barrel.getAlter());
		Barrel barrel1 = new Barrel("Destillat B", 5 ,12,LocalDate.parse("2014-12-03"),LocalDate.parse("2014-12-03"),LocalDate.parse("2014-12-03"), LocalDate.parse("2014-12-03"), "");
		assertEquals(0,barrel1.getAlter());

	}

}
