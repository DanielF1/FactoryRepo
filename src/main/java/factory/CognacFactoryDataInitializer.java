package factory;

import static org.joda.money.CurrencyUnit.EUR;

import java.time.LocalDate;
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

import factory.model.Accountancy;
import factory.model.Article;
import factory.model.ArticleRepository;
import factory.model.Barrel;
import factory.model.BarrelStock;
import factory.model.Bottle;
import factory.model.BottleStock;
import factory.model.CookBookRepository;
import factory.model.Customer;
import factory.model.CustomerRepository;
import factory.model.Department;
import factory.model.DepartmentRepository;
import factory.model.Employee;
import factory.model.EmployeeRepository;
import factory.model.Ingredient;
import factory.model.Location;
import factory.model.LocationRepository;
import factory.model.Production;
import factory.model.Recipe;
import factory.model.Sale;
import factory.model.Still;
import factory.model.WineStock;

@Component
public class CognacFactoryDataInitializer implements DataInitializer {

	private final Inventory<InventoryItem> inventory;
	private final LocationRepository locationRepository;
	private final DepartmentRepository departmentRepository;
	private final EmployeeRepository employeeRepository;
	private final UserAccountManager userAccountManager;
	private final CustomerRepository customerRepository;
	private final ArticleRepository articleRepository;
	private final CookBookRepository cookbookrepository;

	@Autowired
	public CognacFactoryDataInitializer(UserAccountManager userAccountManager,
										CustomerRepository customerRepository,
										LocationRepository locationRepository, 
										DepartmentRepository departmentRepository,
										EmployeeRepository employeeRepository,
										ArticleRepository articleRepository,
										CookBookRepository cookbookrepository, 
										Inventory<InventoryItem> inventory) {

		
		Assert.notNull(locationRepository, "LocationRepository must not be null!");
		Assert.notNull(departmentRepository, "DepartmentRepository must not be null!");
		Assert.notNull(employeeRepository, "EmployeeRepository must not be null!");
		Assert.notNull(userAccountManager, "UserAccountManager must not be null!");
		Assert.notNull(cookbookrepository, "CookBook must not be null!");
		Assert.notNull(inventory, "Inventory must not be null!");
		
		this.userAccountManager = userAccountManager;
		this.customerRepository = customerRepository;
		this.locationRepository = locationRepository;	
		this.departmentRepository = departmentRepository;
		this.employeeRepository = employeeRepository;
		this.articleRepository = articleRepository;
		this.cookbookrepository = cookbookrepository;
		this.inventory = inventory;
	}

	@Override
	public void initialize() {
		initializeLocationsUsersAndStock(	locationRepository, 
											departmentRepository, 
											employeeRepository, 
											userAccountManager, 
											customerRepository);
		
		initializeSortiment(inventory);
		initializeCookBook(cookbookrepository);
	}
	

