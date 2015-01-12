package factory.repository;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import factory.model.Expenditure;

@Repository
public interface ExpenditureRepository extends CrudRepository<Expenditure, Long> {
	
	Expenditure findByDate(LocalDate date);
	Expenditure findBySortOf(String sort);

}
