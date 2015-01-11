package factory.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.salespointframework.useraccount.UserAccount;


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
	
	/* 
	 * default Konstruktor
	 */
	@Deprecated
	public Employee(){}
	
	@OneToOne
	private UserAccount userAccount;
	
	/*
	 * Konstruktor
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
	
	/*
	 * Getter und Setter
	 */
	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public String getWorkplace() {
		return workplace;
	}
	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}
	public String getFamilyname() {
		return familyname;
	}
	public void setFamilyname(String name) {
		this.familyname = name;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
