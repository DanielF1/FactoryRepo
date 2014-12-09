package factory;

import static org.joda.money.CurrencyUnit.EUR;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.joda.money.Money;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountIdentifier;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import factory.model.Article;
import factory.model.ArticleRepository;
import factory.model.Barrel;
import factory.model.BarrelList;
import factory.model.Bottle;
import factory.model.BottleStock;
import factory.model.BottleStockList;
import factory.model.CookBookRepository;
import factory.model.Department;
import factory.model.DepartmentRepository;
import factory.model.Employee;
import factory.model.EmployeeRepository;
import factory.model.Ingredient;
import factory.model.Location;
import factory.model.LocationRepository;
import factory.model.Recipe;

@Component
public class CognacFactoryDataInitializer implements DataInitializer {

//	private final Inventory<InventoryItem> inventory;
	private final LocationRepository locationRepository;
	private final DepartmentRepository departmentRepository;
	private final EmployeeRepository employeeRepository;
	private final UserAccountManager userAccountManager;
	private final ArticleRepository articleRepository;

	private final CookBookRepository cookbookrepository;
	private final BarrelList barrelList;
	private final BottleStockList bottlestocklist;
//	private final BarrelStock_Inter barrelstock_inter;

	@Autowired
	public CognacFactoryDataInitializer(UserAccountManager userAccountManager, 
										LocationRepository locationRepository, 
										DepartmentRepository departmentRepository,
										EmployeeRepository employeeRepository,
										ArticleRepository articleRepository,
										CookBookRepository cookbookrepository, 
										Inventory<InventoryItem> inventory,
										BarrelList barrelList,
										BottleStockList bottlestocklist) {

		
		Assert.notNull(locationRepository, "LocationRepository must not be null!");
		Assert.notNull(departmentRepository, "DepartmentRepository must not be null!");
		Assert.notNull(employeeRepository, "EmployeeRepository must not be null!");
		Assert.notNull(userAccountManager, "UserAccountManager must not be null!");
		Assert.notNull(cookbookrepository, "CookBook must not be null!");
		Assert.notNull(inventory, "Inventory must not be null!");
		Assert.notNull(barrelList, "Barrellist must not be null!");
		Assert.notNull(bottlestocklist, "Bottle Stock must not be null!");
		
		this.userAccountManager = userAccountManager;

		this.locationRepository = locationRepository;	
		this.departmentRepository = departmentRepository;
		this.employeeRepository = employeeRepository;
		this.articleRepository = articleRepository;
		this.cookbookrepository = cookbookrepository;
//		this.inventory = inventory;
		this.barrelList = barrelList;
		this.bottlestocklist = bottlestocklist;
	
	}

	@Override
	public void initialize() {
		initializeLocList(locationRepository, departmentRepository, employeeRepository);
		initializeUsers(userAccountManager);
		initializeSortiment();
		initializeCookBook(cookbookrepository);
//		initializeStock2(stock2);
		initializeBarrelList(barrelList);
		initializeBottlestock(bottlestocklist);
	}
	

	private void initializeLocList(LocationRepository locationRepository, DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {

		Department d1 = departmentRepository.save(new Department("Flaschenlager"));
		Department d2 = departmentRepository.save(new Department("Fasslager"));
		Department d3 = departmentRepository.save(new Department("Weinlager"));
		Department d4 = departmentRepository.save(new Department("Produktion"));
		Department d5 = departmentRepository.save(new Department("Verkauf"));
		Department d6 = departmentRepository.save(new Department("Verwaltung"));
		
		List<Department> list5 = new ArrayList<Department>();
		list5.add(d1);
		list5.add(d4);
		list5.add(d5);
		
		List<Department> list6 = new ArrayList<Department>();
		list6.add(d2);
		list6.add(d3);
	
		List<Department> list7 = new ArrayList<Department>();
		list7.add(d6);
		
		List<Department> list8 = new ArrayList<Department>();
		list8.add(d1);
		list8.add(d4);
		list8.add(d5);
		list8.add(d2);
		
		
		
		Employee e1 = employeeRepository.save(new Employee("Lagerist","Mueller","Klaus","200","klaus@Mueller.de","Klausstrasse"));
		Employee e2 = employeeRepository.save(new Employee("Verkäufer","Fischer","Dieter","210","Dieter@Fischer.de","Dieterstrasse"));
		Employee e3 = employeeRepository.save(new Employee("Fassbinder","Schmidt","Bernd","100","Bernd@Schmidt.de","Berndstrasse"));
		Employee e4 = employeeRepository.save(new Employee("Braumeister","Smith","Johannes","250","Johannes@Smith.de","Johannesstreet"));
		Employee e5 = employeeRepository.save(new Employee("Admin","Kowalsky","Günther","120","Guenther@Kowalsky.de","Guentherstrasse"));
		
		
		List<Employee> list1 = new ArrayList<Employee>();
		list1.add(e1);
		list1.add(e4);
		list1.add(e3);
		
		List<Employee> list2 = new ArrayList<Employee>();
		list2.add(e2);
		list2.add(e3);
		list2.add(e4);
		
		List<Employee> list3 = new ArrayList<Employee>();
		list3.add(e5);
		
		
		List<Employee> list4 = new ArrayList<Employee>();
		list4.add(e1);
		list4.add(e3);
		
		
	
		if (locationRepository.findAll().iterator().hasNext()) {
			return;
		}

		locationRepository.save(new Location("Standort 1","Klausstrasse 1", "0999 Klaushausen", "81906666", "klaus@klaus.de", list1, list5));
		locationRepository.save(new Location("Standort 2","Klausstrasse 2", "0997 Klausberg", "81904446", "klaus1klaus@klaus.de", list2, list6));
		locationRepository.save(new Location("Standort 3","Klausstrasse 3", "0998 Klaustal", "9454366", "klaus@haus.com", list3, list7));
		locationRepository.save(new Location("Standort 4","Klausstrasse 4", "0996 Klausklausen", "8231611", "KLAUS@klaus.de", list4, list8));
		
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
		


//		for (Article article : articleRepository.findAll()) {
//			InventoryItem inventoryItem = new InventoryItem(article, Units.TEN);
//			inventory.save(inventoryItem);
//				}
//		

	}

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
	

	void initializeBottlestock(BottleStockList bottlestocklist) 
	{

		Bottle b1 = new Bottle(0.7);
		Bottle b2 = new Bottle(0.7);
		Bottle b3 = new Bottle(0.3);
		
		List<Bottle> empty = new ArrayList<Bottle>();
		List<Bottle> full = new ArrayList<Bottle>();
		
		empty.add(b1);
		empty.add(b3);
		full.add(b2);
		
//		stock2.save(new BottleStock("Lager A", 100, empty, 20, full));
		bottlestocklist.save(new BottleStock("Bottle Stock A", empty, full));
	}
	
	
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

		System.out.println("0000000000000000000000000000000000000000000000000000000000000000000000000000");
		
	}
	
}
