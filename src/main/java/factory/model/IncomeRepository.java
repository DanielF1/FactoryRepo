package factory.model;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;

public interface IncomeRepository extends CrudRepository<Income, Long> {
	
	Income findByDate(LocalDate date);
	Income findBySortOf(String sort);

}
