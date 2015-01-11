package factory.model;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;

public interface ExpenditureRepository extends CrudRepository<Expenditure, Long> {
	
	Expenditure findByDate(LocalDate date);
	Expenditure findBySortOf(String sort);

}
