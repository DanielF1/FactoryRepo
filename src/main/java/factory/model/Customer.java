package factory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotEmpty;
import org.salespointframework.useraccount.UserAccount;

@Entity

public class Customer {

	@Id
	@GeneratedValue
	private long id;
	@NotEmpty(message = "{RegistrationForm.name.NotEmpty}")
	private String username;
	@NotEmpty(message = "{RegistrationForm.password.NotEmpty}")
	private String password;
	@NotEmpty(message = "{RegistrationForm.familyname.NotEmpty}")
	private String familyname;
	@NotEmpty(message = "{RegistrationForm.firstname.NotEmpty}")
	private String firstname;
	@NotEmpty(message = "{RegistrationForm.address.NotEmpty}")
	private String address;
	@OneToOne
	private UserAccount userAccount;

	
	/*
	 * Konstruktor
	 */
	@Deprecated
	protected Customer() {
	}

	/*
	 * Konstruktor
	 */
	public Customer(UserAccount userAccount, String username, String password, String familyname, String firstname, String address) {
		this.userAccount = userAccount;
		this.address = address;
		this.familyname = familyname;
		this.firstname = firstname;
		this.username = username;
		this.password = password;
		
	}
	
	/*
	 * Getter und Setter
	 */
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFamilyname() {
		return familyname;
	}

	public void setFamilyname(String familyname) {
		this.familyname = familyname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public long getId() {
		return id;
	}
	
	public UserAccount getUserAccount() {
		return userAccount;
	}
	
	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
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