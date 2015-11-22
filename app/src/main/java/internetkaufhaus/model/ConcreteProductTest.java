package internetkaufhaus.model;

import static org.junit.Assert.*;
import static org.salespointframework.core.Currencies.EURO;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;

public class ConcreteProductTest {
	
	private ConcreteProduct model;
	
	@Before
	public void init(){
		model = new ConcreteProduct("Zigarre", Money.of(1.99, EURO), "Tabakwaren", "Eine gute alte Zigarre", "www.tabak.ru", "zigarre.png");		
	}
	
	@Test
	public void testGetName() {
		assertTrue("Beschreibung erhalten", model.getName().equals("Zigarre"));
	}

}
