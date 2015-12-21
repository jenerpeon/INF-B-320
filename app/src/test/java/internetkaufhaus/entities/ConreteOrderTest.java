package internetkaufhaus.entities;

import static org.junit.Assert.assertTrue;
import static org.salespointframework.core.Currencies.EURO;

import org.apache.commons.collections.IteratorUtils;
import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.salespointframework.order.Cart;
import org.salespointframework.order.Order;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import internetkaufhaus.Application;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)

public class ConreteOrderTest{
	
	private ConcreteOrder o;
	
	
	
	@Autowired
	UserAccountManager u;
	
	@Before
	public void init(){
		Cart cart= new Cart();
		ConcreteUserAccount acc = new ConcreteUserAccount("Username1", "Username1", Role.of("ROLE_CUSTOMER"), u);
		ConcreteProduct prod1 =new ConcreteProduct("CIMAROSA Sauvignon Blanc Marlborough Estate Selection, Weißwein 2014", Money.of(5.49, EURO), "Wein", "„Nahezu mühelos produziert Neuseeland helle, aromatische Sauvignons, die eine Geschmackstiefe erreichen, ohne dieselbe Heftigkeit am Gaumen zu zeigen, die diese beliebte Rebsorte oft charakterisiert. Frisch und nicht zu trocken ist dieser Wein leicht zu trinken – alleine oder zu Salaten, Fisch und weißem Fleisch.\" RICHARD BAMPFIELD.", "https://eng.wikipedia.org/wiki/Fuzz", "cimarosa-sauvignon-blanc-marlborough-estate-selection-weisswein-2014.jpg");
		ConcreteProduct prod2=new ConcreteProduct("Allini Pinot Chardonnay, Schaumwein", Money.of(2.49, EURO), "Wein", "Mit diesem prickelnd sanften Spumante, von Pinot & Chardonnay steht dem endlosen Trinkgenuss nichts mehr im Wege. Zart strohgelb im Glas, mit einer Aromatik von Marille und weißer Mandelblüte, einer belebenden Säure und einem spritzig fruchtigen Mundgefühl, schmeckt dieser prickelnde Gefährte hervorragend gut gekühlt bei 8-10°C als Aperitif oder zu knackigen Salaten.", "https://eng.wikipedia.org/wiki/Fuzz", "allini-pinot-chardonnay-schaumwein--1.jpg");
		cart.addOrUpdateItem(prod1, Quantity.of(10));
		cart.addOrUpdateItem(prod2, Quantity.of(5));
		this.o = new ConcreteOrder(acc.getUserAccount(), Cash.CASH);
		cart.addItemsTo(o.getOrder());
		o.getOrderLinesSize();
		
	}
	
	@Test
	public void testgetOrderLinesSize(){
		assertTrue("Anzahl der Orderline", o.getOrderLinesSize()==2);
	}
	
	@Test
	public void testgetTotalProductNumber(){
		assertTrue("Anzahl der Produkte", o.getTotalProductNumber()==15);
	}
	
	@Test
	public void testBilling(){
		o.setBillingGender("Gender");
		assertTrue("Geschlechte gesetzt", o.getBillingGender().equals("Gender"));
		
		o.setBillingFirstName("firstname");
		assertTrue("Vorname gesetzt", o.getBillingFirstName().equals("firstname"));
		
		o.setBillingLastName("lastname");
		assertTrue("Nachname gesetzt", o.getBillingLastName().equals("lastname"));
		
		o.setBillingStreet("street");
		assertTrue("Straße gesetzt", o.getBillingStreet().equals("street"));
		
		o.setBillingHouseNumber("1");
		assertTrue("Nummer gesetzt", o.getBillingHouseNumber().equals("1"));
		
		o.setBillingAdressLine2("adress");
		assertTrue("Adresszusatz gesetzt", o.getBillingAdressLine2().equals("adress"));
		
		o.setBillingZipCode("123");
		assertTrue("PLZ gesetzt", o.getBillingZipCode().equals("123"));
		
		o.setBillingTown("town");
		assertTrue("Stadt gesetzt", o.getBillingTown().equals("town"));
	}
	
	@Test
	public void testShipping(){
		o.setShippingGender("Gender");
		assertTrue("Geschlechte gesetzt", o.getShippingGender().equals("Gender"));
		
		o.setShippingFirstName("firstname");
		assertTrue("Vorname gesetzt", o.getShippingFirstName().equals("firstname"));
		
		o.setShippingLastName("lastname");
		assertTrue("Nachname gesetzt", o.getShippingLastName().equals("lastname"));
		
		o.setShippingStreet("street");
		assertTrue("Straße gesetzt", o.getShippingStreet().equals("street"));
		
		o.setShippingHouseNumber("1");
		assertTrue("Nummer gesetzt", o.getShippingHouseNumber().equals("1"));
		
		o.setShippingAdressLine2("adress");
		assertTrue("Adresszusatz gesetzt", o.getShippingAdressLine2().equals("adress"));
		
		o.setShippingZipCode("123");
		assertTrue("PLZ gesetzt", o.getShippingZipCode().equals("123"));
		
		o.setShippingTown("town");
		assertTrue("Stadt gesetzt", o.getShippingTown().equals("town"));
	}
}