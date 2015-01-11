package factory.model;

import org.salespointframework.useraccount.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

	Employee findByUserAccount(UserAccount userAccount);
	Employee findByUsername(String username);
}
