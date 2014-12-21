package factory.model;

import org.salespointframework.useraccount.UserAccount;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

	Employee findByUserAccount(UserAccount userAccount);
}
