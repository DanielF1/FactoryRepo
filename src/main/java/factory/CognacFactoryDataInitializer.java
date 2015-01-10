package factory;

import static org.joda.money.CurrencyUnit.EUR;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import factory.model.Delivery;
import factory.model.DeliveryRepository;
import factory.model.Department;
import factory.model.DepartmentRepository;
import factory.model.Employee;
import factory.model.EmployeeRepository;
import factory.model.Income;
import factory.model.IncomeRepository;
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
	private final DeliveryRepository deliveryRepository;
	private final IncomeRepository incomeRepository;

	@Autowired
	public CognacFactoryDataInitializer(UserAccountManager userAccountManager,
										CustomerRepository customerRepository,
										LocationRepository locationRepository, 
										DepartmentRepository departmentRepository,
										EmployeeRepository employeeRepository,
										ArticleRepository articleRepository,
										CookBookRepository cookbookrepository, 
										DeliveryRepository deliveryRepository,
										Inventory<InventoryItem> inventory,
										IncomeRepository incomeRepository) {

		
		Assert.notNull(locationRepository, "LocationRepository must not be null!");
		Assert.notNull(departmentRepository, "DepartmentRepository must not be null!");
		Assert.notNull(employeeRepository, "EmployeeRepository must not be null!");
		Assert.notNull(userAccountManager, "UserAccountManager must not be null!");
		Assert.notNull(cookbookrepository, "CookBook must not be null!");
		Assert.notNull(deliveryRepository, "CookBook must not be null!");
		Assert.notNull(inventory, "Inventory must not be null!");
		
		this.userAccountManager = userAccountManager;
		this.customerRepository = customerRepository;
		this.locationRepository = locationRepository;	
		this.departmentRepository = departmentRepository;
		this.employeeRepository = employeeRepository;
		this.articleRepository = articleRepository;
		this.cookbookrepository = cookbookrepository;
		this.deliveryRepository = deliveryRepository;
		this.inventory = inventory;
		this.incomeRepository = incomeRepository;
	}

	@Override
	public void initialize() {
		initializeLocationsUsersAndStock(	locationRepository, 
											departmentRepository, 
											employeeRepository, 
											userAccountManager, 
											customerRepository,
											incomeRepository);
		
		initializeSortiment(inventory);
		initializeCookBook(cookbookrepository);
	}
	

	private void initializeLocationsUsersAndStock(	LocationRepository locationRepository, 
													DepartmentRepository departmentRepository, 
													EmployeeRepository employeeRepository, 
													UserAccountManager userAccountManager, 
													CustomerRepository customerRepository,
													IncomeRepository incomeRepository) {
	
		/*
		 * initialize barrels
		 */
		//Barrel (age, quality, content_amount, manufacturing_date,barrel_volume, birth_of_barrel,death_of_barrel,position )
		Barrel br1 = new Barrel(0, "Schlecht", 5 ,LocalDate.parse("2007-12-03"),12,LocalDate.parse("2014-12-03"),LocalDate.parse("2014-12-03"), LocalDate.parse("2014-12-03"), "");			
		Barrel br2 = new Barrel(7, "Schlecht", 5 ,LocalDate.parse("2008-12-03"),23, LocalDate.parse("2014-12-03"),LocalDate.parse("2014-12-03"), LocalDate.parse("2010-12-03"), "");
		Barrel br3 = new Barrel(0, "Schlecht", 20 ,LocalDate.parse("2009-12-03"),23, LocalDate.parse("2007-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2008-12-03"), "");
		Barrel br13 = new Barrel(7, "" ,0,LocalDate.parse("2007-12-03"),2489,LocalDate.parse("2014-12-03"),LocalDate.parse("2014-12-03"), LocalDate.parse("2014-12-03"),"");
		Barrel br4 = new Barrel(7, "" ,0,LocalDate.parse("2007-12-03"),229,LocalDate.parse("2014-12-03"),LocalDate.parse("2014-12-03"), LocalDate.parse("2014-12-03"),"");
		Barrel br5 = new Barrel(0, "Schlecht", 5 ,LocalDate.parse("2007-12-03"),23,LocalDate.parse("2014-12-03"),LocalDate.parse("2014-12-03"), LocalDate.parse("2014-12-03"),"");
		Barrel br6 = new Barrel(7, "Schlecht",17,LocalDate.parse("2007-12-03"),21, LocalDate.parse("2014-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2014-12-03"),"");
		Barrel br7 = new Barrel(0,"Gut", 12 ,LocalDate.parse("2007-12-03"),22, LocalDate.parse("2007-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2007-12-03"),"");
		Barrel br8 = new Barrel(7, "Gut", 7 ,LocalDate.parse("2007-12-03"),22, LocalDate.parse("2007-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2007-12-03"),"");
		Barrel br9 = new Barrel(7, "Gut", 10 ,LocalDate.parse("2007-12-03"),13,LocalDate.parse("2014-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2011-12-03"),"");
		Barrel br10 = new Barrel(7, "Gut", 9 ,LocalDate.parse("2010-12-03"),12,LocalDate.parse("2014-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2012-12-03"),"");
		Barrel br11 = new Barrel(7, "Sehr Gut", 12 ,LocalDate.parse("2007-12-03"),14,LocalDate.parse("2014-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2013-12-03"),"");
		Barrel br12 = new Barrel(7, "Sehr Gut", 12 ,LocalDate.parse("2007-12-03"),15,LocalDate.parse("2014-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2013-12-03"),"");
		
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
		barrels.add(br13);
		
		
		Barrel br61 = new Barrel(6, "Schlecht",17,LocalDate.parse("2008-12-03"),21, LocalDate.parse("2014-12-03"),LocalDate.parse("2014-12-03"), LocalDate.parse("2014-12-03"),"");
		Barrel br71 = new Barrel(6,"Gut", 12 ,LocalDate.parse("1994-12-03"),22, LocalDate.parse("2007-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2014-12-23"),"");
		Barrel br81 = new Barrel(1, "Gut", 7 ,LocalDate.parse("2008-12-03"),22, LocalDate.parse("2007-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2014-12-15"),"");
		Barrel br91 = new Barrel(6, "Gut", 10 ,LocalDate.parse("2009-12-03"),13,LocalDate.parse("2014-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2014-12-16"),"");
		Barrel br101 = new Barrel(1, "Gut", 9 ,LocalDate.parse("2008-12-03"),12,LocalDate.parse("2014-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2014-12-13"),"");
		Barrel br111 = new Barrel(6, "Sehr Gut", 12 ,LocalDate.parse("2008-12-03"),14,LocalDate.parse("2014-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2014-12-24"),"");
		Barrel br121 = new Barrel(6, "Sehr Gut", 12 ,LocalDate.parse("2000-12-03"),15,LocalDate.parse("2014-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2014-12-25"),"");
		
		List<Barrel> barrels1 = new ArrayList<Barrel>();
		barrels1.add(br61);
		barrels1.add(br71);
		barrels1.add(br81);
		barrels1.add(br91);
		barrels1.add(br101);
		barrels1.add(br111);
		barrels1.add(br121);
		
		List<Barrel> barrels2 = new ArrayList<Barrel>();
		Barrel br271 = new Barrel(6,"Gut", 34 ,LocalDate.parse("1994-12-03"),22, LocalDate.parse("2007-12-03"),LocalDate.parse("2015-12-03"), LocalDate.parse("2007-12-03"),"");
		barrels2.add(br271);
		
		
		/*
		 * initialize bottles
		 */
		Bottle b1 = new Bottle("", 0.7, 0.0);
		Bottle b2 = new Bottle("", 0.7, 0.0);
		Bottle b3 = new Bottle("", 0.7, 0.0);
		Bottle b4 = new Bottle("Courvoisier Napoleon", 0.7, 0.0);
		Bottle b5 = new Bottle("" ,0.7, 0.0);
		Bottle b6 = new Bottle("", 0.3, 0.0);	
		Bottle b8 = new Bottle("Chatelier Cognac" ,0.7, 0.0);
		Bottle b10 = new Bottle("Frapin Domaine Château" ,0.7, 0.0);
			
		Bottle b11 = new Bottle("Monnet Cognac" ,0.7, 0.0);
		Bottle b21 = new Bottle("Courvoisier Napoleon", 0.7, 0.0);
		Bottle b7 = new Bottle("Claude Chatelier Extra", 1, 0.0);
		Bottle b9 = new Bottle("Delamain Vesper" ,0.7, 0.0);
		
		
		List<Bottle> bottles = new ArrayList<Bottle>();
		List<Bottle> bottles1 = new ArrayList<Bottle>();
		
		bottles.add(b1);
		bottles.add(b2);
		bottles.add(b3);
		bottles.add(b4);
		bottles.add(b5);
		bottles.add(b6);
		bottles.add(b8);
		bottles.add(b10);
		
		bottles1.add(b21);
		bottles1.add(b7);
		bottles1.add(b9);
		bottles1.add(b11);
		
		
		/*
		 * initialize stills
		 * 
		 * ============================================
		 * 				0 == free
		 * 				1 == still is running
		 * 				2 == still is ready
		 * ============================================
		 */
		Still still_1 = new Still(30, 0, 0, null, null);
		Still still_2 = new Still(30, 0, 0, null, null);
		Still still_3 = new Still(30, 0, 0, null, null);
		Still still_4 = new Still(30, 0, 0, null, null);
		
		List<Still> stills1 = new ArrayList<Still>();
		
		
		stills1.add(still_1);
		stills1.add(still_2);
		stills1.add(still_3);
		stills1.add(still_4);
		
		
		Still still_5 = new Still(30, 0, 0, null, null);
		
		List<Still> stills2 = new ArrayList<Still>();
		
		stills2.add(still_5);
		
		/*
		 * initialize departments
		 */
	
		Department bottlestock1 = departmentRepository.save(new BottleStock("FlaschenlagerA", bottles));
		Department bottlestock2 = departmentRepository.save(new BottleStock("FlaschenlagerB", bottles1));
		Department barrelstock1 = departmentRepository.save(new BarrelStock("FasslagerA", barrels));
		Department barrelstock2 = departmentRepository.save(new BarrelStock("FasslagerB", barrels1));
		Department barrelstock3 = departmentRepository.save(new BarrelStock("FasslagerC", barrels2));
		Department winestock1 = departmentRepository.save(new WineStock("WeinlagerA", 300));
		Department winestock2 = departmentRepository.save(new WineStock("WeinlagerB", 300));
		Department production1 = departmentRepository.save(new Production("ProduktionA", 1000, stills1));
		Department production2 = departmentRepository.save(new Production("ProduktionB", 1000, stills2));
		Department verkauf1 = departmentRepository.save(new Sale("VerkaufA"));
		Department verkauf2 = departmentRepository.save(new Sale("VerkaufB"));
		Department rechnungswesen = departmentRepository.save(new Accountancy("Rechnungswesen"));

		
		List<Department> list5 = new ArrayList<Department>();
		list5.add(bottlestock1);
		list5.add(winestock1);
		list5.add(barrelstock1);
		list5.add(production1);
		list5.add(verkauf1);
		
		List<Department> list6 = new ArrayList<Department>();
		list6.add(winestock2);
		list6.add(production2);
		list6.add(barrelstock2);
	
		List<Department> list7 = new ArrayList<Department>();
		list7.add(rechnungswesen);
		
		List<Department> list8 = new ArrayList<Department>();
		list8.add(verkauf2);
		list8.add(bottlestock2);
		list8.add(barrelstock3);
		
		
		/*
		 * initialize delivery
		 */
		
		Delivery deliver1 = deliveryRepository.save(new Delivery(100,new GregorianCalendar(2015,Calendar.JANUARY,25,0,0,0).getTimeInMillis(), "Standort 1"));
		Delivery deliver2 = deliveryRepository.save(new Delivery(100,new GregorianCalendar(2015,Calendar.FEBRUARY,9,0,0,0).getTimeInMillis(), "Standort 4"));
		
//		List<Delivery> deliver = new ArrayList<Delivery>();
////		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		Location loc1 = locationRepository.findByName("Standort 1");
//		Long id1 = loc1.getId();
//		Location loc4 = locationRepository.findByName("Standort 4");
//		Long id4 = loc4.getId();
//		
//		
//		Delivery deliver_1 = new Delivery(500, new GregorianCalendar(2015,Calendar.JANUARY,25,0,0,0).getTimeInMillis(), id1);
//		Delivery deliver_2 = new Delivery(120, new GregorianCalendar(2015,Calendar.FEBRUARY,9,0,0,0).getTimeInMillis(), id4);
//		Delivery deliver_3 = new Delivery(120, new GregorianCalendar(2015,Calendar.MARCH,8,0,0,0).getTimeInMillis(), id4);
//		Delivery deliver_4 = new Delivery(120, new GregorianCalendar(2015,Calendar.FEBRUARY,14,0,0,0).getTimeInMillis(), id4);
//		
//		deliver.add(deliver_1);
//		deliver.add(deliver_2);
//		deliver.add(deliver_3);
//		deliver.add(deliver_4);
		
		
		
		
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
		UserAccount barrelmakerAcc3 = userAccountManager.create("fassbinder3", "123", new Role("ROLE_BARRELMAKER"));
		userAccountManager.save(barrelmakerAcc3);
		UserAccount superAcc = userAccountManager.create("superuser", "123", new Role("ROLE_SUPERUSER"));
		userAccountManager.save(superAcc);
		
		/*
		 * initialize employees
		 */
		Employee e1 = employeeRepository.save(new Employee(warehousemanAcc, "lagerist1", "123", "Lagerist","Mueller","Klaus","200","klaus@Mueller.de","Klausstrasse"));
		Employee e2 = employeeRepository.save(new Employee(warehousemanAcc2, "lagerist2", "123", "Lagerist","Maier","Chris","200","klaus@Mueller.de","Klausstrasse"));
		Employee e10 = employeeRepository.save(new Employee(warehousemanAcc3, "lagerist3", "123", "Lagerist","Maier","Chris","200","klaus@Mueller.de","Klausstrasse"));
		Employee e3 = employeeRepository.save(new Employee(salesmanAcc, "verkaeufer1", "123", "Verkäufer","Fischer","Dieter","210","Dieter@Fischer.de","Dieterstrasse"));
		Employee e4 = employeeRepository.save(new Employee(salesmanAcc2, "verkaeufer2", "123", "Verkäufer","Fleischer","Detlef","210","Dieter@Fischer.de","Dieterstrasse"));
		Employee e5 = employeeRepository.save(new Employee(barrelmakerAcc, "fassbinder1", "123", "Fassbinder","Schmidt","Bernd","100","Bernd@Schmidt.de","Berndstrasse"));
		Employee e6 = employeeRepository.save(new Employee(barrelmakerAcc2, "fassbinder2", "123", "Fassbinder","Schmiedel","Bruno","100","Bernd@Schmidt.de","Berndstrasse"));
		Employee e11 = employeeRepository.save(new Employee(barrelmakerAcc3, "fassbinder3", "123", "Fassbinder","Schedel","Bruno","1300","Be@Schmt.de","Bzzestrasse"));
		Employee e7 = employeeRepository.save(new Employee(brewerAcc, "braumeister1", "123", "Braumeister","Smith","Johannes","250","Johannes@Smith.de","Johannesstreet"));
		Employee e8 = employeeRepository.save(new Employee(brewerAcc2, "braumeister2", "123", "Braumeister","Smittie","Joe","250","Johannes@Smith.de","Johannesstreet"));
		Employee e9 = employeeRepository.save(new Employee(adminAcc, "admin", "123", "Admin","Kowalsky","Günther","120","Guenther@Kowalsky.de","Guentherstrasse"));
		
		
		List<Employee> list1 = new ArrayList<Employee>();
		list1.add(e1);
		list1.add(e7);
		list1.add(e4);
		list1.add(e5);
		
		List<Employee> list2 = new ArrayList<Employee>();
		list2.add(e10);
		list2.add(e6);
		list2.add(e8);

		List<Employee> list3 = new ArrayList<Employee>();
		list3.add(e9);
		
		List<Employee> list4 = new ArrayList<Employee>();
		list4.add(e2);
		list4.add(e3);
		list4.add(e11);
		
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

		
		/*
		 * initialize customer accounts
		 */
		final Role userRole = new Role("ROLE_CUSTOMER");

		UserAccount ua1 = userAccountManager.create("hans", "123", userRole);
		userAccountManager.save(ua1);
		
		Customer c1 = new Customer(ua1, "hans", "123", "Dittrich", "Günther", "Hauptstraße 5");
		customerRepository.save(c1);
		
		
		incomeRepository.save(new Income("Hans Klausen", LocalDate.of(2014, 6, 8), 46.95, "Produktkauf"));
		incomeRepository.save(new Income("Dieter Petersen", LocalDate.of(2014, 11, 18), 46.95, "Produktkauf"));
		incomeRepository.save(new Income("Klaus Klausen", LocalDate.of(2014, 12, 24), 46.95, "Produktkauf"));
		incomeRepository.save(new Income("Marianne Müller", LocalDate.of(2014, 7, 28), 46.95, "Produktkauf"));
		incomeRepository.save(new Income("Peter Peterson", LocalDate.of(2014, 7, 14), 46.95, "Produktkauf"));
	}
	
	/*
	 * initialize catalog
	 */
	public void initializeSortiment(Inventory<InventoryItem> inventory) {
		
		// Die Bilder sind von der Internetseite: http://de.123rf.com und  http://www.pixelio.de
		

		Article article1 = new Article("chatelier", "Claude Chatelier Extra", "20 Jahre", Money.of(EUR, 46.95), "40,0 %",1, "Cognac");
		articleRepository.save(article1);
		
		Article article2 = new Article("chateliere", "Chatelier Cognac", "8 Jahre", Money.of(EUR, 41.90 ), "40,0 %",0.7,"Cognac");
		articleRepository.save(article2);

		Article article3 = new Article("chabassenapoleon", "Courvoisier Napoleon ", "5 Jahre", Money.of(EUR, 79.90), "40,0 %",0.7,"Cognac");
		articleRepository.save(article3);
		
		Article article4 = new Article("delamain-vesper", "Delamain Vesper", "35 Jahre", Money.of(EUR, 97.95), "40,0 %",0.7,"Cognac");
		articleRepository.save(article4);
		
		Article article5 = new Article("fontpinot", "Frapin Domaine Château", "5 Jahre", Money.of(EUR, 46.95), "30,0 %",0.7,"Cognac");
		articleRepository.save(article5);
		
		Article article6 = new Article("monnet", "Monnet Cognac", "5 Jahre", Money.of(EUR, 26.95), "40,0 %",0.7,"Cognac");
		articleRepository.save(article6); 		
				
		InventoryItem i1 = new InventoryItem(article1, Units.ONE);
		inventory.save(i1);
		
		InventoryItem i2 = new InventoryItem(article2, Units.ONE);
		inventory.save(i2);
		
		InventoryItem i3 = new InventoryItem(article3, Units.ONE);
		inventory.save(i3);
		
		InventoryItem i4 = new InventoryItem(article4, Units.ONE);
		inventory.save(i4);
		
		InventoryItem i5 = new InventoryItem(article5, Units.ONE);
		inventory.save(i5);
		
		InventoryItem i6 = new InventoryItem(article6, Units.ONE);
		inventory.save(i6);
		
	}
	
	/*
	 * initialize recipes
	 */
	private void initializeCookBook(CookBookRepository cookbookrepository) 
	{
		
		Ingredient i1 = new Ingredient("Gut", 		4, 4, "Liter");
		Ingredient i2 = new Ingredient("Schlecht", 	5, 15, "Liter");
		Ingredient i3 = new Ingredient("Wasser", 	0, 3,  "Liter");

		
		List<Ingredient> mapIngredients1 = new ArrayList<Ingredient>();
		
		mapIngredients1.add(i1);
		mapIngredients1.add(i2);
		mapIngredients1.add(i3);
		
		cookbookrepository.save(new Recipe("Chatelier Cognac", mapIngredients1));
		
		Ingredient i4 = new Ingredient("Sehr Gut", 14, 10,"Liter");
		Ingredient i5 = new Ingredient("Gut", 20, 18,"Liter");
		Ingredient i6 = new Ingredient("Wasser", 0,12, "Liter");
		
		List<Ingredient> mapIngredients2 = new ArrayList<Ingredient>();
		
		mapIngredients2.add(i4);
		mapIngredients2.add(i5);
		mapIngredients2.add(i6);
		
		cookbookrepository.save(new Recipe("Delamain Vesper", mapIngredients2));
		
		Ingredient i7 = new Ingredient("Gut", 14, 13,"Liter");
		Ingredient i8 = new Ingredient("Schlecht", 20, 12,"Liter");
		Ingredient i9 = new Ingredient("Wasser", 0, 12,"Liter");
		
		List<Ingredient> mapIngredients3 = new ArrayList<Ingredient>();
		
		mapIngredients3.add(i7);
		mapIngredients3.add(i8);
		mapIngredients3.add(i9);
		
		cookbookrepository.save(new Recipe("Claude Chatelier Extra", mapIngredients3));	
			
		Ingredient i10 = new Ingredient("Sehr Gut", 20, 14,"Liter");
		Ingredient i11 = new Ingredient("Gut", 12, 15,"Liter");
		Ingredient i12 = new Ingredient("Wasser", 0, 12,"Liter");
		
		List<Ingredient> mapIngredients4 = new ArrayList<Ingredient>();
		
		mapIngredients4.add(i10);
		mapIngredients4.add(i11);
		mapIngredients4.add(i12);
		
		cookbookrepository.save(new Recipe("Monnet Cognac", mapIngredients4));

		Ingredient i13 = new Ingredient("Gut", 25,3, "Liter");
		Ingredient i14 = new Ingredient("Schlecht", 10,12, "Liter");
		Ingredient i15 = new Ingredient("Sehr Gut", 30, 12,"Liter");
		Ingredient i155 = new Ingredient("Wasser", 0, 12,"Liter");
		
		List<Ingredient> mapIngredients5 = new ArrayList<Ingredient>();
		
		mapIngredients5.add(i13);
		mapIngredients5.add(i14);
		mapIngredients5.add(i15);
		mapIngredients5.add(i155);
		
		cookbookrepository.save(new Recipe("Courvoisier Napoleon", mapIngredients5));

		Ingredient i16 = new Ingredient("Gut", 10, 3,"Liter");
		Ingredient i17 = new Ingredient("Schlecht", 120, 5,"Liter");
		Ingredient i18 = new Ingredient("Sehr Gut", 40, 29, "Liter");
		Ingredient i188 = new Ingredient("Wasser", 0, 29, "Liter");
		
		List<Ingredient> mapIngredients6 = new ArrayList<Ingredient>();
		
		mapIngredients6.add(i16);
		mapIngredients6.add(i17);
		mapIngredients6.add(i18);
		mapIngredients6.add(i188);
		
		cookbookrepository.save(new Recipe("Frapin Domaine Château", mapIngredients6));
		
		
		
	}
}
