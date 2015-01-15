package factory.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ProductionADay {

	@Id @GeneratedValue private Long id;
	LocalDate date ;
	String location_name;
	double wine_amount_for_production;
	double wine_transport_at_that_day_in;
	double wine_transport_at_that_day_out;
	double wine_delivery_at_that_day;

	/**
	 * Constructor
	 * 
	 * 
	 * @param date production date
	 * @param location_name name of location
	 * @param wine_amount_for_production amount that can be used for production
	 * @param wine_transport_at_that_day_in all wine that come at this day in
	 * @param wine_transport_at_that_day_out all wine that come at this day out
	 * @param wine_delivery_at_that_day all wine that have delivered at that day
	 */
	public ProductionADay(LocalDate date, String location_name,
			double wine_amount_for_production,
			double wine_transport_at_that_day_in,
			double wine_transport_at_that_day_out,
			double wine_delivery_at_that_day) {
		super();
		this.date = date;
		this.location_name = location_name;
		this.wine_amount_for_production = wine_amount_for_production;
		this.wine_transport_at_that_day_in = wine_transport_at_that_day_in;
		this.wine_transport_at_that_day_out = wine_transport_at_that_day_out;
		this.wine_delivery_at_that_day = wine_delivery_at_that_day;
	}

	
	/**
	 * default Constructor
	 */
	public ProductionADay(){}



	/**
	 * Getter and Setter
	 * 
	 * @return necessary value
	 */
	/**
	 * Getter
	 * @return date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Setter
	 * @param date
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * Getter
	 * @return location_name
	 */
	public String getLocation_name() {
		return location_name;
	}

	/**
	 * Setter
	 * @param location_name
	 */
	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}

	/**
	 * Getter
	 * @return wine_amount_for_production
	 */
	public double getWine_amount_for_production() {
		return wine_amount_for_production;
	}

	/**
	 * Setter
	 * @param wine_amount_for_production
	 */
	public void setWine_amount_for_production(double wine_amount_for_production) {
		this.wine_amount_for_production = wine_amount_for_production;
	}

	/**
	 * Getter 
	 * @return wine_transport_at_that_day_in
	 */
	public double getWine_transport_at_that_day_in() {
		return wine_transport_at_that_day_in;
	}

	/**
	 * Setter
	 * @param wine_transport_at_that_day_in
	 */
	public void setWine_transport_at_that_day_in(
			double wine_transport_at_that_day_in) {
		this.wine_transport_at_that_day_in = wine_transport_at_that_day_in;
	}

	/**
	 * Getter
	 * @return wine_transport_at_that_day_out
	 */
	public double getWine_transport_at_that_day_out() {
		return wine_transport_at_that_day_out;
	}

	/**
	 * Setter
	 * @param wine_transport_at_that_day_out
	 */
	public void setWine_transport_at_that_day_out(
			double wine_transport_at_that_day_out) {
		this.wine_transport_at_that_day_out = wine_transport_at_that_day_out;
	}

	/**
	 * Getter
	 * @return wine_delivery_at_that_day
	 */
	public double getWine_delivery_at_that_day() {
		return wine_delivery_at_that_day;
	}

	/**
	 * Setter
	 * 
	 * @param wine_delivery_at_that_day
	 */
	public void setWine_delivery_at_that_day(double wine_delivery_at_that_day) {
		this.wine_delivery_at_that_day = wine_delivery_at_that_day;
	}
}
