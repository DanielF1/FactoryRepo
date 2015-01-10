package factory.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Barrel {

	private @Id @GeneratedValue Long id;
	private int age;
	private String quality;
	private double content_amount;
	private LocalDate manufacturing_date;
	private double barrel_volume;	
	private LocalDate birthdate_of_barrel;
	private LocalDate death_of_barrel;
	private LocalDate lastFill;
	private String position;
	
	/*
	 * Konstruktor
	 */
	public Barrel(int age,String quality, double content_amount,LocalDate manufacturing_date, double barrel_volume, 
			LocalDate birthdate_of_barrel,LocalDate death_of_barrel, LocalDate lastFill,String position)
	{
		this.age = age;
		this.quality = quality;
		this.content_amount = content_amount;
		this.barrel_volume = barrel_volume;
		this.manufacturing_date = manufacturing_date;
		this.birthdate_of_barrel = birthdate_of_barrel;
		this.death_of_barrel = death_of_barrel;
		this.lastFill = lastFill;
		this.position = position;
		

	}
	
	/*
	 * Konstruktor
	 */
	public Barrel(int age,String quality, double content_amount,String manufacturing_date, String barrel_volume, 
			String birthdate_of_barrel,String death_of_barrel, String lastFill,String position)
	{
		this.age = age;
		this.quality = quality;
		this.content_amount = content_amount;
		this.barrel_volume = Double.parseDouble(barrel_volume);
		this.manufacturing_date = LocalDate.parse(manufacturing_date);
		this.birthdate_of_barrel = LocalDate.parse(birthdate_of_barrel);
		this.death_of_barrel = LocalDate.parse(death_of_barrel);
		this.lastFill = LocalDate.parse(lastFill);
		this.position = position;
	}
	
	

	Barrel(){}

	/*
	 * Getter und Setter
	 */

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
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
	
	/*
	 * 
	 */
	public int getAge(){
		int datecount =0;
		int ANZAHL_TAGE_IM_JAHR = 365;
		int age ;
		while (getManufacturing_date().compareTo(LocalDate.now())<0){
			datecount++;
			setManufacturing_date(getManufacturing_date().plusDays(1));
		}
			setManufacturing_date(LocalDate.now().minusDays(datecount));
			age = datecount/ANZAHL_TAGE_IM_JAHR;			
			if (getQuality().equals("") || getContent_amount()==0)
				{
					age = 0;
				}
		System.out.println(getContent_amount()+ " Date count" + datecount + " alter" + age);
		System.out.println(getBarrel_volume()+ " Date count" + datecount + " alter" + age);
		return age;
	}
}
