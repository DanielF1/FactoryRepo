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
	
	/**
	 * Default Constructor
	 */
	Recipe(){}
	
	/**
	 * Constructor
	 * 
	 * @param name Name der Rezepts
	 * @param map1 Liste der Rezepte
	 */
	public Recipe(String name, List<Ingredient> map1){
		this.name = name;
		this.ingredients = map1;
	}

	/**
	 * getter
	 * @return name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * getter
	 * @return ingredients
	 */
	public List<Ingredient> getIngredients(){
		return ingredients;
	}
	
	/**
	 * setter
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * setter
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * setter
	 * @param ingredients
	 */
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	/**
	 * getter
	 * @return id
	 */
	public Long getId(){
		return id;
	}
}