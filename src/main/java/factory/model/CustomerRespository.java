package factory.model;

import org.salespointframework.useraccount.UserAccount;
import org.springframework.data.repository.CrudRepository;


public interface CustomerRespository extends CrudRepository<Customer, Long> {

	Customer findByUserAccount(UserAccount userAccount);
}
