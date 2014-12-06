package factory.model;

//<<<<<<< HEAD
import org.springframework.data.repository.CrudRepository;

public interface BarrelList extends CrudRepository<Barrel, Long>{

//	Barrel save(Barrel barrel);
//	Iterable<Barrel> findById(Long id);
//=======
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.OneToMany;
//
//@Entity
//public class BarrelStock {
//	
//	private @Id @GeneratedValue Long id;
//	
//	@OneToMany(cascade = CascadeType.ALL)
//	private  List<Barrel> barrels = new ArrayList<Barrel>();
//	
//	public BarrelStock(List<Barrel> mapBarrels){
//		this.barrels = mapBarrels;
//
//	}
//	
//	public BarrelStock(){}
//	
//	
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public List<Barrel> getBarrels() {
//		return barrels;
//	}
//
//	public void setBarrels(List<Barrel> barrels) {
//		this.barrels = barrels;
//	}
//	
//>>>>>>> origin/master
}
