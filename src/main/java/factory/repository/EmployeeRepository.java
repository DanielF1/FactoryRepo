package factory.repository;

import org.salespointframework.useraccount.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import factory.model.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

	Employee findByUserAccount(UserAccount userAccount);
	Employee findByUsername(String username);
}
