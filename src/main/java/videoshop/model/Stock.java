package videoshop.model;


import org.springframework.data.repository.Repository;

public interface Stock extends Repository<BarrelStock, Long> {

	BarrelStock save(BarrelStock barrelstock);
	
	Iterable<BarrelStock> findAll();
	BarrelStock findOne(Long id);
}
