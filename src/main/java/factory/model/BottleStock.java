package factory.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class BottleStock {

	private @Id @GeneratedValue Long id;
	private String name;
	private int quantity_empty;
	private int quantity_full;

	@OneToMany(cascade = CascadeType.ALL)
	private static List<Bottle> emptybottles = new ArrayList<Bottle>();
	
	@OneToMany(cascade = CascadeType.ALL)
	private static  List<Bottle> fullbottles = new ArrayList<Bottle>();
	
	public BottleStock(String name, List<Bottle> emptybottles, List<Bottle> fullbottles)
	{
		this.name = name;
		this.emptybottles = emptybottles;
		this.fullbottles = fullbottles;
	}



	BottleStock(){}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static List<Bottle> getEmptybottles() {
		return emptybottles;
	}
	
	public void setEmptybottles(List<Bottle> emptybottles) {
		this.emptybottles = emptybottles;
	}

	public static List<Bottle> getFullbottles() {
		return fullbottles;
	}

	public void setFullbottles(List<Bottle> fullbottles) {
		this.fullbottles = fullbottles;
	}
	
	public int getQuantity_empty() {
		return emptybottles.size();
	}

	public void setQuantity_empty(int quantity_empty) {
		this.quantity_empty = quantity_empty;
	}

	public int getQuantity_full() {
		return fullbottles.size();
	}

	public void setQuantity_full(int quantity_full) {
		this.quantity_full = quantity_full;
	}



	public static void remove(Bottle bottle) {
		
		
	}
}
