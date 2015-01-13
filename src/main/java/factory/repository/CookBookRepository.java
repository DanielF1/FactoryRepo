package factory.repository;

import org.springframework.data.repository.Repository;

import factory.model.Recipe;

public interface CookBookRepository extends Repository<Recipe, Long>{

	Recipe save(Recipe recipe);
	
	Iterable<Recipe> findById(Long id);
	
	Iterable<Recipe> findAll();

	void delete(Long id);

}
