package videoshop;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import factory.CognacFactory;

/**
 * Base class for integration tests bootstrapping the core {@link VideoShop} configuration class.
 * 
 * @author Oliver Gierke
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CognacFactory.class)
public class AbstractIntegrationTests {

	@Test
	public void testName() throws Exception {
		
	}
}
