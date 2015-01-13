package factory.repository;

import org.salespointframework.useraccount.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import factory.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

	Customer findByUserAccount(UserAccount userAccount);
	Customer findByUsername(String username);
}
