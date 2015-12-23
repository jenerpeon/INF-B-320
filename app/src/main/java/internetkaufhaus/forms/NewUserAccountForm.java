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

	public void createUser(CreateUserForm createuserform) {
		ConcreteUserAccount cacc = new ConcreteUserAccount(createuserform.getName(),createuserform.getPassword(), Role.of(createuserform.getRolename()), manager);
		repo.save(cacc);
		manager.save(cacc.getUserAccount());
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