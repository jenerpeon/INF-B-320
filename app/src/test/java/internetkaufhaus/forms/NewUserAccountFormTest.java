/*package internetkaufhaus.forms;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.salespointframework.useraccount.UserAccountManager;

import internetkaufhaus.repositories.ConcreteUserAccountRepository;

@RunWith(MockitoJUnitRunner.class)
public class NewUserAccountFormTest {
	
	@InjectMocks
	private NewUserAccountForm model = new NewUserAccountForm();
	
	@Mock
	private UserAccountManager manager;
	@Mock
	private ConcreteUserAccountRepository repo;
	
	private CreateUserForm createUserForm = new CreateUserForm();
	
	@Test
	public void createUserTest() {
		createUserForm.setName("Test");
		createUserForm.setPassword("12345678");
		createUserForm.setPasswordrepeat("12345678");
		createUserForm.setRolename("ADMIN");
		long id = model.createUser(createUserForm);
		assertTrue("User erstellt", repo.exists(id));
	}
}*/