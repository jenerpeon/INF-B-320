package internetkaufhaus.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import internetkaufhaus.Application;
import internetkaufhaus.entities.ConcreteUserAccount;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)

public class AccountingServiceTest {

	private AccountingService service;

	@Before
	public void initTest() {
		service = new AccountingService();
	}

	/*@Test
	public void testAddUser() {
		assertFalse("testAddUser", service.addUser(new ConcreteUserAccount()));
	}*/

	// TODO: fix this
	/*
	 * @Test public void testUpdateUser() { assertTrue("testUpdateUser",
	 * service.updateUser()); }
	 */
	/*
	@Test
	public void testDeleteUser() {
		assertFalse("testDeleteUser", service.deleteUser(0L));
	}*/

	// TODO: fix this
	/*
	 * @Test public void testRegisterNew() { // TODO: This fails always because
	 * dataService can't be loaded. We need // to fix that first.
	 * assertFalse("testRegisterNew", service.registerNew(new
	 * RegistrationForm())); }
	 */
	/*
	@Test
	public void testRegisterCustomer() {
		assertFalse("testRegisterCustomer", service.registerCustomer("peon@peon.cave"));
	}*/

	@Test
	public void testRecruitCustomer() {
		assertEquals("testRecruitCustomer", service.recruitCustomer(null, "peon@peon.cave"), "invalid invitator");
	}
}
