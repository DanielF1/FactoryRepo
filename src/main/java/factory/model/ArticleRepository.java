package factory.model;

import org.salespointframework.catalog.Catalog;
import org.springframework.stereotype.Repository;

@Repository("babab")
public interface ArticleRepository extends Catalog<Article> {
	

}


