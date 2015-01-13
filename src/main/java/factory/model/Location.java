package factory.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.validator.constraints.NotEmpty;


@Entity
public class Location {

	@Id
	@GeneratedValue
	private Long id;
	
	
	@NotEmpty(message = "{RegistrationForm.locname.NotEmpty}")
	private String name;
	@NotEmpty(message = "{RegistrationForm.address.NotEmpty}")
	private String address;
	@NotEmpty(message = "{RegistrationForm.city.NotEmpty}")
	private String city;
	@NotEmpty(message = "{RegistrationForm.telefon.NotEmpty}")
	private String telefon;
	@NotEmpty(message = "{RegistrationForm.mail.NotEmpty}")
	private String mail;
	
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Employee> employees;
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Department> departments;

	
	
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

	public WineStock getWineStockDepartment() {
	
		List<Department> deps = this.getDepartments();
		for (Department depwein : deps) {
			if (depwein.getName().contains("Wein")) {
				return (WineStock)depwein;
			}
		}
		return null;
	}
	
}