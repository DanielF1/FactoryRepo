package factory.model;

import org.salespointframework.catalog.Catalog;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends Catalog<Article> {
	

}


