package factory.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.salespointframework.useraccount.UserAccount;

/**
 * Ein Employee ist eine Person, die in der Firma verschiedene Arbeiten verrichtet
 */

@Entity
public class Employee {

	@Id 
	@GeneratedValue
	private Long id;
	@NotEmpty(message = "{RegistrationForm.name.NotEmpty}")
	private String username;
	@NotEmpty(message = "{RegistrationForm.password.NotEmpty}")
	private String password;
	@NotEmpty
	private String workplace;
	@NotEmpty(message = "{RegistrationForm.familyname.NotEmpty}")
	private String familyname;
	@NotEmpty(message = "{RegistrationForm.firstname.NotEmpty}")
	private String firstname;
	@NotEmpty(message = "{RegistrationForm.salary.NotEmpty}")
	private String salary;
	@NotEmpty(message = "{RegistrationForm.mail.NotEmpty}")
	@Email
	private String mail;
	@NotEmpty(message = "{RegistrationForm.address.NotEmpty}")
	private String address;
	@OneToOne
	private UserAccount userAccount;
	
	/** 
	 * Default Constructor
	 */
	@Deprecated
	public Employee(){}

	/**
	 * Constructor
	 * 
	 * @param userAccount Benutzerkonto des Mitarbeiters
	 * @param username Benutzername des Mitarbeiters
	 * @param password Passwort des Mitarbeiters
	 * @param workplace Arbeitsplatz des Mitarbeiters
	 * @param name Nachname des Mitarbeiters
	 * @param firstname Vorname des Mitarbeiters
	 * @param salary Gehalt des Mitarbeiters
	 * @param mail E-Mail Adresse des Mitarbeiters
	 * @param address Adresse des Mitarbeiters
	 */
	public Employee(UserAccount userAccount, String username, String password, String workplace, String name, String firstname,
			String salary, String mail, String address) {
		this.workplace = workplace;
		this.familyname = name;
		this.firstname = firstname;
		this.salary = salary;
		this.mail = mail;
		this.address = address;
		this.userAccount = userAccount;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * getter
	 * @return serAccount
	 */
	public UserAccount getUserAccount() {
		return userAccount;
	}
	
	/**
	 * setter
	 * @param userAccount
	 */
	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	/**
	 * getter
	 * @return workplace
	 */
	public String getWorkplace() {
		return workplace;
	}
	
	/**
	 * setter
	 * @param workplace
	 */
	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}
	
	/**
	 * getter
	 * @return familyname
	 */
	public String getFamilyname() {
		return familyname;
	}
	
	/**
	 * setter
	 * @param name
	 */
	public void setFamilyname(String name) {
		this.familyname = name;
	}
	
	/**
	 * getter
	 * @return firstname
	 */
	public String getFirstname() {
		return firstname;
	}
	
	/**
	 * setter
	 * @param firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	/**
	 * getter
	 * @return salary
	 */
	public String getSalary() {
		return salary;
	}
	
	/**
	 * setter
	 * @param salary
	 */
	public void setSalary(String salary) {
		this.salary = salary;
	}
	
	/**
	 * getter
	 * @return  mail
	 */
	public String getMail() {
		return mail;
	}
	
	/**
	 * setter
	 * @param mail
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	/**
	 * getter
	 * @return address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * setter
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * getter
	 * @return id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * getter
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * setter
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * getter
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * setter
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}
