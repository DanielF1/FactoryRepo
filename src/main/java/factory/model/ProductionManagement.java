package factory.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class ProductionManagement {

	@Id @GeneratedValue private Long id;
	private String location_name;
	private String location_address;
	private String location_city;
	private String location_telefon;
	private String location_mail;
	private int year;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<ProductionMonth> production_month;
	
	/**
	 * Constructor
	 * 
	 * @param location_name Name des Standortes
	 * @param location_address Adresse des Standortes
	 * @param location_city Ort/Stadt des Standortes
	 * @param location_telefon Telefonnummer des Standortes
	 * @param location_mail E-Mail Adresse des Standortes
	 * @param production_month Produktionsmonat des Standortes
	 * @param year Jahr des Standortes
	 */
	public ProductionManagement(String location_name, String location_address,
			String location_city, String location_telefon,
			String location_mail, List<ProductionMonth> production_month, int year) {
		super();
		this.location_name = location_name;
		this.location_address = location_address;
		this.location_city = location_city;
		this.location_telefon = location_telefon;
		this.location_mail = location_mail;
		this.production_month = production_month;
		this.year = year;
	}

	public ProductionManagement(){}
	
	/**
	 * getter
	 * @return location_name
	 */
	public String getLocation_name() {
		return location_name;
	}
	
	/**
	 * setter
	 * @param location_name
	 */
	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}

	/**
	 * getter
	 * @return location_address
	 */
	public String getLocation_address() {
		return location_address;
	}

	/**
	 * setter
	 * @param location_address
	 */
	public void setLocation_address(String location_address) {
		this.location_address = location_address;
	}

	/**
	 * getter 
	 * @return location_city
	 */
	public String getLocation_city() {
		return location_city;
	}

	/**
	 * setter
	 * @param location_city
	 */
	public void setLocation_city(String location_city) {
		this.location_city = location_city;
	}

	/**
	 * getter
	 * @return location_telefon
	 */
	public String getLocation_telefon() {
		return location_telefon;
	}

	/**
	 * setter
	 * @param location_telefon
	 */
	public void setLocation_telefon(String location_telefon) {
		this.location_telefon = location_telefon;
	}

	/**
	 * getter 
	 * @return location_mail
	 */
	public String getLocation_mail() {
		return location_mail;
	}

	/**
	 * setter
	 * @param location_mail
	 */
	public void setLocation_mail(String location_mail) {
		this.location_mail = location_mail;
	}

	/**
	 * getter
	 * @return production_month
	 */
	public List<ProductionMonth> getProduction_month() {
		return production_month;
	}

	/**
	 * setter
	 * @param production_month
	 */
	public void setProduction_month(List<ProductionMonth> production_month) {
		this.production_month = production_month;
	}

	/**
	 * getter
	 * @return year
	 */
	public int getYear() {
		return year;
	}

	/** 
	 * setter
	 * @param year
	 */
	public void setYear(int year) {
		this.year = year;
	}
}
