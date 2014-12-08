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
	private String name;
	private String firstname;
	private String address;

	@OneToOne
	private UserAccount userAccount;

	@Deprecated
	protected Customer() {
	}

	public Customer(UserAccount userAccount, String address, String name, String firstname) {
		this.userAccount = userAccount;
		this.address = address;
		this.name = name;
		this.firstname = firstname;
		
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

}