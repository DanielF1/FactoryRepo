package factory.repository;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import factory.model.Income;

@Repository
public interface IncomeRepository extends CrudRepository<Income, Long> {
	
	Income findByDate(LocalDate date);
	Income findBySortOf(String sort);

}
