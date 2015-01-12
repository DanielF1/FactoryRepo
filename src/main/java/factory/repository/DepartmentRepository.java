package factory.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import factory.model.Department;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {

}
