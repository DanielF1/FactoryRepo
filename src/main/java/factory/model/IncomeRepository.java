package factory.model;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepository extends CrudRepository<Income, Long> {
	
	Income findByDate(LocalDate date);
	Income findBySortOf(String sort);

}
