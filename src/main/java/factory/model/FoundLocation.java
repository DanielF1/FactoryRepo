package factory.model;

public class FoundLocation {

	private String location;
	private String quality;
	private int age;
	private double amount;
	
	/*
	 * Konstruktor
	 */
	public FoundLocation(String location, String quality, int age, double amount) {
		this.location = location;
		this.quality = quality;
		this.age = age;
		this.amount = amount;
	}
	
	/*
	 * leerer Konstruktor
	 */
	public FoundLocation(){}
	
	/*
	 * Getter und Setter
	 */
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
}
