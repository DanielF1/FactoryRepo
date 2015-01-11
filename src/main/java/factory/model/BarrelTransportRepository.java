package factory.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarrelTransportRepository extends CrudRepository<BarrelTransport, Long>{

}
