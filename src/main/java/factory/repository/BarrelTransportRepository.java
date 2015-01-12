package factory.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import factory.model.BarrelTransport;

@Repository
public interface BarrelTransportRepository extends CrudRepository<BarrelTransport, Long>{

}
