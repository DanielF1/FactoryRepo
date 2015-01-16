package factoryTests.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;

import factory.model.WineTransport;
import factoryTests.AbstractIntegrationTests;

public class WineTransportTest extends AbstractIntegrationTests{

	@Test
	public void testWineTransport() {
		WineTransport w = new WineTransport ("Standort 1","Standort 2", 20.1,LocalDateTime.of(2012, 6, 29, 12, 00),LocalDateTime.of(2012, 6, 30, 12, 00),true );
		assertEquals("Standort 1",w.getStarting_point());
		assertEquals("Standort 2",w.getGoal());
	}
	
}
