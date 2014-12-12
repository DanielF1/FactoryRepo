package factory.model.validation;

import org.hibernate.validator.constraints.NotEmpty;


public class RegistrationForm {

	@NotEmpty(message = "{RegistrationForm.name.NotEmpty}")
	private String name;

	@NotEmpty(message = "{RegistrationForm.password.NotEmpty}")
	private String password;

	@NotEmpty(message = "{RegistrationForm.familyname.NotEmpty}")
	private String familyname;
	
	@NotEmpty(message = "{RegistrationForm.firstname.NotEmpty}")
	private String firstname;

	@NotEmpty(message = "{RegistrationForm.address.NotEmpty}")
	private String address;
	
	
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
