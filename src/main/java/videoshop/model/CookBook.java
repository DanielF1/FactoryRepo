package videoshop.model;

import org.springframework.data.repository.Repository;


public interface CookBook extends Repository<Recipe, Long>{

	Recipe save(Recipe recipe);
	
	Iterable<Recipe> findById(Long id);
	
	Iterable<Recipe> findAll();
	
//	Iterable<Recipe> findByName(String name);
	
//	int count();

	void delete(Long id);

}
