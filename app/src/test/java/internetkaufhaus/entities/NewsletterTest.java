package internetkaufhaus.entities;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.salespointframework.core.Currencies.EURO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.javamoney.moneta.Money;

public class NewsletterTest {

	Newsletter model;
	
	@Before
	public void init() {
		ConcreteProduct prod = new ConcreteProduct("Zigarre", Money.of(1.99, EURO), Money.of(1.49, EURO), "Tabakwaren",
				"Eine gute alte Zigarre", "www.tabak.ru", "zigarre.png");
		List<ConcreteProduct> selection = new ArrayList<ConcreteProduct>();
		selection.add(prod);
		model = new Newsletter("/ich/bin/ein/Pfad", "ich bin kein HTML", "ich bin kein HTML", selection, LocalDate.now());
	}
	
	@Test
	public void newsLetterIdenfifierTest() {
		NewsletterIdentifier id = new NewsletterIdentifier();
		model.setNewsletterIdentifier(id);
		assertEquals(id, model.getNewsletterIdentifier());
	}
	
	@Test
	public void htmlContentTest() {
		model.setHtmlPreviewContent("ich bin kein HTML");
		assertEquals("ich bin kein HTML", model.getHtmlContent());
	}
	
	@Test
	public void htmlPreviewContentTest() {
		model.setHtmlPreviewContent("ich bin kein HTML");
		assertEquals("ich bin kein HTML", model.getHtmlPreviewContent());
	}
	
	@Test
	public void idTest() {
		NewsletterIdentifier id = new NewsletterIdentifier();
		model.setId(id);
		assertEquals(id, model.getId());
	}
	
	@Test
	public void templateTest() {
		model.setTemplate("/ich/bin/ein/Pfad");
		assertEquals("/ich/bin/ein/Pfad", model.getTemplate());
	}
	
	@Test
	public void productSelectionTest() {
		ConcreteProduct prod = new ConcreteProduct("Zigarre", Money.of(1.99, EURO), Money.of(1.49, EURO), "Tabakwaren",
				"Eine gute alte Zigarre", "www.tabak.ru", "zigarre.png");
		List<ConcreteProduct> selection = new ArrayList<ConcreteProduct>();
		selection.add(prod);
		model.setProductSelection(selection);
		assertEquals(selection, model.getProductSelection());
	}
	
	@Test
	public void dateCreatedTest() {
		LocalDate now = LocalDate.now();
		model.setDateCreated(now);
		assertEquals(now, model.getDateCreated());
	}
	
}
