package factory.repository;

import org.salespointframework.catalog.Catalog;
import org.springframework.stereotype.Repository;

import factory.model.Article;

@Repository("babab")
public interface Sortiment extends Catalog<Article> {
}


