package factory.model.validation;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class InsertBarrel {
	
	@NotEmpty (message = "Required field")
	@Min(5)
	private String Barrel_volume;
	@NotNull
	@Min(1)
	private int Barrel_anzahl;
	
	public String getBarrel_volume() {
		return Barrel_volume;
	}
	public void setBarrel_volume(String barrel_volume) {
		Barrel_volume = barrel_volume;
	}
	public int getBarrel_anzahl() {
		return Barrel_anzahl;
	}
	public void setBarrel_anzahl(int barrel_anzahl) {
		Barrel_anzahl = barrel_anzahl;
	}
	
}
