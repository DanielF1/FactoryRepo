package factory.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Employee {

	@Id 
	@GeneratedValue
	private Long id;
	private String workplace;
	private String name;
	private String firstname;
	private String salary;
	private String mail;
	private String address;
	
	@Deprecated
	public Employee(){}
	
	public Employee(String workplace, String name, String firstname,
			String salary, String mail, String address) {
		this.workplace = workplace;
		this.name = name;
		this.firstname = firstname;
		this.salary = salary;
		this.mail = mail;
		this.address = address;
	}
	
	public String getWorkplace() {
		return workplace;
	}
	public void setWorkplace(String workplace) {
		this.workplace = workplace;
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
}