	private void initializeLocationsUsersAndStock(	LocationRepository locationRepository, 
													DepartmentRepository departmentRepository, 
													EmployeeRepository employeeRepository, 
													UserAccountManager userAccountManager, 
													CustomerRepository customerRepository) {
		
		if (userAccountManager.get(new UserAccountIdentifier("admin")).isPresent()) {
			return;
		}

		
		/*
		 * initialize employee accounts
		 */
		UserAccount adminAcc = userAccountManager.create("admin", "123", new Role("ROLE_ADMIN"));
		userAccountManager.save(adminAcc);
		UserAccount brewerAcc = userAccountManager.create("braumeister1", "123", new Role("ROLE_BREWER"));
		userAccountManager.save(brewerAcc);
		UserAccount brewerAcc2 = userAccountManager.create("braumeister2", "123", new Role("ROLE_BREWER"));
		userAccountManager.save(brewerAcc2);
		UserAccount salesmanAcc = userAccountManager.create("verkaeufer1", "123", new Role("ROLE_SALESMAN"));
		userAccountManager.save(salesmanAcc);
		UserAccount salesmanAcc2 = userAccountManager.create("verkaeufer2", "123", new Role("ROLE_SALESMAN"));
		userAccountManager.save(salesmanAcc2);
		UserAccount warehousemanAcc = userAccountManager.create("lagerist1", "123", new Role("ROLE_WAREHOUSEMAN"));
		userAccountManager.save(warehousemanAcc);
		UserAccount warehousemanAcc2 = userAccountManager.create("lagerist2", "123", new Role("ROLE_WAREHOUSEMAN"));
		userAccountManager.save(warehousemanAcc2);
		UserAccount warehousemanAcc3 = userAccountManager.create("lagerist3", "123", new Role("ROLE_WAREHOUSEMAN"));
		userAccountManager.save(warehousemanAcc3);
		UserAccount winegrowerAcc = userAccountManager.create("weinbauer1", "123", new Role("ROLE_WINEGROWER"));
		userAccountManager.save(winegrowerAcc);
		UserAccount winegrowerAcc2 = userAccountManager.create("weinbauer2", "123", new Role("ROLE_WINEGROWER"));
		userAccountManager.save(winegrowerAcc2);
		UserAccount barrelmakerAcc = userAccountManager.create("fassbinder1", "123", new Role("ROLE_BARRELMAKER"));
		userAccountManager.save(barrelmakerAcc);
		UserAccount barrelmakerAcc2 = userAccountManager.create("fassbinder2", "123", new Role("ROLE_BARRELMAKER"));
		userAccountManager.save(barrelmakerAcc2);
		
		
		/*
		 * initialize customer accounts
		 */
		final Role userRole = new Role("ROLE_CUSTOMER");

		UserAccount ua1 = userAccountManager.create("hans", "123", userRole);
		userAccountManager.save(ua1);
		
		Customer c1 = new Customer(ua1,"Dittrich", "Günther", "Hauptstraße 5");
		customerRepository.save(c1);
		
		
		/*
		 * initialize barrels
		 */
		Barrel br1 = new Barrel("Destillat A", 5 ,12,LocalDate.parse("2007-12-03"),LocalDate.parse("2014-12-03"),LocalDate.parse("2014-12-03"), LocalDate.parse("2014-12-03"), "");
		Barrel br2 = new Barrel("Destillat A", 5 ,23,LocalDate.parse("2007-12-03"), LocalDate.parse("2014-12-03"),LocalDate.parse("2014-12-03"), LocalDate.parse("2010-12-03"), "");
		Barrel br3 = new Barrel("Destillat A", 20 ,23,LocalDate.parse("2007-12-03"), LocalDate.parse("2007-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2008-12-03"), "");
		Barrel br4 = new Barrel("", 0 ,23,LocalDate.parse("2007-12-03"),LocalDate.parse("2014-12-03"),LocalDate.parse("2014-12-03"), LocalDate.parse("2014-12-03"),"");
		Barrel br5 = new Barrel("Destillat A", 5 ,23,LocalDate.parse("2007-12-03"),LocalDate.parse("2014-12-03"),LocalDate.parse("2014-12-03"), LocalDate.parse("2014-12-03"),"");
		Barrel br6 = new Barrel("Destillat A", 12 ,21,LocalDate.parse("2007-12-03"), LocalDate.parse("2014-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2014-12-03"),"");
		Barrel br7 = new Barrel("Destillat B", 12 ,22,LocalDate.parse("2007-12-03"), LocalDate.parse("2007-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2007-12-03"),"");
		Barrel br8 = new Barrel("Destillat B", 7 ,22,LocalDate.parse("2007-12-03"),LocalDate.parse("2007-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2007-12-03"),"");
		Barrel br9 = new Barrel("Destillat C", 10 ,13,LocalDate.parse("2007-12-03"), LocalDate.parse("2014-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2011-12-03"),"");
		Barrel br10 = new Barrel("Destillat C", 9 ,12,LocalDate.parse("2007-12-03"),LocalDate.parse("2014-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2012-12-03"),"");
		Barrel br11 = new Barrel("Destillat C", 12 ,14,LocalDate.parse("2007-12-03"),LocalDate.parse("2014-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2013-12-03"),"");
		Barrel br12 = new Barrel("Destillat C", 12 ,15,LocalDate.parse("2007-12-03"),LocalDate.parse("2014-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2013-12-03"),"");
		
		List<Barrel> barrels = new ArrayList<Barrel>();
		barrels.add(br1);
		barrels.add(br2);
		barrels.add(br3);
		barrels.add(br4);
		barrels.add(br5);
		barrels.add(br6);
		barrels.add(br7);
		barrels.add(br8);
		barrels.add(br9);
		barrels.add(br10);
		barrels.add(br11);
		barrels.add(br12);
		

		
		/*
		 * initialize bottles
		 */
		Bottle b1 = new Bottle("", 0.7);
		Bottle b2 = new Bottle("Courvoisier Napoleon", 0.7);
		Bottle b3 = new Bottle("", 0.7);
		Bottle b4 = new Bottle("", 0.7);
		Bottle b5 = new Bottle("" ,0.7);
		Bottle b6 = new Bottle("", 0.3);
		
		List<Bottle> empty = new ArrayList<Bottle>();
		List<Bottle> full = new ArrayList<Bottle>();
		
		empty.add(b1);
		empty.add(b3);
		empty.add(b4);
		empty.add(b5);
		empty.add(b6);
		
		full.add(b2);
		
		
		/*
		 * initialize stills
		 */
		List<Still> still = new ArrayList<Still>();
		
		Still still_1 = new Still(24, false, true);
		Still still_2 = new Still(24, true, true);
		Still still_3 = new Still(24, false, false);
		Still still_4 = new Still(24, true, true);
		
		still.add(still_1);
		still.add(still_2);
		still.add(still_3);
		still.add(still_4);
		
		
		/*
		 * initialize departments
		 */
		Department d1 = departmentRepository.save(new BottleStock("FlaschenlagerA", empty, full));
		Department d2 = departmentRepository.save(new BottleStock("FlaschenlagerB", empty, full));
		Department d3 = departmentRepository.save(new BarrelStock("FasslagerA", barrels));
		Department d4 = departmentRepository.save(new BarrelStock("FasslagerB", barrels));
		Department d5 = departmentRepository.save(new WineStock("WeinlagerA", 300));
		Department d6 = departmentRepository.save(new WineStock("WeinlagerB", 300));
		Department d7 = departmentRepository.save(new Production("ProduktionA", still,1000));
		Department d8 = departmentRepository.save(new Production("ProduktionB", still,1000));
		Department d9 = departmentRepository.save(new Sale("VerkaufA"));
		Department d10 = departmentRepository.save(new Sale("VerkaufB"));
		Department d11 = departmentRepository.save(new Accountancy("Verwaltung", 0, 0));

		
		List<Department> list5 = new ArrayList<Department>();
		list5.add(d1);
		list5.add(d5);
		list5.add(d8);
		list5.add(d9);
		
		List<Department> list6 = new ArrayList<Department>();
		list6.add(d2);
		list6.add(d4);
		list6.add(d9);
	
		List<Department> list7 = new ArrayList<Department>();
		list7.add(d11);
		
		List<Department> list8 = new ArrayList<Department>();
		list8.add(d3);
		list8.add(d6);
		list8.add(d7);
		list8.add(d10);
		
		
		/*
		 * initialize employees
		 */
		Employee e1 = employeeRepository.save(new Employee(warehousemanAcc, "Lagerist","Mueller","Klaus","200","klaus@Mueller.de","Klausstrasse"));
		Employee e2 = employeeRepository.save(new Employee(warehousemanAcc2, "Lagerist","Maier","Chris","200","klaus@Mueller.de","Klausstrasse"));
		Employee e10 = employeeRepository.save(new Employee(warehousemanAcc3, "Lagerist","Maier","Chris","200","klaus@Mueller.de","Klausstrasse"));
		Employee e3 = employeeRepository.save(new Employee(salesmanAcc, "Verkäufer","Fischer","Dieter","210","Dieter@Fischer.de","Dieterstrasse"));
		Employee e4 = employeeRepository.save(new Employee(salesmanAcc2, "Verkäufer","Fleischer","Detlef","210","Dieter@Fischer.de","Dieterstrasse"));
		Employee e5 = employeeRepository.save(new Employee(barrelmakerAcc, "Fassbinder","Schmidt","Bernd","100","Bernd@Schmidt.de","Berndstrasse"));
		Employee e6 = employeeRepository.save(new Employee(barrelmakerAcc2, "Fassbinder","Schmiedel","Bruno","100","Bernd@Schmidt.de","Berndstrasse"));
		Employee e7 = employeeRepository.save(new Employee(brewerAcc, "Braumeister","Smith","Johannes","250","Johannes@Smith.de","Johannesstreet"));
		Employee e8 = employeeRepository.save(new Employee(brewerAcc2, "Braumeister","Smittie","Joe","250","Johannes@Smith.de","Johannesstreet"));
		
		Employee e9 = employeeRepository.save(new Employee(adminAcc, "Admin","Kowalsky","Günther","120","Guenther@Kowalsky.de","Guentherstrasse"));
		
		
		List<Employee> list1 = new ArrayList<Employee>();
		list1.add(e1);
		list1.add(e4);
		list1.add(e5);
		list1.add(e7);
		
		List<Employee> list2 = new ArrayList<Employee>();
		list2.add(e2);
		list2.add(e3);
		
		List<Employee> list3 = new ArrayList<Employee>();
		list3.add(e9);
		
		
		List<Employee> list4 = new ArrayList<Employee>();
		list4.add(e6);
		list4.add(e8);
		list4.add(e10);
		
		
		/*
		 * initialize locations
		 */
		if (locationRepository.findAll().iterator().hasNext()) {
			return;
		}

		locationRepository.save(new Location("Standort 1","Klausstrasse 1", "0999 Klaushausen", "81906666", "klaus@klaus.de", list1, list5));
		locationRepository.save(new Location("Standort 2","Klausstrasse 2", "0997 Klausberg", "81904446", "klaus1klaus@klaus.de", list2, list6));
		locationRepository.save(new Location("Standort 3","Klausstrasse 3", "0998 Klaustal", "9454366", "klaus@haus.com", list3, list7));
		locationRepository.save(new Location("Standort 4","Klausstrasse 4", "0996 Klausklausen", "8231611", "KLAUS@klaus.de", list4, list8));

	}

	
	/*
	 * initialize catalog
	 */
	public void initializeSortiment(Inventory<InventoryItem> inventory) {
		
		// Die Bilder sind von der Internetseite: http://www.spirituosentheke.de 
		

		Article article1 = new Article("chatelier", "Claude Chatelier Extra", "20 Jahre", Money.of(EUR, 46.95), "40,0 %","1.0 Liter", "Cognac");
		articleRepository.save(article1);
		
		Article article2 = new Article("chatelier", "Chatelier Cognac", "8 Jahre", Money.of(EUR, 41.90 ), "40,0 %","0,7 Liter","Cognac");
		articleRepository.save(article2);
		
		Article article3 = new Article("chabasse-napoleon", "Courvoisier Napoleon ", "5 Jahre", Money.of(EUR, 79.90), "40,0 %","0,7 Liter","Cognac");
		articleRepository.save(article3);
		
		Article article4 = new Article("delamain-vesper", "Delamain Vesper", "35 Jahre", Money.of(EUR, 97.95), "40,0 %","0,7 Liter","Cognac");
		articleRepository.save(article4);
		
		Article article5 = new Article("fontpinot", "Frapin Domaine Château", "5 Jahre", Money.of(EUR, 46.95), "30,0 %","1,0 Liter","Cognac");
		articleRepository.save(article5);
		
		Article article6 = new Article("monnet", "Monnet Cognac", "5 Jahre", Money.of(EUR, 26.95), "40,0 %","0,7 Liter","Cognac");
		articleRepository.save(article6); 		
				
		InventoryItem i1 = new InventoryItem(article1, Units.TEN);
		inventory.save(i1);
		
		InventoryItem i2 = new InventoryItem(article2, Units.TEN);
		inventory.save(i2);
		
		InventoryItem i3 = new InventoryItem(article3, Units.TEN);
		inventory.save(i3);
		
		InventoryItem i4 = new InventoryItem(article4, Units.TEN);
		inventory.save(i4);
		
		InventoryItem i5 = new InventoryItem(article5, Units.TEN);
		inventory.save(i5);
		
		InventoryItem i6 = new InventoryItem(article6, Units.TEN);
		inventory.save(i6);
		
		// Ist für den Vorrat in der Detailansicht verantwortlich, damit wenn etwas bestellt wird, auch der Vorrat aktualisiert wird

//		for (Article article : articleRepository.findAll()) {
//			InventoryItem inventoryItem = new InventoryItem(article, Units.TEN);
//			inventory.save(inventoryItem);
//				}		

	}

	
	/*
	 * initialize recipes
	 */
	private void initializeCookBook(CookBookRepository cookbookrepository) 
	{
		
		Ingredient i1 = new Ingredient("Destillat A", 2000, "Liter");
		Ingredient i2 = new Ingredient("Destillat B", 200, "Liter");
		Ingredient i3 = new Ingredient("Destillat E", 100, "Liter");
		
		List<Ingredient> mapIngredients1 = new ArrayList<Ingredient>();
		
		mapIngredients1.add(i1);
		mapIngredients1.add(i2);
		mapIngredients1.add(i3);
		
		cookbookrepository.save(new Recipe("Cognac A", mapIngredients1));
		
		Ingredient i4 = new Ingredient("Destillat B", 140, "Liter");
		Ingredient i5 = new Ingredient("Destillat T", 20, "Liter");
		Ingredient i6 = new Ingredient("Destillat H", 10, "Liter");
		
		List<Ingredient> mapIngredients2 = new ArrayList<Ingredient>();
		
		mapIngredients2.add(i4);
		mapIngredients2.add(i5);
		mapIngredients2.add(i6);
		
		cookbookrepository.save(new Recipe("Cognac B", mapIngredients2));
		
		Ingredient i7 = new Ingredient("Destillat A", 140, "Liter");
		Ingredient i8 = new Ingredient("Destillat C", 20, "Liter");
		Ingredient i9 = new Ingredient("Destillat D", 10, "Liter");
		
		List<Ingredient> mapIngredients3 = new ArrayList<Ingredient>();
		
		mapIngredients3.add(i7);
		mapIngredients3.add(i8);
		mapIngredients3.add(i9);
		
		cookbookrepository.save(new Recipe("Cognac C", mapIngredients3));		
	}
}
