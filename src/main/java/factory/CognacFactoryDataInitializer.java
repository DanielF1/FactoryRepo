package factory;

import static org.joda.money.CurrencyUnit.EUR;

import java.util.ArrayList;
import java.util.List;

import org.joda.money.Money;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountIdentifier;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import factory.model.Article;
import factory.model.CookBook;
import factory.model.Department;
import factory.model.Employee;
import factory.model.Ingredient;
import factory.model.Location;
import factory.model.Locationmanagement;
import factory.model.Recipe;
import factory.model.Sortiment;
//import factory.model.Stock;
import factory.model.Stock2;
//import factory.model.BarrelStock;

@Component
public class CognacFactoryDataInitializer implements DataInitializer {

	private final Locationmanagement locationmanagement;
	private final UserAccountManager userAccountManager;
	private final Sortiment sortiment;
	private final CookBook cookbook;
//	private final Stock stock;
	private final Stock2 stock2;
//	private final BarrelStock barrelstock;
	

	@Autowired
	public CognacFactoryDataInitializer(UserAccountManager userAccountManager, Locationmanagement locationmanagement, 
			Sortiment sortiment, CookBook cookbook /*,Stock stock*/, Stock2 stock2/*, BarrelStock barrelstock*//* Inventory<InventoryItem> inventory, BarrelList barrelList*/) {

		Assert.notNull(locationmanagement, "LocationManagement must not be null!");
		Assert.notNull(userAccountManager, "UserAccountManager must not be null!");
		Assert.notNull(cookbook, "CookBook must not be null!");
//		Assert.notNull(stock, "Stock must not be null!");
		Assert.notNull(stock2, "Stock2 must not be null!");
//		Assert.notNull(barrelstock, "barrelstock must not be null!");
		//Assert.notNull(barrelList, "BarrelList must not be null!");
		
		this.userAccountManager = userAccountManager;
		this.locationmanagement = locationmanagement;	
		this.sortiment = sortiment;
		this.cookbook = cookbook;
//		this.stock = stock;
		this.stock2 = stock2;
//		this.barrelstock = barrelstock;
//		Inventory<InventoryItem> inventory;
//		this.barrelList = barrelList;
	}

	@Override
	public void initialize() {
		initializeLocList(locationmanagement);
		initializeUsers(userAccountManager);
		initializeSortiment();
		initializeCookBook(cookbook);
//		initializeStock(stock);
//		initializeStock2(stock2);
//		initializeBarrelList(barrelList);
	}
	
