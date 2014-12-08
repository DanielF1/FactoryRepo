package factory;

import static org.joda.money.CurrencyUnit.EUR;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.joda.money.Money;
import org.neo4j.cypher.internal.compiler.v2_1.docbuilders.logicalPlanDocBuilder;
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

import factory.model.Article;
import factory.model.Barrel;
import factory.model.BarrelList;
import factory.model.CookBook;
import factory.model.Department;
import factory.model.Employee;
import factory.model.Ingredient;
import factory.model.Location;
import factory.model.Locationmanagement;
import factory.model.Recipe;
import factory.model.ArticleRepository;

@Component
public class CognacFactoryDataInitializer implements DataInitializer {

	private final Inventory<InventoryItem> inventory;
	private final Locationmanagement locationmanagement;
	private final UserAccountManager userAccountManager;
	private final ArticleRepository articleRepository;
	private final CookBook cookbook;
	private final BarrelList barrelList;
//	private final BarrelStock_Inter barrelstock_inter;

	@Autowired
	public CognacFactoryDataInitializer(UserAccountManager userAccountManager, Locationmanagement locationmanagement, 

			ArticleRepository articleRepository, CookBook cookbook, Inventory<InventoryItem> inventory,BarrelList barrelList) {

		Assert.notNull(locationmanagement, "LocationManagement must not be null!");
		Location.locationmanagement = locationmanagement;

		Assert.notNull(userAccountManager, "UserAccountManager must not be null!");
		Assert.notNull(cookbook, "CookBook must not be null!");
		Assert.notNull(inventory, "Inventory must not be null!");
		Assert.notNull(barrelList, "Inventory must not be null!");
		
		this.userAccountManager = userAccountManager;
		this.locationmanagement = locationmanagement;	
		this.articleRepository = articleRepository;
		this.cookbook = cookbook;
		this.inventory = inventory;
		this.barrelList = barrelList;
	
	}

	@Override
	public void initialize() {
		initializeLocList(locationmanagement);
		initializeUsers(userAccountManager);
		initializeSortiment();
		initializeCookBook(cookbook);
//		initializeStock2(stock2);
		initializeBarrelList(barrelList);
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
		list6.add(e6);

		
		List<Employee> list7 = new ArrayList<Employee>();
		list7.add(e5);
		list7.add(e6);
		list7.add(e6);
		list7.add(e6);
		list7.add(e6);

		
		List<Employee> list8 = new ArrayList<Employee>();
		list8.add(e1);
		list8.add(e2);
		list8.add(e3);
		list8.add(e6);
		list8.add(e6);
	
		if (locationmanagement.findAll().iterator().hasNext()) {
			return;
		}

		locationmanagement.save(new Location("Standort 1","Klausstrasse 1", "0999 Klaushausen", "81906666", "klaus@klaus.de", list5, list1));
		locationmanagement.save(new Location("Standort 2","Klausstrasse 2", "0997 Klausberg", "81904446", "klaus1klaus@klaus.de", list6, list2));
		locationmanagement.save(new Location("Standort 3","Klausstrasse 3", "0998 Klaustal", "9454366", "klaus@haus.com", list7, list3));
		locationmanagement.save(new Location("Standort 4","Klausstrasse 4", "0996 Klausklausen", "8231611", "KLAUS@klaus.de", list8, list4));
		
	}
	
	private void initializeUsers(UserAccountManager userAccountManager) {


		if (userAccountManager.get(new UserAccountIdentifier("admin")).isPresent()) {
			return;
		}

		UserAccount adminAccount = userAccountManager.create("admin", "123", new Role("ROLE_ADMIN"));
		userAccountManager.save(adminAccount);

		UserAccount brewerAccount = userAccountManager.create("braumeister", "123", new Role("ROLE_BREWER"));
		userAccountManager.save(brewerAccount);
		
		UserAccount salesmanAccount = userAccountManager.create("verkaeufer", "123", new Role("ROLE_SALESMAN"));
		userAccountManager.save(salesmanAccount);
		
		UserAccount warehousemanAccount = userAccountManager.create("lagerist", "123", new Role("ROLE_WAREHOUSEMAN"));
		userAccountManager.save(warehousemanAccount);
		
		UserAccount winegrowerAccount = userAccountManager.create("weinbauer", "123", new Role("ROLE_WINEGROWER"));
		userAccountManager.save(winegrowerAccount);
		
		UserAccount barrelmakerAccount = userAccountManager.create("fassbinder", "123", new Role("ROLE_BARRELMAKER"));
		userAccountManager.save(barrelmakerAccount);
		
		final Role userRole = new Role("ROLE_CUSTOMER");

		UserAccount ua1 = userAccountManager.create("hans", "123", userRole);
		userAccountManager.save(ua1);
	}

