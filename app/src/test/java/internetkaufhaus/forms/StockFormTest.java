package internetkaufhaus.forms;

import static org.junit.Assert.assertTrue;
import static org.salespointframework.core.Currencies.EURO;

import org.javamoney.moneta.Money;
import org.junit.Test;

import internetkaufhaus.entities.ConcreteProduct;

// TODO: Auto-generated Javadoc
/**
 * The Class StockFormTest.
 */
public class StockFormTest {

	/** The model. */
	private StockForm model = new StockForm();

	/**
	 * Quantity test.
	 */
	@Test
	public void quantityTest() {
		model.setQuantity(10);
		assertTrue("Menge erhalten", model.getQuantity() == 10);
	}

	/**
	 * Prod id test.
	 */
	@Test
	public void prodIdTest() {
		ConcreteProduct prod = new ConcreteProduct("TestArtikel", Money.of(50, EURO), Money.of(28, EURO),
				"Testkategorie", "Testbeschreibung", "Testweblink", "Testimagefile");
		model.setProdId(prod.getIdentifier());
		assertTrue("Produkt ID erhalten", model.getProdId().equals(prod.getIdentifier()));
	}

}