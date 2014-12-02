package videoshop.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Location {
	
	@Id 
	@GeneratedValue
	private Long id;
	private String name;
	private String address;
	private String city;
	private String telefon;
	private String mail;
	//#TODO departments in Array umwandeln
	private String departments;
	

	public Location(String name, String address, String city, String telefon, String mail, String departments){
		this.name = name;
		this.address = address;
		this.city = city;
		this.telefon = telefon;
		this.mail = mail;
		this.departments = departments;	
	}
	
	Location(){}
	
	public String getName(){
		return name;
	}

	public String getAddress(){
		return address;
	}

	public String getCity(){
		return city;
	}
	
	public String getTelefon(){
		return telefon;
	}
	
	public String getMail(){
		return mail;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public void setCity(String city){
		this.city = city;
	}
	
	public void setTelefon(String telefon){
		this.telefon = telefon;
	}
	
	public void setMail(String mail){
		this.mail = mail;
	}
	
	public void setDepartments(String departments){
		this.departments = departments;
	}

	public String getDepartments() {
		return departments;
	}
	
	//Liste muss erst noch eingefügt werden
//	public List<Department> getDepartments() {
//		return departments;
//	}
	
	//prüft alle Locations die Produktionsmanagement haben und gibt sie aus
//	public WineDepartment getWineDepartment() {
//		Department wineDepartment = null;
//		for (Department department : departments) {
//			if (department.hasWine())
//				wineDepartment = department;
//		}
//		if (wineDepartment == null)
//			return null;
//		return (WineDepartment) wineDepartment;
//	}
	
//	public void addDepartment(Department department) {
//		departments.add(department);
//
//	}
//
//	@Override
//	public String toString() {
//		return "Location-ID: " + ID;
//	}
	
	
}