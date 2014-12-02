package videoshop.model.validation;

import org.hibernate.validator.constraints.NotEmpty;


public class RegistrationForm {

	@NotEmpty(message = "{RegistrationForm.name.NotEmpty}")
	private String name;

	@NotEmpty(message = "{RegistrationForm.password.NotEmpty}")
	private String password;

//	@NotEmpty(message = "{RegistrationForm.address.NotEmpty}")
//	private String address;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
//	public String getAddress() {
//		return address;
//	}
//	public void setAddress(String address) {
//		this.address = address;
//	}
}
