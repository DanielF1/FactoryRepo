package factory.model.validation;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class InsertBarrel {
	
	@NotNull(message = "Required field")
	private Long Id;
	private String Content;
	private double Amount;
	@NotEmpty (message = "Required field")
	private String Birthdate_of_barrel;
	@NotEmpty (message = "Required field")
	private String Death_of_barrel;
	@NotEmpty (message = "Required field")
	private String LastFill;
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public double getAmount() {
		return Amount;
	}
	public void setAmount(double amount) {
		Amount = amount;
	}
	public String getBirthdate_of_barrel() {
		return Birthdate_of_barrel;
	}
	public void setBirthdate_of_barrel(String birthdate_of_barrel) {
		Birthdate_of_barrel = birthdate_of_barrel;
	}
	public String getDeath_of_barrel() {
		return Death_of_barrel;
	}
	public void setDeath_of_barrel(String death_of_barrel) {
		Death_of_barrel = death_of_barrel;
	}
	public String getLastFill() {
		return LastFill;
	}
	public void setLastFill(String lastFill) {
		LastFill = lastFill;
	}
	
	
}
