package videoshop.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface Locationmanagement extends CrudRepository<Location, Long> {
}
