package internetkaufhaus.model;

import static org.junit.Assert.assertEquals;
import static org.salespointframework.core.Currencies.EURO;

import java.util.ArrayList;
import java.util.List;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;

import internetkaufhaus.entities.ConcreteProduct;

// TODO: Auto-generated Javadoc
/**
 * The Class StartPageTest.
 */
public class StartPageTest {
	
	/** The start page. */
	private StartPage startPage;

	/**
	 * Inits the.
	 */
	@Before
	public void init() {
		this.startPage = new StartPage();
		this.startPage.setBannerProducts(new ArrayList<ConcreteProduct>());
		this.startPage.setSelectionProducts(new ArrayList<ConcreteProduct>());
	}

	/**
	 * Test.
	 */
	@Test
	public void test() {
		List<ConcreteProduct> list1 = new ArrayList<ConcreteProduct>();
		list1.add(new ConcreteProduct("Name", Money.of(5, EURO), Money.of(4, EURO), "Grillen", "Beschreeeibung",
				"WAPlink", "IMAETSCHFEIL"));
		this.startPage.setBannerProducts(list1);
		assertEquals("setBannerProducts", this.startPage.getBannerProducts(), list1);
		
		ConcreteProduct prod1 = new ConcreteProduct("NAAME", Money.of(20, EURO), Money.of(10, EURO),
				"KATEGORIE", "BESCHREIBUNG", "NETZVERKNUEPFUNG", "BILDDATEI");
		this.startPage.addBannerProduct(prod1);
		list1.add(prod1);
		assertEquals("addBannerProduct", this.startPage.getBannerProducts(), list1);
		
		this.startPage.setSelectionProducts(list1);
		assertEquals("setSelectionProducts", this.startPage.getSelectionProducts(), list1);
		
		this.startPage.addSelectionProduct(prod1);
		assertEquals("addSelectionProduct", this.startPage.getSelectionProducts(), list1);
	}

}
