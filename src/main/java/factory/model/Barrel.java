package factory.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Barrel {

	private @Id @GeneratedValue Long id;
	private String content;
	private double barrel_amount;
	private double barrel_content_amount;
	private LocalDate birthdate_of_barrel;
	private LocalDate death_of_barrel;
	private LocalDate lastFill;
	
	public Barrel(String content, double barrel_amount, double barrel_content_amount, LocalDate birthdate_of_barrel,
					LocalDate death_of_barrel, LocalDate lastFill)
	{
		this.content = content;
		this.barrel_amount = barrel_amount;
		this.barrel_content_amount = barrel_content_amount;
		this.birthdate_of_barrel = birthdate_of_barrel;
		this.death_of_barrel = death_of_barrel;
		this.lastFill = lastFill;
	}
	
	public Barrel(String content, double barrel_amount, double barrel_content_amount, String birthdate_of_barrel,
			String death_of_barrel, String lastFill)
	{
		this.content = content;
		this.barrel_amount = barrel_amount;
		this.barrel_content_amount = barrel_content_amount;
		this.birthdate_of_barrel = LocalDate.parse(birthdate_of_barrel);
		this.death_of_barrel = LocalDate.parse(death_of_barrel);
		this.lastFill = LocalDate.parse(lastFill);
	}
	
	Barrel(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public LocalDate getBirthdate_of_barrel() {
		return birthdate_of_barrel;
	}

	public void setBirthdate_of_barrel(LocalDate birthdate_of_barrel) {
		this.birthdate_of_barrel = birthdate_of_barrel;
	}

	public LocalDate getDeath_of_barrel() {
		return death_of_barrel;
	}

	public void setDeath_of_barrel(LocalDate death_of_barrel) {
		this.death_of_barrel = death_of_barrel;
	}

	public LocalDate getLastFill() {
		return lastFill;
	}

	public void setLastFill(LocalDate lastFill) {
		this.lastFill = lastFill;
	}
	
	public double getBarrel_amount() {
		return barrel_amount;
	}

	public void setBarrel_amount(double barrel_amount) {
		this.barrel_amount = barrel_amount;
	}

	public double getBarrel_content_amount() {
		return barrel_content_amount;
	}

	public void setBarrel_content_amount(double barrel_content_amount) {
		this.barrel_content_amount = barrel_content_amount;
	}

	public int getAlter(){
		int datecount =0;
		int ANZAHL_TAGE_IM_JAHR = 365;
		int Alter ;
		while (getLastFill().compareTo(LocalDate.now())<0){
			datecount++;
			setLastFill(getLastFill().plusDays(1));
		}	
			Alter = datecount/ANZAHL_TAGE_IM_JAHR;			
		System.out.println(getContent() + getBarrel_amount()+ " Date count" + datecount + " alter" + Alter);
		return Alter;
	}
	
}
