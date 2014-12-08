package factory.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;


@Entity
public class Location {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String address;
	private String city;
	private String telefon;
	private String mail;
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Employee> employees;
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Department> departments;
	@OneToOne(cascade = CascadeType.ALL)
	private ProductionManagement productionManagement;
	
	
	@Deprecated
	public Location() {
	}


	public Location(String name, String address, String city, String telefon,
			String mail, List<Employee> employees, List<Department> departments) {
		this.name = name;
		this.address = address;
		this.city = city;
		this.telefon = telefon;
		this.mail = mail;
		this.employees = employees;
		this.departments = departments;

	}

//	public int getWineQuantity() {
//		return getProductionManagementDepartment().getWineQuantity();
//	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getTelefon() {
		return telefon;
	}

	public String getMail() {
		return mail;
	}

	public Long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	// check if this Location contains Production management Department
//	public boolean containsProductionmanagement() {
//		for (Department dept : this.departments) {
//			if (dept.getName().contains("Produ")) { // :-(
//				return true;
//			}
//		}
//		return false;
//	}
//
//	public ProductionManagement getProductionManagementDepartment() {
//		if (this.productionManagement == null)
//			if (this.containsProductionmanagement()) {
//				this.productionManagement = new ProductionManagement();
//			}
//		return this.productionManagement;
//	}
//
	// für Liste von Locations mit Productionmanagement
//	public static List<Location> getLocationsListWithProductionManagement() {
//		Iterable<Location> locations = locationRepository.findAll();
//		List<Location> result = new ArrayList<Location>();
//		for (Location location : locations)
//			if (location.containsProductionmanagement())
//				result.add(location);
//		return result;
//	}
//	
//	
//	public Transport deliverWine(int quantity, Date date){
//			throws Exception {
//		ProductionManagement dept = location.getWineDepartment();
//		if (dept == null)
//			throw new Exception();
//		
//		ProductionManagement dept = getProductionManagementDepartment();
//		if (!dept.isOverflow(quantity, date)) {
//			dept.deliverWine(quantity);
//			return null;
//		} else {
//
//		int overflow = this.productionManagement.overflowQuantity(quantity, date);
//		dept.deliverWine(quantity - overflow);
//		quantity = overflow;} 
//		
//		for (Location loc : Location.getLocationsListWithProductionManagement()) {
//			ProductionManagement locDept = loc.getProductionManagementDepartment();
//			if (!locDept.isOverflow(quantity, date)) {
//				locDept.deliverWine(quantity);
//				Transport transport = new Transport(this, loc, quantity);
//				return transport;
//			}
//		}
//		
//		// Der Rest kommt doch in selektierte Location
//		dept.deliverWine(quantity );
//		
//		return null;
//	}
//
//	public static Location getLocationById(long id) {
//		return locationRepository.findOne(id);
//	}
//
//	public void Save() {
//		locationmanagement.save(this);
//	}

}