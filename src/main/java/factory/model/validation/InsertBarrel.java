package factory.model.validation;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class InsertBarrel {
	
	@NotNull(message = "Required field")
	private Long Id;
	private String content;
	private double amount;
	@NotEmpty (message = "Required field")
	private String birthdate_of_barrel;
	@NotEmpty (message = "Required field")
	private String death_of_barrel;
	@NotEmpty (message = "Required field")
	private String lastFill;
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getBirthdate_of_barrel() {
		return birthdate_of_barrel;
	}
	public void setBirthdate_of_barrel(String birthdate_of_barrel) {
		this.birthdate_of_barrel = birthdate_of_barrel;
	}
	public String getDeath_of_barrel() {
		return death_of_barrel;
	}
	public void setDeath_of_barrel(String death_of_barrel) {
		this.death_of_barrel = death_of_barrel;
	}
	public String getLastFill() {
		return lastFill;
	}
	public void setLastFill(String lastFill) {
		this.lastFill = lastFill;
	}
	
	
}
