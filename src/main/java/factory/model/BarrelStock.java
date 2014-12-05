package factory.model;

import org.springframework.data.repository.CrudRepository;

public interface BarrelStock extends CrudRepository<Barrel, Long>{

//	Barrel save(Barrel barrel);
//	Iterable<Barrel> findById(Long id);
}
