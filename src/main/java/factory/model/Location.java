package factory.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Locations sind Produktions- und Verwaltungsstätten der Firma, 
 * wo verschiedene Employees in verschiedenen Departments arbeiten
 */
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

	
	/** 
	 * Default Constructor
	 */
	@Deprecated
	public Location() {
	}

	/**
	 *  Constructor
	 * 
	 * @param name Name des Standortes
	 * @param address Adresse des Standortes
	 * @param city Ort des Standortes
	 * @param telefon Telefonnummer des Standortes
	 * @param mail E-Mail Adresse des Standortes
	 * @param employees Mitarbeiter des Standortes
	 * @param departments Abteilungen des Standortes
	 */
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

	/**
	 * getter
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/** 
	 * getter
	 * @return address
	 */
	public String getAddress() {
		return address;
	}

	/** 
	 * getter
	 * @return city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * getter
	 * @return telefon
	 */
	public String getTelefon() {
		return telefon;
	}

	/**
	 * getter
	 * @return mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * getter
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * setter
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * setter
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * setter
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * setter
	 * @param telefon
	 */
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	/**
	 * setter
	 * @param mail
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * sgtter
	 * @return employees
	 */
	public List<Employee> getEmployees() {
		return employees;
	}

	/**
	 * setter
	 * @param employees
	 */
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	/**
	 * setter
	 * @param departments
	 */
	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	/**
	 * getter
	 * @return departments
	 */
	public List<Department> getDepartments() {
		return departments;
	}
}