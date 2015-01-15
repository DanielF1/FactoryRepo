package factory.model;

public class FoundLocation {

	private String location;
	private String quality;
	private int age;
	private double amount;
	
	/** 
	 * Default Constructor
	 */
	public FoundLocation(){}
	
	/**
	 * Constructor
	 * 
	 * @param location Standort der Foundlocation
	 * @param quality Qualität  der Foundlocation
	 * @param age Alter der Foundlocation
	 * @param amount Menge der Foundlocation
	 */
	public FoundLocation(String location, String quality, int age, double amount) {
		this.location = location;
		this.quality = quality;
		this.age = age;
		this.amount = amount;
	}
	

	/**
	 * getter
	 * @return location
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * setter
	 * @param location
	 */
	public void setLocation(String location) {
		this.location = location;
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
	 * @return age
	 */
	public int getAge() {
		return age;
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
	 * @return
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * setter
	 * @param amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
}
