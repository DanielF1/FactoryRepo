package videoshop.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import videoshop.AbstractIntegrationTests;
import factory.model.Barrel;

public class BarrelTest extends AbstractIntegrationTests{

	@Test
	public void testBarrel() {

		Barrel barrel = new Barrel(0, "Schlecht", 5 ,LocalDate.parse("2007-12-03"),12,LocalDate.parse("2014-12-03"),LocalDate.parse("2014-12-03"), LocalDate.parse("2014-12-03"), "");
		assertEquals(7,barrel.getAge());
		assertEquals("Schlecht",barrel.getQuality());
		assertEquals(5,barrel.getContent_amount(),0.0);
		assertEquals(12,barrel.getBarrel_volume(),0.0);
		assertEquals("2007-12-03",barrel.getManufacturing_date().toString());
	}
	
	@Test
	public void testRund2Decimal(){
		double x = 2.33;
		double Ergebnis = (double) (int) (x*100)/100;
		assertEquals(x,Ergebnis,0.0);
	}
	

}
