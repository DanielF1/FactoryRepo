package factory.repository;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;

import factory.model.ProductionADay;
import factory.model.ProductionManagement;

public interface ProductionManagementRepository extends CrudRepository<ProductionManagement, Long>{
}
