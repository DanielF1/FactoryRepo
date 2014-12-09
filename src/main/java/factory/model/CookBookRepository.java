package factory.model;

import org.springframework.data.repository.Repository;

public interface CookBookRepository extends Repository<Recipe, Long>{

	Recipe save(Recipe recipe);
	
	Iterable<Recipe> findById(Long id);
	
	Iterable<Recipe> findAll();

	void delete(Long id);

}
