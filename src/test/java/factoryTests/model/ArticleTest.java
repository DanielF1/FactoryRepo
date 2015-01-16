package factoryTests.model;

import static org.joda.money.CurrencyUnit.EUR;
import static org.junit.Assert.assertEquals;

import org.joda.money.Money;
import org.junit.Test;

import factory.model.Article;
import factoryTests.AbstractIntegrationTests;

public class ArticleTest extends AbstractIntegrationTests{

	@Test
	public void testArticle() {
		
		Article article = new Article("chatelier", "Claude Chatelier Extra", "20 Jahre", Money.of(EUR, 46.95), "40,0 %",1, "Cognac");
		assertEquals("chatelier",article.getImage());
		assertEquals("20 Jahre",article.getAlter());
		assertEquals("40,0 %",article.getAlkoholgehalt());
		assertEquals(1,article.getVolumen(),0.0);
		assertEquals("Cognac",article.getCognacart());
	}
	
}
