package internetkaufhaus.model;

import static org.junit.Assert.assertEquals;
import static org.salespointframework.core.Currencies.EURO;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import internetkaufhaus.Application;
import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.entities.ConcreteUserAccount;
import internetkaufhaus.repositories.ConcreteOrderRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)

public class CreditmanagerTest {
	private ConcreteUserAccount acc;
	private ConcreteUserAccount user;
	private Creditmanager manager;
	private ConcreteOrder o;
	private Order order;

	@Autowired
	ConcreteOrderRepository concreteOrderRepo;
	@Autowired
	OrderManager<Order> orderManager;

	@Autowired
	UserAccountManager u;

	@Before
	public void init() {
		this.manager = new Creditmanager(concreteOrderRepo);
		this.user = new ConcreteUserAccount("Username1", "Username1", Role.of("ROLE_CUSTOMER"), u);
		this.acc = new ConcreteUserAccount("test@mail.com", "Username2", "Firstname", "Lastname", "Adress", "ZipCode",
				"City", "Password", Role.of("ROLE_EMPLOYEE"), u);

		user.setRecruits(acc);

		// Cart cart= new Cart();
		// ConcreteUserAccount acc = new ConcreteUserAccount("Username1",
		// "Username1", Role.of("ROLE_CUSTOMER"), u);
		// ConcreteProduct prod1 =new ConcreteProduct("CIMAROSA Sauvignon Blanc
		// Marlborough Estate Selection, Weißwein 2014", Money.of(5.49, EURO),
		// "Wein", "„Nahezu mühelos produziert Neuseeland helle, aromatische
		// Sauvignons, die eine Geschmackstiefe erreichen, ohne dieselbe
		// Heftigkeit am Gaumen zu zeigen, die diese beliebte Rebsorte oft
		// charakterisiert. Frisch und nicht zu trocken ist dieser Wein leicht
		// zu trinken – alleine oder zu Salaten, Fisch und weißem Fleisch.\"
		// RICHARD BAMPFIELD.", "https://eng.wikipedia.org/wiki/Fuzz",
		// "cimarosa-sauvignon-blanc-marlborough-estate-selection-weisswein-2014.jpg");
		// ConcreteProduct prod2=new ConcreteProduct("Allini Pinot Chardonnay,
		// Schaumwein", Money.of(2.49, EURO), "Wein", "Mit diesem prickelnd
		// sanften Spumante, von Pinot & Chardonnay steht dem endlosen
		// Trinkgenuss nichts mehr im Wege. Zart strohgelb im Glas, mit einer
		// Aromatik von Marille und weißer Mandelblüte, einer belebenden Säure
		// und einem spritzig fruchtigen Mundgefühl, schmeckt dieser prickelnde
		// Gefährte hervorragend gut gekühlt bei 8-10°C als Aperitif oder zu
		// knackigen Salaten.", "https://eng.wikipedia.org/wiki/Fuzz",
		// "allini-pinot-chardonnay-schaumwein--1.jpg");
		// cart.addOrUpdateItem(prod1, Quantity.of(10));
		// cart.addOrUpdateItem(prod2, Quantity.of(5));
		// this.o = new ConcreteOrder(acc.getUserAccount(), Cash.CASH);
		// this.order=this.o.getOrder();
		// cart.addItemsTo(order);
		//
		// orderManager.payOrder(order);// only set orderManager.payOrder(o), do
		// not use orderManager.completeOrder(0), to complete Order look at the
		// next line!
		//
		// this.o.setStatus(OrderStatus.COMPLETED); // to complete Order do not
		// use orderManager.completeOrder
		// this.o.setDateOrdered(LocalDateTime.now().minusDays(31));
		// concreteOrderRepo.save(this.o);
		// orderManager.save(order);

	}

	@Test
	public void testupdateCreditpointsByUser() {
		// assertTrue("0 pkte", acc.getCredits() == 0);
		Money credits = Money.of(14, EURO);
		acc.setCredits(credits);
		manager.updateCreditpointsByUser(acc);
		assertEquals("UpdatePoints", acc.getCredits(), credits);
	}
}