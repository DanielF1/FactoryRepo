package factory.repository;

import org.springframework.data.repository.CrudRepository;

import factory.model.ProductionManagement;
import factory.model.WineTransport;

public interface WineTransportRepository extends CrudRepository<WineTransport, Long>{

}
