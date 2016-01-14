package internetkaufhaus.model;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import internetkaufhaus.AbstractIntegrationTests;
import internetkaufhaus.Application;
import internetkaufhaus.entities.ConcreteUserAccount;

// TODO: Auto-generated Javadoc
/**
 * The Class CreditComparatorTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)

@Transactional
public class CreditComparatorTest extends AbstractIntegrationTests{
	
	/** The acc1. */
	private ConcreteUserAccount acc1;
	
	/** The acc2. */
	private ConcreteUserAccount acc2;

	/** The u. */
	@Autowired
	UserAccountManager u;

	/**
	 * Inits the.
	 */
	@Before
	public void init() {
		acc1 = new ConcreteUserAccount("Username1", "Username1", Role.of("ROLE_CUSTOMER"), u);
		acc1.setCredits(6);
		acc2 = new ConcreteUserAccount("test@mail.com", "Username2", "Firstname", "Lastname", "Straße", "3", "ZipCode",
				"City", "Password", Role.of("ROLE_EMPLOYEE"), u);
		acc2.setCredits(3);
	}

	/**
	 * Test compare.
	 */
	@Test
	public void testCompare() {
		List<ConcreteUserAccount> list = new ArrayList<ConcreteUserAccount>();
		List<ConcreteUserAccount> sorted = new ArrayList<ConcreteUserAccount>();
		
		list.add(acc1);
		list.add(acc2);
		Collections.sort(list, new CreditComparator());
		
		sorted.add(acc2);
		sorted.add(acc1);
		
		assertTrue("compare", list.equals(sorted));
	}

}
