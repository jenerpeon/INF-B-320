package internetkaufhaus.entities;

import static org.junit.Assert.assertTrue;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.salespointframework.useraccount.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import internetkaufhaus.Application;
import internetkaufhaus.services.DataService;

// TODO: Auto-generated Javadoc
/**
 * The Class ConcreteUserAccountTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)

public class ConcreteUserAccountTest {

	/** The model1. */
	private ConcreteUserAccount model1;

	/** The model2. */
	private ConcreteUserAccount model2;

	/** The u. */
	@Autowired
	DataService data;

	/**
	 * Inits the.
	 */
	@Before
	public void init() {
		List<ConcreteUserAccount> recruits = new ArrayList<ConcreteUserAccount>();

		model1 = new ConcreteUserAccount("Username1", "Username1", Role.of("ROLE_CUSTOMER"),
				data.getUserAccountManager());
		model2 = new ConcreteUserAccount("test@mail.com", "Username2", "Firstname", "Lastname", "Adress", "ZipCode",
				"City", "Password", Role.of("ROLE_EMPLOYEE"), data.getUserAccountManager(), 2, recruits);
		recruits.add(model1);

	}

	/**
	 * Test getter setter.
	 */
	@Test
	public void testGetterSetter() {
		Comment com = new Comment("Das hier ist ein Titel", "Das hier ist ein Kommentar", 4, LocalDateTime.now(), "12");
		model1.addComment(com);
		assertTrue("Com hinzugefügt", model1.getComments().size() == 1);
		assertTrue("Com get", model1.getComments().contains(com));

		Long id = 3L;
		model1.setId(id);
		assertTrue("id gesetzt", model1.getId() == id);

		assertTrue("Zipcode get", model2.getZipCode().equals("ZipCode"));
		model2.setZipCode("New");
		assertTrue("Zipcode geändert", model2.getZipCode().equals("New"));

		assertTrue("Role get", model2.getRole().equals(Role.of("ROLE_EMPLOYEE")));
		model2.setRole(Role.of("ROLE_CUSTOMER"));

		assertTrue("Email get", model2.getEmail().equals("test@mail.com"));
		model1.setEmail("test@mail.com");
		assertTrue("Email geändert", model1.getEmail().equals("test@mail.com"));

		assertTrue("Adress get", model2.getAddress().equals("Adress"));
		model1.setAddress("Adress1");
		assertTrue("Adresse geänder", model1.getAddress().equals("Adress1"));

		assertTrue("Credit get", model2.getCredits() == 2);
		model2.setCredits(4);
		assertTrue("Credit geändert", model2.getCredits() == 4);

		assertTrue("Recruits get", model2.getRecruits().get(0) == model1);

	}
}