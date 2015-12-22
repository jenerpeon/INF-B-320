package internetkaufhaus.forms;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import internetkaufhaus.entities.ConcreteUserAccount;
import internetkaufhaus.repositories.ConcreteUserAccountRepository;

@Component
public class NewUserAccountForm {

	private UserAccountManager manager;
	private ConcreteUserAccountRepository repo;

	@Autowired
	public NewUserAccountForm(UserAccountManager manager, ConcreteUserAccountRepository repo) {
		this.manager = manager;
		this.repo = repo;
	}

	public long createUser(CreateUserForm createuserform) {

		ConcreteUserAccount cacc = new ConcreteUserAccount(
				// createuserform.getEmail(),
				// createuserform.getUsername(),
				createuserform.getName(),
				// createuserform.getFirstname(),
				// createuserform.getLastname(),
				// createuserform.getAddress(),
				// createuserform.getZipcode(),
				// createuserform.getCity(),
				createuserform.getPassword(), Role.of(createuserform.getRolename()), manager);
		repo.save(cacc);
		manager.save(cacc.getUserAccount());
		return cacc.getId();
	}

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
}