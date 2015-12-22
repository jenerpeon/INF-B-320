package internetkaufhaus.model;

import static org.junit.Assert.assertEquals;
import static org.salespointframework.core.Currencies.EURO;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import internetkaufhaus.Application;
import internetkaufhaus.entities.ConcreteUserAccount;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)

public class CreditComparatorTest {
	private ConcreteUserAccount model1;
	private ConcreteUserAccount model2;

	@Autowired
	CreditComparator comparator;
	@Autowired
	UserAccountManager u;

	@Before
	public void init() {
		model1 = new ConcreteUserAccount("Username1", "Username1", Role.of("ROLE_CUSTOMER"), u);
		model1.setCredits(Money.of(6, EURO));
		model2 = new ConcreteUserAccount("test@mail.com", "Username2", "Firstname", "Lastname", "Adress", "ZipCode",
				"City", "Password", Role.of("ROLE_EMPLOYEE"), u);
		model2.setCredits(Money.of(3, EURO));
	}

	@Test
	public void testCompare() {
		assertEquals("compare", comparator.compare(model1, model2), 6);
	}

}
