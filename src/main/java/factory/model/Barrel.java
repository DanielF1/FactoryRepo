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
	 * @param age Alter des Fasses
	 * @param quality Qualität des Fasses
	 * @param content_amount Inhaltsangabe des Fasses
	 * @param manufacturing_date Herstellungsdatum des Fasses
	 * @param barrel_volume Volumen des Fasses
	 * @param birthdate_of_barrel Herstellungsdatum des Fasses
	 * @param death_of_barrel Ablaufdatum des Fasses
	 * @param lastFill letzte Füllung des Fasses
	 * @param position Position des Fasses
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
	 * 
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
	 * Getter and Setter
	 * 
	 * @return necessary value
	 */
	
	/**
	 * Getter 
	 * 
	 * @return id of barrel
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Setter
	 * 
	 * @param set id of barrel
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Setter
	 * 
	 * @param set age of barrel 
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * Getter
	 * 
	 * @return quality of content
	 */
	public String getQuality() {
		return quality;
	}

	/**
	 * Setter
	 * 
	 * @param quality set quality of content
	 */
	public void setQuality(String quality) {
		this.quality = quality;
	}

	/**
	 * Getter
	 * 
	 * @return content amount of barrel
	 */
	public double getContent_amount() {
		return content_amount;
	}

	/**
	 * Setter
	 * 
	 * @param set content amount of barrel
	 */
	public void setContent_amount(double content_amount) {
		this.content_amount = content_amount;
	}

	/**
	 * Getter
	 * 
	 * @return volume of barrel
	 */
	public double getBarrel_volume() {
		return barrel_volume;
	}

	/**
	 * Setter
	 * 
	 * @param  set volume of barrel
	 */
	public void setBarrel_volume(double barrel_volume) {
		this.barrel_volume = barrel_volume;
	}

	/**
	 * Getter
	 * 
	 * @return manufacturing date of distillate
	 */
	public LocalDate getManufacturing_date() {
		return manufacturing_date;
	}

	/**
	 * Setter
	 * 
	 * @param set manufacturing date of distillate
	 */
	public void setManufacturing_date(LocalDate manufacturing_date) {
		this.manufacturing_date = manufacturing_date;
	}

	/**
	 * Getter
	 * 
	 * @return birth of barrel
	 */
	public LocalDate getBirthdate_of_barrel() {
		return birthdate_of_barrel;
	}

	/**
	 * Setter
	 * 
	 * @param  set birth of barrel
	 */
	public void setBirthdate_of_barrel(LocalDate birthdate_of_barrel) {
		this.birthdate_of_barrel = birthdate_of_barrel;
	}
	
	/**
	 * Getter
	 * 
	 * @return death of barrel
	 */
	public LocalDate getDeath_of_barrel() {
		return death_of_barrel;
	}

	/**
	 * Setter
	 * 
	 * @param  set death of barrel
	 */
	public void setDeath_of_barrel(LocalDate death_of_barrel) {
		this.death_of_barrel = death_of_barrel;
	}

	/**
	 * Getter
	 * 
	 * @return last fill of barrel
	 */
	public LocalDate getLastFill() {
		return lastFill;
	}

	/**
	 * Setter
	 * 
	 * @param set last fill of barrel
	 */
	public void setLastFill(LocalDate lastFill) {
		this.lastFill = lastFill;
	}
	
	/**
	 * Getter
	 * 
	 * @return position position of barrel
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * Setter
	 * 
	 * @param set position position of barrel
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
