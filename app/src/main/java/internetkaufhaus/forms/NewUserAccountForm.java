package internetkaufhaus.forms;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import internetkaufhaus.entities.ConcreteUserAccount;
import internetkaufhaus.repositories.ConcreteUserAccountRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class NewUserAccountForm.
 */
@Component
public class NewUserAccountForm {

	/** The manager. */
	private UserAccountManager manager;
	
	/** The repo. */
	private ConcreteUserAccountRepository repo;

	/**
	 * Instantiates a new new user account form.
	 *
	 * @param manager the manager
	 * @param repo the repo
	 */
	@Autowired
	public NewUserAccountForm(UserAccountManager manager, ConcreteUserAccountRepository repo) {
		this.manager = manager;
		this.repo = repo;
	}

	/**
	 * Creates the user.
	 *
	 * @param createuserform the createuserform
	 */
	public void createUser(CreateUserForm createuserform) {
		ConcreteUserAccount cacc = new ConcreteUserAccount(createuserform.getName(),createuserform.getPassword(), Role.of(createuserform.getRolename()), manager);
		manager.save(cacc.getUserAccount());
		repo.save(cacc);
	}

	/**
	 * Change user.
	 *
	 * @param id the id
	 * @param role the role
	 * @param password the password
	 * @return true, if successful
	 */
	public boolean changeUser(Long id, String email, String role, String password) {
		ConcreteUserAccount acc = repo.findOne(id);
		if (acc == null) {
			return false;
		}
		UserAccount usacc = acc.getUserAccount();
		usacc.remove(Role.of("ROLE_ADMIN"));
		usacc.remove(Role.of("ROLE_EMPLOYEE"));
		usacc.remove(Role.of("ROLE_CUSTOMER"));
		usacc.add(Role.of(role));
		manager.save(usacc);
		acc.setRole(Role.of(role));
		acc.setEmail(email);
		manager.changePassword(usacc, password);
		repo.save(acc);
		return true;
	}

}