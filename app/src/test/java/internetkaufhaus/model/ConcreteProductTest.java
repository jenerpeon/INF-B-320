package internetkaufhaus.model;

import static org.junit.Assert.*;
import static org.salespointframework.core.Currencies.EURO;

import java.util.Date;

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
	
	@Test
	public void testComment() {
		Date date = new Date(200000);
		Comment p = new Comment("das Produkt ist ekelerregend", 1, date, "t");
		model.addnewComments(p);
		assertTrue("Kommentar gesetzt", model.getNewComments().contains(p));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetNameError() throws Exception{
		model = new ConcreteProduct("", Money.of(1.99, EURO), "Tabakwaren", "Eine gute alte Zigarre", "www.tabak.ru", "zigarre.png");
	}

}
