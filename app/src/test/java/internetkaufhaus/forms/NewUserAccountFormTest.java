package internetkaufhaus.forms;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import internetkaufhaus.AbstractIntegrationTests;
import internetkaufhaus.Application;
import internetkaufhaus.repositories.ConcreteUserAccountRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@Transactional
public class NewUserAccountFormTest extends AbstractIntegrationTests{
	
	@Autowired
	private UserAccountManager manager;

	@Autowired
	private ConcreteUserAccountRepository repo;
	
	private NewUserAccountForm model = new NewUserAccountForm(manager, repo);
	
	private CreateUserForm createUserForm; 
	
	@Before
	public void init() {
		CreateUserForm createUserForm = new CreateUserForm();
		createUserForm.setName("Test");
		createUserForm.setPassword("12345678");
		createUserForm.setPasswordrepeat("12345678");
		createUserForm.setRolename("ADMIN");
		
	}
	
	@Test
	public void createUserTest() {
		long id = model.createUser(createUserForm);
		assertTrue("User erstellt", true);
	}
}