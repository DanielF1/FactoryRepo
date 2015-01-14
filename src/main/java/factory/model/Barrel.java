package factory.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Barrel ist der Aufbewahrungsort eines Destillates
 */
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
	
	/**
	 * Default Constructor
	 */
	@Deprecated
	public Barrel(){}
	
	/**
	 * Constructor
	 * 
	 * @param age Alter des Barrels
	 * @param quality Qualität des Barrels
	 * @param content_amount Inhaltsangabe des Barrels
	 * @param manufacturing_date Herstellungsdatum des Barrels
	 * @param barrel_volume Volumen des Barrels
	 * @param birthdate_of_barrel Herstellungsdatum des Barrels
	 * @param death_of_barrel Ablaufdatum des Barrels
	 * @param lastFill letzte Füllung des Barrels
	 * @param position Position des Barrels
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
	
	/**
	 * Constructor
	 */
	/**
	 * 
	 * @param age Alter des Barrels
	 * @param quality Qualität des Barrels
	 * @param content_amount Inhaltsangabe des Barrels
	 * @param manufacturing_date Herstellungsdatum des Barrels
	 * @param barrel_volume Volumen des Barrels
	 * @param birthdate_of_barrel Herstellungsdatum des Barrels
	 * @param death_of_barrel Ablaufdatum des Barrels
	 * @param lastFill letzte Füllung des Barrels
	 * @param position Position des Barrels
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
	
	/**
	 * getter
	 * @return id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * setter
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * setter
	 * @param age
	 */
	public void setAge(int age) {
		this.age = age;
	}
	
	/**
	 * getter
	 * @return quality
	 */
	public String getQuality() {
		return quality;
	}
	
	/**
	 * setter
	 * @param quality
	 */
	public void setQuality(String quality) {
		this.quality = quality;
	}
	
	/**
	 * getter
	 * @return content_amount
	 */
	public double getContent_amount() {
		return content_amount;
	}
	
	/**
	 * setter
	 * @param content_amount
	 */
	public void setContent_amount(double content_amount) {
		this.content_amount = content_amount;
	}
	
	/**
	 * getter
	 * @return
	 */
	public double getBarrel_volume() {
		return barrel_volume;
	}
	
	/**
	 * setter
	 * @param barrel_volume
	 */
	public void setBarrel_volume(double barrel_volume) {
		this.barrel_volume = barrel_volume;
	}
	
	/**
	 * getter
	 * @return manufacturing_date
	 */
	public LocalDate getManufacturing_date() {
		return manufacturing_date;
	}
	
	/** 
	 * setter
	 * @param manufacturing_date
	 */
	public void setManufacturing_date(LocalDate manufacturing_date) {
		this.manufacturing_date = manufacturing_date;
	}
	
	/**
	 * getter
	 * @return birthdate_of_barrel
	 */
	public LocalDate getBirthdate_of_barrel() {
		return birthdate_of_barrel;
	}
	
	/**
	 * setter
	 * @param birthdate_of_barrel
	 */
	public void setBirthdate_of_barrel(LocalDate birthdate_of_barrel) {
		this.birthdate_of_barrel = birthdate_of_barrel;
	}
	
	/**
	 * getter
	 * @return death_of_barrel
	 */
	public LocalDate getDeath_of_barrel() {
		return death_of_barrel;
	}
	
	/**
	 * setter
	 * @param death_of_barrel
	 */
	public void setDeath_of_barrel(LocalDate death_of_barrel) {
		this.death_of_barrel = death_of_barrel;
	}
	
	/**
	 * getter
	 * @return lastFill
	 */
	public LocalDate getLastFill() {
		return lastFill;
	}
	
	/**
	 * setter
	 * @param lastFill
	 */
	public void setLastFill(LocalDate lastFill) {
		this.lastFill = lastFill;
	}
	
	/**
	 * getter
	 * @return position
	 */
	public String getPosition() {
		return position;
	}
	
	/**
	 * setter
	 * @param position
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	
	/*
	 Diese Funktion berechnet den Alter der Destille 
	 */
	/**
	 * @return age
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
		
		return age;
	}
}
