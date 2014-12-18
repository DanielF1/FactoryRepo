package factory.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Barrel {

	private @Id @GeneratedValue Long id;
	private String content;
	private double content_amount;
	private double barrel_volume;
	private LocalDate manufacturing_date;
	private LocalDate birthdate_of_barrel;
	private LocalDate death_of_barrel;
	private LocalDate lastFill;
	private String position;
	
	
	public Barrel(String content, double content_amount, double barrel_volume, LocalDate manufacturing_date,
			LocalDate birthdate_of_barrel,LocalDate death_of_barrel, LocalDate lastFill,String position)
	{
		this.content = content;
		this.content_amount = content_amount;
		this.barrel_volume = barrel_volume;
		this.manufacturing_date = manufacturing_date;
		this.birthdate_of_barrel = birthdate_of_barrel;
		this.death_of_barrel = death_of_barrel;
		this.lastFill = lastFill;
		this.position = position;
		

	}
	public Barrel(String content, double content_amount, String barrel_volume, String manufacturing_date,
			String birthdate_of_barrel,String death_of_barrel, String lastFill,String position)
	{
		this.content = content;
		this.content_amount = content_amount;
		this.barrel_volume = Double.parseDouble(barrel_volume);
		this.manufacturing_date = LocalDate.parse(manufacturing_date);
		this.birthdate_of_barrel = LocalDate.parse(birthdate_of_barrel);
		this.death_of_barrel = LocalDate.parse(death_of_barrel);
		this.lastFill = LocalDate.parse(lastFill);
		this.position = position;
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
	public double getContent_amount() {
		return content_amount;
	}
	public void setContent_amount(double content_amount) {
		this.content_amount = content_amount;
	}
	public double getBarrel_volume() {
		return barrel_volume;
	}
	public void setBarrel_volume(double barrel_volume) {
		this.barrel_volume = barrel_volume;
	}
	public LocalDate getManufacturing_date() {
		return manufacturing_date;
	}
	public void setManufacturing_date(LocalDate manufacturing_date) {
		this.manufacturing_date = manufacturing_date;
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
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public int getAlter(){
		int datecount =0;
		int ANZAHL_TAGE_IM_JAHR = 365;
		int Alter ;
		while (getManufacturing_date().compareTo(LocalDate.now())<0){
			datecount++;
			setManufacturing_date(getManufacturing_date().plusDays(1));
		}
			setManufacturing_date(LocalDate.now().minusDays(datecount));
			Alter = datecount/ANZAHL_TAGE_IM_JAHR;			

		System.out.println(getContent() + getContent_amount()+ " Date count" + datecount + " alter" + Alter);
		System.out.println(getContent() + getBarrel_volume()+ " Date count" + datecount + " alter" + Alter);
		return Alter;
	}
	
}
