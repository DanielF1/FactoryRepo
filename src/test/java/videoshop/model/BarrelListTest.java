//package videoshop.model;
//
//import static org.junit.Assert.assertEquals;
//
//import java.time.LocalDate;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import videoshop.AbstractIntegrationTests;
//import factory.model.Barrel;
//import factory.model.BarrelRepository;
//
//
//public class BarrelListTest extends AbstractIntegrationTests {
//	
//	@Autowired
//	BarrelRepository barrellist;
//	
//	@Test
//	public void test() {
//		Barrel barrel = new Barrel("Destillat A", 5 ,LocalDate.parse("2014-12-03"),LocalDate.parse("2014-12-03"), LocalDate.parse("2014-12-03"));
//		barrellist.save(barrel);
//		Barrel barrel1 = barrellist.findOne(barrel.getId());
//		assertEquals(barrel.getContent(),barrel1.getContent());
//		assertEquals(0, Double.compare(barrel.getAmount(), barrel1.getAmount()));
//		assertEquals(barrel.getBirthdate_of_barrel(),barrel1.getBirthdate_of_barrel());
//		assertEquals(barrel.getDeath_of_barrel(),barrel1.getDeath_of_barrel());
//		assertEquals(barrel.getLastFill(),barrel1.getLastFill());
//		assertEquals(barrel.getAlter(),barrel1.getAlter());
//
//	}
//
//}
