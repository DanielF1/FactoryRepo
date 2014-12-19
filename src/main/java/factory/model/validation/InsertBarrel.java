package factory.model.validation;

import org.hibernate.validator.constraints.NotEmpty;

public class InsertBarrel {
	
	@NotEmpty (message = "Required field")
	private String Barrel_volume;
	
	public String getBarrel_volume() {
		return Barrel_volume;
	}
	public void setBarrel_volume(String barrel_volume) {
		Barrel_volume = barrel_volume;
	}	
}
