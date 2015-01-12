package factory.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import factory.model.Location;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {
	
	Location findByName(String name);
	
}
