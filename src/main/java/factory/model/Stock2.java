package factory.model;

import org.springframework.data.repository.Repository;

public interface Stock2 extends Repository<BottleStock, Long> {

	BottleStock save(BottleStock bottlestock);
	
	Iterable<BottleStock> findAll();
}
