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
		repo.save(cacc);
		manager.save(cacc.getUserAccount());
	}

	/**
	 * Change user.
	 *
	 * @param id the id
	 * @param role the role
	 * @param password the password
	 * @return true, if successful
	 */
	public boolean changeUser(Long id, String role, String password) {
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
		manager.changePassword(usacc, password);
		repo.save(acc);
		return true;
	}
	
	public boolean changeUser2( String firstname, String lastname, String address, String city, String zipCode, String email,Long id, String password) {
		ConcreteUserAccount acc = repo.findOne(id);
		if (acc == null) {
			return false;
		}
		acc.setAddress(address);
		acc.setCity(city);
		acc.setEmail(email);
		acc.setZipCode(zipCode);
		
		UserAccount usacc = acc.getUserAccount();
		usacc.setFirstname(firstname);
		usacc.setLastname(lastname);
		usacc.setEmail(email);
		
		manager.save(usacc);
		
		manager.changePassword(usacc, password);
		repo.save(acc);
		return true;
	}
}