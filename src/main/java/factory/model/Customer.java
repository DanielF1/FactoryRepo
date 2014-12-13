package factory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.salespointframework.useraccount.UserAccount;

@Entity

public class Customer {

	@Id
	@GeneratedValue
	private long id;
	private String familyname;
	private String firstname;
	private String address;
	@OneToOne
	private UserAccount userAccount;

	@Deprecated
	protected Customer() {
	}

	public Customer(UserAccount userAccount, String familyname, String firstname, String address) {
		this.userAccount = userAccount;
		this.address = address;
		this.familyname = familyname;
		this.firstname = firstname;
		
	}
	
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
}