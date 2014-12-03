package videoshop;

import static org.joda.money.CurrencyUnit.EUR;

import java.util.ArrayList;
import java.util.List;

import org.joda.money.Money;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Units;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountIdentifier;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import videoshop.model.Article;
import videoshop.model.Department;
import videoshop.model.Location;
import videoshop.model.Locationmanagement;
import videoshop.model.Sortiment;

@Component
public class CognacFactoryDataInitializer implements DataInitializer {

//	private final Inventory<InventoryItem> inventory;
	private final Locationmanagement locationmanagement;
	private final UserAccountManager userAccountManager;
	private final Sortiment sortiment;
//	private final BarrelList barrelList;
	
	
	
	@Autowired
	public CognacFactoryDataInitializer(UserAccountManager userAccountManager, Locationmanagement locationmanagement, 
			Sortiment sortiment/* Inventory<InventoryItem> inventory, BarrelList barrelList*/) {

		Assert.notNull(locationmanagement, "LocationManagement must not be null!");
		Assert.notNull(userAccountManager, "UserAccountManager must not be null!");
		//Assert.notNull(barrelList, "BarrelList must not be null!");
		
		this.userAccountManager = userAccountManager;
		this.locationmanagement = locationmanagement;	
		this.sortiment = sortiment;
//		Inventory<InventoryItem> inventory;
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

		Department d1 = new Department("Lager");
		Department d2 = new Department("Produktion");
		Department d3 = new Department("Verkauf");
		Department d4 = new Department("Verwaltung");
		
		List<Department> list1 = new ArrayList<Department>();
		list1.add(d1);
		list1.add(d2);
		
		List<Department> list2 = new ArrayList<Department>();
		list2.add(d3);
		list2.add(d1);
		
		List<Department> list3 = new ArrayList<Department>();
		list3.add(d4);
		
		List<Department> list4 = new ArrayList<Department>();
		list4.add(d1);
		list4.add(d2);
		list4.add(d3);
		
		if (locationmanagement.findAll().iterator().hasNext()) {
			return;
		}

		locationmanagement.save(new Location("Standort 1","Klausstrasse 1", "0999 Klaushausen", "81906666", "klaus@klaus.de", list1));
		locationmanagement.save(new Location("Standort 2","Klausstrasse 2", "0997 Klausberg", "81904446", "klaus1klaus@klaus.de", list2));
		locationmanagement.save(new Location("Standort 3","Klausstrasse 3", "0998 Klaustal", "9454366", "klaus@haus.com", list3));
		locationmanagement.save(new Location("Standort 4","Klausstrasse 4", "0996 Klausklausen", "8231611", "KLAUS@klaus.de", list4));
		
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
		sortiment.save(new Article("fontpinot", "Frapin Domaine Château", "5 Jahre", Money.of(EUR, 46.95), "30,0 %","1,0 Liter","Cognac"));

		
		// Ist für den Vorrat in der Detailansicht verantwortlich, damit wenn etwas bestellt wird, auch der Vorrat aktualisiert wird
		

//		for (Article article : sortiment.findAll()) {
//			InventoryItem inventoryItem = new InventoryItem(article, Units.TEN);
//			inventory.save(inventoryItem);
//				}
//		
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
