package factory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotEmpty;
import org.salespointframework.useraccount.UserAccount;

/**
 * Customer ist eine Person, die hergestellte Produkte der Firma kaufen kann
 */
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

	
	/** 
	 * Default Constructor
	 */
	@Deprecated
	protected Customer() {
	}
	
	/**
	 * 
	 * @param userAccount Benutzerkonto des Kunden
	 * @param username Benutzername des Kunden
	 * @param password Passwort des Kunden
	 * @param familyname Nachname des Kunden
	 * @param firstname Vorname des Kunden
	 * @param address Adresse des Kunden
	 */
	public Customer(UserAccount userAccount, String username, String password, String familyname, String firstname, String address) {
		this.userAccount = userAccount;
		this.address = address;
		this.familyname = familyname;
		this.firstname = firstname;
		this.username = username;
		this.password = password;
		
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
	 * @return familyname
	 */
	public String getFamilyname() {
		return familyname;
	}
	
	/**
	 * setter
	 * @param familyname
	 */
	public void setFamilyname(String familyname) {
		this.familyname = familyname;
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
	 * @return id
	 */
	public long getId() {
		return id;
	}

	/**
	 * getter 
	 * @return userAccount
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