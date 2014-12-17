package factory.model.validation;

import org.hibernate.validator.constraints.NotEmpty;

public class InsertBarrel {
	
	
	private String Content;
	private double Content_amount;
	@NotEmpty (message = "Required field")
	private String Barrel_volume;
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public double getContent_amount() {
		return Content_amount;
	}
	public void setContent_amount(double content_amount) {
		Content_amount = content_amount;
	}
	public String getBarrel_volume() {
		return Barrel_volume;
	}
	public void setBarrel_volume(String barrel_volume) {
		Barrel_volume = barrel_volume;
	}

	
	
//	public String getBirthdate_of_barrel() {
//		return Birthdate_of_barrel;
//	}
//	public void setBirthdate_of_barrel(String birthdate_of_barrel) {
//		Birthdate_of_barrel = birthdate_of_barrel;
//	}
//	public String getDeath_of_barrel() {
//		return Death_of_barrel;
//	}
//	public void setDeath_of_barrel(String death_of_barrel) {
//		Death_of_barrel = death_of_barrel;
//	}
//	public String getLastFill() {
//		return LastFill;
//	}
//	public void setLastFill(String lastFill) {
//		LastFill = lastFill;
//	}
//	
	
}
