package factory;

import java.util.Date;



public class Deliver {

	private double amount;
	private Date date;
	private long locationId;
	
//	public Deliver(double amount, Date date, long locationId)
//	{
//		this.amount = amount;
//		this.date = date;
//		this.locationId = locationId;
//	}
//	
	public Deliver(double amount, long dateInMillis, Long locationId)
	{
		this.amount = amount;
		this.date = new Date(dateInMillis);
		this.locationId = locationId;
	
	}

	@Deprecated
	public Deliver(){}
	

	 // getter & setter
	 
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getLocation() {
		return locationId;
	}

	public void setLocation(Long locationId) {
		this.locationId = locationId;
	}	
}


