package videoshop;

import static org.joda.money.CurrencyUnit.EUR;

import org.joda.money.Money;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountIdentifier;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import videoshop.model.Article;
import videoshop.model.Location;
import videoshop.model.Locationmanagement;
import videoshop.model.Sortiment;

@Component
public class CognacFactoryDataInitializer implements DataInitializer {

	private final Locationmanagement locationmanagement;
	private final UserAccountManager userAccountManager;
	private final Sortiment sortiment;
//	private final BarrelList barrelList;
	
	
	
	@Autowired
	public CognacFactoryDataInitializer(UserAccountManager userAccountManager, Locationmanagement locationmanagement, 
			Sortiment sortiment/*, BarrelList barrelList*/) {

		Assert.notNull(locationmanagement, "LocationManagement must not be null!");
		Assert.notNull(userAccountManager, "UserAccountManager must not be null!");
		//Assert.notNull(barrelList, "BarrelList must not be null!");
		
		this.userAccountManager = userAccountManager;
		this.locationmanagement = locationmanagement;
		this.sortiment = sortiment;
//		this.barrelList = barrelList;
	}

	@Override
	public void initialize() {
		initializeLocList(locationmanagement);
		initializeUsers(userAccountManager);
		initializeSortiment();
//		initializeBarrelList(barrelList);
	}
	
	private void initializeLocList(Locationmanagement locationmanagement) {

		if (locationmanagement.findAll().iterator().hasNext()) {
			return;
		}

		locationmanagement.save(new Location("Standort 1","Klausstrasse 1", "0999 Klaushausen", "81906666", "klaus@klaus.de", "Lager"));
		locationmanagement.save(new Location("Standort 2","Klausstrasse 2", "0997 Klausberg", "81904446", "klaus1klaus@klaus.de", "Destillation, Lager"));
		locationmanagement.save(new Location("Standort 3","Klausstrasse 3", "0998 Klaustal", "9454366", "klaus@haus.com", "Verwaltung"));
		locationmanagement.save(new Location("Standort 4","Klausstrasse 4", "0996 Klausklausen", "8231611", "KLAUS@klaus.de", "Verkauf, Destillation"));
		
	}
	
	private void initializeUsers(UserAccountManager userAccountManager) {


		if (userAccountManager.get(new UserAccountIdentifier("boss")).isPresent()) {
			return;
		}

		UserAccount adminAccount = userAccountManager.create("admin", "123", new Role("ROLE_BOSS"));
		userAccountManager.save(adminAccount);

		UserAccount brewerAccount = userAccountManager.create("braumeister", "123", new Role("ROLE_BREWER"));
		userAccountManager.save(brewerAccount);
		
		UserAccount salesmanAccount = userAccountManager.create("verkäufer", "123", new Role("ROLE_SALESMAN"));
		userAccountManager.save(salesmanAccount);
		
		UserAccount warehousemanAccount = userAccountManager.create("lagerist", "123", new Role("ROLE_WAREHOUSEMAN"));
		userAccountManager.save(warehousemanAccount);
		
		UserAccount winegrowerAccount = userAccountManager.create("weinbauer", "123", new Role("ROLE_WINEGROWER"));
		userAccountManager.save(winegrowerAccount);
		
		final Role userRole = new Role("ROLE_CUSTOMER");

		UserAccount ua1 = userAccountManager.create("hans", "123", userRole);
		userAccountManager.save(ua1);
	}

	public void initializeSortiment() {
		
		// Die Bilder sind von der Internetseite: http://www.spirituosentheke.de 
		
		sortiment.save(new Article("chatelier", "Claude Chatelier Extra", "20 Jahre", Money.of(EUR, 46.95), "40,0 %","1.0 Liter", "Cognac"));
		sortiment.save(new Article("chatelier", "Chatelier Cognac", "8 Jahre", Money.of(EUR, 41.90 ), "40,0 %","0,7 Liter","Cognac"));
		sortiment.save(new Article("chabasse-napoleon", "Courvoisier Napoleon ", "5 Jahre", Money.of(EUR, 79.90), "40,0 %","0,7 Liter","Cognac"));
		sortiment.save(new Article("delamain-vesper", "Delamain Vesper", "35 Jahre", Money.of(EUR, 97.95), "40,0 %","0,7 Liter","Cognac"));
		sortiment.save(new Article("chabasse-napoleon", "Frapin Domaine Château", "5 Jahre", Money.of(EUR, 46.95), "30,0 %","1,0 Liter","Cognac"));

	}

//	private void initializeBarrelList(BarrelList barrelList) {
//
//		if (barrelList.findAll().iterator().hasNext()) {
//			System.out.println("Rep ist nicht leer!!");
//			return;
//		}
//		
//		barrelList.save(new Barrel(1L,LocalDate.parse("2007-12-03"), BarrelContentType.Wein, 20,LocalDate.parse("2007-12-03"), LocalDate.parse("2007-12-03") ));
//		barrelList.save(new Barrel(2L,LocalDate.parse("2007-12-03"), BarrelContentType.Wein, 20,LocalDate.parse("2007-12-03"), LocalDate.parse("2007-12-03") ));
//		barrelList.save(new Barrel(3L,LocalDate.parse("2007-12-03"), BarrelContentType.Wein, 20,LocalDate.parse("2007-12-03"), LocalDate.parse("2007-12-03") ));
//		//barrelList.save(new Barrel(4L,sdf.parse("04.04.2013"), "Wein", 20,sdf.parse("04.04.2013"), sdf.parse("01.01.2014") ));
//		
//		System.out.println("0000000000000000000000000000000000000000000000000000000000000000000000000000");
//		
//	}
	
}
