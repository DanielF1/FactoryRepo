package videoshop.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Barrel {

	private @Id @GeneratedValue Long id;
	private String content;
	private double amount;
//	private int quantity;
	private BarrelType type;
//	private LocalDate startdate;
//	private LocalDate lastFill;
//	private LocalDate ablaufdate;
	
	
	public static int barrelCount = 0;
	
	public static enum BarrelType 
	{
		EMPTY, FULL;
	}
	
	public Barrel(String content, double amount, BarrelType type/*, LocalDate startdate,
			LocalDate lastFill, LocalDate ablaufdate*/)
	{
		this.setContent(content);
		this.amount = amount;
		this.type = type;
//		this.startdate = startdate;
//		this.lastFill = lastFill;
//		this.ablaufdate = ablaufdate;
	}
	
//	public Barrel(String startdate, BarrelType barrelType, double amount,
//			String lastFill, String ablaufdate) {
//		
//		startdate=LocalDate.parse(startdate);
//		this.content = barrelType;
//		this.amount = amount;
//		this.lastFill = LocalDate.parse(lastFill);
//		this.ablaufdate = LocalDate.parse(ablaufdate);
//		barrelCount++;
//	}
	
	Barrel(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

//	public int getQuantity() {
//		return quantity;
//	}
//
//	public void setQuantity(int quantity) {
//		this.quantity = quantity;
//	}

	public BarrelType getType() {
		return type;
	}

	public void setType(BarrelType type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

//	public LocalDate getStartdate() {
//		return startdate;
//	}
//
//	public void setStartdate(LocalDate startdate) {
//		this.startdate = startdate;
//	}
//
//	public LocalDate getLastFill() {
//		return lastFill;
//	}
//
//	public void setLastFill(LocalDate lastFill) {
//		this.lastFill = lastFill;
//	}
//
//	public LocalDate getAblaufdate() {
//		return ablaufdate;
//	}
//
//	public void setAblaufdate(LocalDate ablaufdate) {
//		this.ablaufdate = ablaufdate;
//	}
//
//	public static int getBarrelCount() {
//		return barrelCount;
//	}
//
//	public static void setBarrelCount(int barrelCount) {
//		Barrel.barrelCount = barrelCount;
//	}
//
//	public void setContent(BarrelContentType content) {
//		Content = content;
//	}
}