	private void initializeLocList(Locationmanagement locationmanagement) {

		Department d1 = new Department("Flaschenlager");
		Department d2 = new Department("Fasslager");
		Department d3 = new Department("Weinlager");
		Department d4 = new Department("Produktion");
		Department d5 = new Department("Verkauf");
		Department d6 = new Department("Verwaltung");
		Department d7 = new Department("");
		
		List<Department> list1 = new ArrayList<Department>();
		list1.add(d1);
		list1.add(d4);
		
		list1.add(d7);
		list1.add(d7);
		list1.add(d7);
		list1.add(d7);
		
		List<Department> list2 = new ArrayList<Department>();
		list2.add(d2);
		list2.add(d4);
		list2.add(d5);
		
		list2.add(d7);
		list2.add(d7);
		list2.add(d7);
		list2.add(d7);
		
		List<Department> list3 = new ArrayList<Department>();
		list3.add(d6);
		
		list3.add(d7);
		list3.add(d7);
		list3.add(d7);
		list3.add(d7);
		list3.add(d7);
		
		List<Department> list4 = new ArrayList<Department>();
		list4.add(d1);
		list4.add(d2);
		list4.add(d3);
		
		list4.add(d7);
		list4.add(d7);
		list4.add(d7);
		
		Employee e1 = new Employee("Lagerist","Mueller","Klaus","200","klaus@Mueller.de","Klausstrasse");
		Employee e2 = new Employee("Verkäufer","Fischer","Dieter","210","Dieter@Fischer.de","Dieterstrasse");
		Employee e3 = new Employee("Fassbinder","Schmidt","Bernd","100","Bernd@Schmidt.de","Berndstrasse");
		Employee e4 = new Employee("Braumeister","Smith","Johannes","250","Johannes@Smith.de","Johannesstreet");
		Employee e5 = new Employee("Admin","Kowalsky","Günther","120","Guenther@Kowalsky.de","Guentherstrasse");
		Employee e6 = new Employee("","","","","","");
		
		List<Employee> list5 = new ArrayList<Employee>();
		list5.add(e1);
		list5.add(e3);
		list5.add(e4);
		
		list5.add(e6);
		list5.add(e6);
		
		
		List<Employee> list6 = new ArrayList<Employee>();
		list6.add(e1);
		list6.add(e3);
		list6.add(e4);
		list6.add(e2);
		
		list5.add(e6);
		
		List<Employee> list7 = new ArrayList<Employee>();
		list7.add(e5);
		
		list5.add(e6);
		list5.add(e6);
		list5.add(e6);
		list5.add(e6);
		
		List<Employee> list8 = new ArrayList<Employee>();
		list8.add(e1);
		list8.add(e2);
		list8.add(e3);
		
		list5.add(e6);
		list5.add(e6);
		
		if (locationmanagement.findAll().iterator().hasNext()) {
			return;
		}

		locationmanagement.save(new Location("Standort 1","Klausstrasse 1", "0999 Klaushausen", "81906666", "klaus@klaus.de", list5, list1));
		locationmanagement.save(new Location("Standort 2","Klausstrasse 2", "0997 Klausberg", "81904446", "klaus1klaus@klaus.de", list6, list2));
		locationmanagement.save(new Location("Standort 3","Klausstrasse 3", "0998 Klaustal", "9454366", "klaus@haus.com", list7, list3));
		locationmanagement.save(new Location("Standort 4","Klausstrasse 4", "0996 Klausklausen", "8231611", "KLAUS@klaus.de", list8, list4));
		
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

	private void initializeCookBook(CookBook cookbook) 
	{
		
		Ingredient i1 = new Ingredient("Destillat A", 2000, "Liter");
		Ingredient i2 = new Ingredient("Destillat B", 200, "Liter");
		Ingredient i3 = new Ingredient("Destillat E", 100, "Liter");
		
		List<Ingredient> mapIngredients1 = new ArrayList<Ingredient>();
		
		mapIngredients1.add(i1);
		mapIngredients1.add(i2);
		mapIngredients1.add(i3);
		
		cookbook.save(new Recipe("Cognac A", mapIngredients1));
		
		Ingredient i4 = new Ingredient("Destillat B", 140, "Liter");
		Ingredient i5 = new Ingredient("Destillat T", 20, "Liter");
		Ingredient i6 = new Ingredient("Destillat H", 10, "Liter");
		
		List<Ingredient> mapIngredients2 = new ArrayList<Ingredient>();
		
		mapIngredients2.add(i4);
		mapIngredients2.add(i5);
		mapIngredients2.add(i6);
		
		cookbook.save(new Recipe("Cognac B", mapIngredients2));
		
		Ingredient i7 = new Ingredient("Destillat A", 140, "Liter");
		Ingredient i8 = new Ingredient("Destillat C", 20, "Liter");
		Ingredient i9 = new Ingredient("Destillat D", 10, "Liter");
		
		List<Ingredient> mapIngredients3 = new ArrayList<Ingredient>();
		
		mapIngredients3.add(i7);
		mapIngredients3.add(i8);
		mapIngredients3.add(i9);
		
		cookbook.save(new Recipe("Cognac C", mapIngredients3));		
	}
	
//	private void initializeStock(Stock stock) {}
//
//
//	void initializeStock2(Stock2 stock2) 
//	{
//
//		Bottle b1 = new Bottle(0.7, BottleType.EMPTY);
//		Bottle b2 = new Bottle(0.7, BottleType.FULL);
//		
//		List<Bottle> empty = new ArrayList<Bottle>();
//		List<Bottle> full = new ArrayList<Bottle>();
//		
//		empty.add(b1);
//		full.add(b2);
//		
//		stock2.save(new BottleStock("Lager A", 100, empty, 20, full));
//	}
//	
//	public void initializeBarrelStock(BarrelStock barrelstock) 
//	{
//		
//		//List<Barrel> mapBarrels = new ArrayList<Barrel>();		
//		barrelstock.saveBarrel(new Barrel("Wein", 20 ,LocalDate.parse("2007-12-03"),LocalDate.parse("2007-12-03"), LocalDate.parse("2007-12-03")) );
//		barrelstock.saveBarrel(new Barrel("Wein", 20 ,LocalDate.parse("2008-11-04"),LocalDate.parse("2009-12-03"), LocalDate.parse("2008-12-04")));
//		barrelstock.saveBarrel(new Barrel("Wein", 20 ,LocalDate.parse("2010-12-03"),LocalDate.parse("2012-12-32"), LocalDate.parse("2011-12-11")));
//		barrelstock.saveBarrel(new Barrel("Wein", 20 ,LocalDate.parse("2011-12-03"),LocalDate.parse("2014-12-03"), LocalDate.parse("2011-12-03")));
//		barrelstock.saveBarrel(new Barrel("Wein", 20 ,LocalDate.parse("2011-12-23"),LocalDate.parse("2017-1-03"), LocalDate.parse("2011-12-30")));
//		
//		//	stock.save(new BarrelStock("Lager A", mapBarrels));
//	
//	}
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
