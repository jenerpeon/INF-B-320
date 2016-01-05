package internetkaufhaus.forms;

import static org.junit.Assert.assertTrue;
import static org.salespointframework.core.Currencies.EURO;

import org.javamoney.moneta.Money;
import org.junit.Test;

import internetkaufhaus.entities.ConcreteProduct;

// TODO: Auto-generated Javadoc
/**
 * The Class EditArticleFormTest.
 */
public class EditArticleFormTest {

	/** The model. */
	private EditArticleForm model = new EditArticleForm();

	/**
	 * Prod id test.
	 */
	@Test
	public void prodIdTest() {
		ConcreteProduct prodId = new ConcreteProduct("TestArtikel", Money.of(50, EURO), Money.of(48, EURO),
				"Testkategorie", "Testbeschreibung", "Testweblink", "Testimagefile");
		model.setProdId(prodId);
		assertTrue("Produkt erhalten", model.getProdId().equals(prodId));
	}

	/**
	 * Name test.
	 */
	@Test
	public void nameTest() {
		model.setName("Quidditch Kiste");
		assertTrue("Name erhalten", model.getName().equals("Quidditch Kiste"));
	}

	/**
	 * Category test.
	 */
	@Test
	public void categoryTest() {
		model.setCategory("Kisten");
		assertTrue("Kategorie erhalten", model.getCategory().equals("Kisten"));
	}

	/**
	 * Price test.
	 */
	@Test
	public void priceTest() {
		model.setPrice((float) 50.0);
		assertTrue("Preis erhalten", model.getPrice() == (float) 50.0);
	}

	/**
	 * Description test.
	 */
	@Test
	public void descriptionTest() {
		model.setDescription("Eine Kiste. Mit Quidditch Sachen drin");
		assertTrue("Beschreibung erhalten", model.getDescription().equals("Eine Kiste. Mit Quidditch Sachen drin"));
	}

	/**
	 * Buying price test.
	 */
	@Test
	public void buyingPriceTest() {
		model.setBuyingPrice(48);
		assertTrue("Einkaufspreis erhalten", model.getBuyingPrice() == (float) 48);
	}

}