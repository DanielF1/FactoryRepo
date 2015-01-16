package factory.model.validation;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;


public class DeliveryForm {

	@NotNull
	private int wine_delivery_amount; 
	@NotNull
	private int wine_delivery_year;
	@NotNull
	private int wine_delivery_month;
	@NotNull
	private int wine_delivery_day; 
	@NotEmpty
	private String location_goal;
	
	
	public int getWine_delivery_amount() {
		return wine_delivery_amount;
	}
	public void setWine_delivery_amount(int wine_delivery_amount) {
		this.wine_delivery_amount = wine_delivery_amount;
	}
	public int getWine_delivery_year() {
		return wine_delivery_year;
	}
	public void setWine_delivery_year(int wine_delivery_year) {
		this.wine_delivery_year = wine_delivery_year;
	}
	public int getWine_delivery_month() {
		return wine_delivery_month;
	}
	public void setWine_delivery_month(int wine_delivery_month) {
		this.wine_delivery_month = wine_delivery_month;
	}
	public int getWine_delivery_day() {
		return wine_delivery_day;
	}
	public void setWine_delivery_day(int wine_delivery_day) {
		this.wine_delivery_day = wine_delivery_day;
	}
	public String getLocation_goal() {
		return location_goal;
	}
	public void setLocation_goal(String location_goal) {
		this.location_goal = location_goal;
	}
	
	
}