	public void initializeSortiment() {
		
		// Die Bilder sind von der Internetseite: http://www.spirituosentheke.de 
		
		articleRepository.save(new Article("chatelier", "Claude Chatelier Extra", "20 Jahre", Money.of(EUR, 46.95), "40,0 %","1.0 Liter", "Cognac"));
		articleRepository.save(new Article("chatelier", "Chatelier Cognac", "8 Jahre", Money.of(EUR, 41.90 ), "40,0 %","0,7 Liter","Cognac"));
		articleRepository.save(new Article("chabasse-napoleon", "Courvoisier Napoleon ", "5 Jahre", Money.of(EUR, 79.90), "40,0 %","0,7 Liter","Cognac"));
		articleRepository.save(new Article("delamain-vesper", "Delamain Vesper", "35 Jahre", Money.of(EUR, 97.95), "40,0 %","0,7 Liter","Cognac"));
		articleRepository.save(new Article("fontpinot", "Frapin Domaine Château", "5 Jahre", Money.of(EUR, 46.95), "30,0 %","1,0 Liter","Cognac"));

		
		// Ist für den Vorrat in der Detailansicht verantwortlich, damit wenn etwas bestellt wird, auch der Vorrat aktualisiert wird
		

		for (Article article : articleRepository.findAll()) {
			InventoryItem inventoryItem = new InventoryItem(article, Units.TEN);
			inventory.save(inventoryItem);
				}
		
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
	

//	public void initializeBarrelStock() 
//	{
//		List<Barrel> barrelMap = new ArrayList<Barrel>();
//		
//		barrelMap.add(new Barrel("Destillat A", 20 ,LocalDate.parse("2007-12-03"),LocalDate.parse("2007-12-03"), LocalDate.parse("2007-12-03")));
//		barrelMap.add(new Barrel("Destillat B", 20 ,LocalDate.parse("2008-11-04"),LocalDate.parse("2009-12-03"), LocalDate.parse("2008-12-04")));
//		barrelMap.add(new Barrel("Destillat C", 20 ,LocalDate.parse("2010-12-03"),LocalDate.parse("2012-12-31"), LocalDate.parse("2011-12-11")));
//		barrelMap.add(new Barrel("Destillat D", 20 ,LocalDate.parse("2011-12-03"),LocalDate.parse("2014-12-03"), LocalDate.parse("2011-12-03")));
//		barrelMap.add(new Barrel("Destillat E", 20 ,LocalDate.parse("2011-12-23"),LocalDate.parse("2017-01-03"), LocalDate.parse("2011-12-30")));
//		
//		barrelstock_inter.save(new BarrelStock(barrelMap));
//	
//	}
	private void initializeBarrelList(BarrelList barrelList) {

		if (barrelList.findAll().iterator().hasNext()) {
			System.out.println("Rep ist nicht leer!!");
			return;
		}
		
		barrelList.save(new Barrel("Destillat A", 20 ,LocalDate.parse("2007-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2007-12-03")));
		barrelList.save(new Barrel("", 12 ,LocalDate.parse("2014-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2014-12-03")));
		barrelList.save(new Barrel("Destillat A", 12 ,LocalDate.parse("2014-12-03"),LocalDate.parse("2014-12-03"), LocalDate.parse("2014-12-03")));
		barrelList.save(new Barrel("Destillat B", 12 ,LocalDate.parse("2007-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2007-12-03")));
		barrelList.save(new Barrel("Destillat B", 7 ,LocalDate.parse("2007-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2007-12-03")));
		barrelList.save(new Barrel("Destillat C", 10 ,LocalDate.parse("2014-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2011-12-03")));
		barrelList.save(new Barrel("Destillat C", 9 ,LocalDate.parse("2014-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2012-12-03")));
		barrelList.save(new Barrel("Destillat C", 12 ,LocalDate.parse("2014-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2014-12-03")));
		barrelList.save(new Barrel("Destillat C", 12 ,LocalDate.parse("2014-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2014-12-03")));

		//	barrelList.save(new Barrel(2L,LocalDate.parse("2007-12-03"), BarrelContentType.Wein, 20,LocalDate.parse("2007-12-03"), LocalDate.parse("2007-12-03") ));
//		barrelList.save(new Barrel(3L,LocalDate.parse("2007-12-03"), BarrelContentType.Wein, 20,LocalDate.parse("2007-12-03"), LocalDate.parse("2007-12-03") ));
		//barrelList.save(new Barrel(4L,sdf.parse("04.04.2013"), "Wein", 20,sdf.parse("04.04.2013"), sdf.parse("01.01.2014") ));
		
		System.out.println("0000000000000000000000000000000000000000000000000000000000000000000000000000");
		
	}
	
}
