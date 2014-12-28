package factory.model;

import org.salespointframework.useraccount.UserAccount;
import org.springframework.data.repository.CrudRepository;


public interface CustomerRepository extends CrudRepository<Customer, Long> {

	Customer findByUserAccount(UserAccount userAccount);
	Customer findByUsername(String username);
}
