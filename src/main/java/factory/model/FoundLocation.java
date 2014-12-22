package factory.model;

public class FoundLocation {

	private String location;
	private String quality;
	private int age;
	
	
	public FoundLocation(String location, String quality, int age) {
		super();
		this.location = location;
		this.quality = quality;
		this.age = age;
	}
	
	public FoundLocation(){}
	
	/*
	 * getter & setter
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
	
	
}
