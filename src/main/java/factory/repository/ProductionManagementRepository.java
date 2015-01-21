package factory.repository;

import org.springframework.data.repository.CrudRepository;

import factory.model.ProductionManagement;

public interface ProductionManagementRepository extends CrudRepository<ProductionManagement, Long>{
}
