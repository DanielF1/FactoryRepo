package factory.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Recipe {

	@Id @GeneratedValue
	private Long id;
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Ingredient> ingredients = new ArrayList<Ingredient>();
	
	/*
	 * Konstruktor
	 */
	public Recipe(String name, List<Ingredient> map1){
		this.name = name;
		this.ingredients = map1;
	}

	/* 
	 * default Konstruktor
	 */
	Recipe(){}
	
	/*
	 * Getter und Setter
	 */
	public String getName(){
		return name;
	}
	
	public List<Ingredient> getIngredients(){
		return ingredients;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public Long getId(){
		return id;
	}
}
