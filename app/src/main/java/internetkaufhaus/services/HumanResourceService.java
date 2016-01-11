package internetkaufhaus.services;

import java.util.Optional;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import internetkaufhaus.entities.ConcreteUserAccount;
import internetkaufhaus.forms.CreateUserForm;
import internetkaufhaus.forms.EditCustomerForm;
import internetkaufhaus.forms.EditUserForm;

/**
 * 
 * @author Wilhelm Mundt
 *
 *         A service class for Human Resource
 *
 */
@Service
public class HumanResourceService {

	@Autowired
	private DataService dataService;
	@Autowired
	private AccountingService accService;

	/**
	 * 
	 */
	public HumanResourceService() {
		// Empty constructor
	}

	/**
	 * Hires an employee
	 * 
	 * @param form
	 *            The CreateUserForm
	 * @return
	 */
	public boolean hireEmployee(CreateUserForm form) {
		ConcreteUserAccount acc = null;
		try {
			acc = new ConcreteUserAccount(form.getEmail(), form.getName(), form.getFirstname(), form.getLastname(),
					form.getStreet(), form.getHouseNumber(), form.getZipCode(), form.getCity(), form.getPassword(),
					Role.of(form.getRolename()), dataService.getUserAccountManager());
			accService.addUser(acc);
		} catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Fires an employee
	 * 
	 * @param id
	 *            The employee's id
	 * @param admin
	 *            The admin responsible for the action
	 * @return
	 */
	public boolean fireEmployee(Long id, Optional<UserAccount> admin) {
		if (!(admin.isPresent()))
			return false;
		if (dataService.getConcreteUserAccountRepository().findOne(id).getUserAccount().getIdentifier()
				.equals(admin.get().getIdentifier())) {
			return false;
		}
		System.out.println(
				dataService.getConcreteUserAccountRepository().findOne(id).getUserAccount().getIdentifier().toString()
						+ "/n");
		System.out.println(admin.map(UserAccount::getIdentifier).get().toString() + "/n");
		System.out.println(dataService.getConcreteUserAccountRepository().findOne(id).getUserAccount().getIdentifier()
				.equals(admin.map(UserAccount::getIdentifier).get()));
		dataService.getUserAccountManager()
				.disable(dataService.getConcreteUserAccountRepository().findOne(id).getUserAccount().getId());
		dataService.getConcreteUserAccountRepository().delete(id);
		return true;
	}

	/**
	 * Changes an employee
	 * 
	 * @param form
	 *            The EditUserForm
	 * @param admin
	 *            The admin responsible for the action
	 * @return
	 */
	public boolean changeEmployee(EditUserForm form, Optional<UserAccount> admin) {
		ConcreteUserAccount acc = dataService.getConcreteUserAccountRepository().findOne(form.getId());
		if (acc == null || !(admin.isPresent())) {
			return false;
		}
		if (acc.getUserAccount().getId().equals(admin.get().getIdentifier())
				&& !(form.getRolename().equals("ROLE_ADMIN"))) {
			return false;
		}
		acc.getUserAccount().remove(Role.of("ROLE_ADMIN"));
		acc.getUserAccount().remove(Role.of("ROLE_EMPLOYEE"));
		acc.getUserAccount().remove(Role.of("ROLE_CUSTOMER"));
		acc.getUserAccount().add(Role.of(form.getRolename()));
		acc.setEmail(form.getEmail());
		acc.setStreet(form.getStreet());
		acc.setHouseNumber(form.getHouseNumber());
		acc.setCity(form.getCity());
		acc.setZipCode(form.getZipCode());
		acc.getUserAccount().setFirstname(form.getFirstname());
		acc.getUserAccount().setLastname(form.getLastname());
		acc.setRole(Role.of(form.getRolename()));
		dataService.getUserAccountManager().save(acc.getUserAccount());
		dataService.getUserAccountManager().changePassword(acc.getUserAccount(), form.getPassword());
		dataService.getConcreteUserAccountRepository().save(acc);
		return true;
	}

	public boolean changeCustomer(EditCustomerForm form, Optional<UserAccount> customer) {
		if (!(form.getPassword().equals(form.getPasswordrepeat()))) {
			return false;
		}
		ConcreteUserAccount caccount = dataService.getConcreteUserAccountRepository().findByUserAccount(customer.get())
				.get();
		caccount.setEmail(form.getEmail());
		caccount.setStreet(form.getStreet());
		caccount.setHouseNumber(form.getHouseNumber());
		caccount.setCity(form.getCity());
		caccount.setZipCode(form.getZipCode());
		caccount.getUserAccount().setFirstname(form.getFirstname());
		caccount.getUserAccount().setLastname(form.getLastname());
		dataService.getUserAccountManager().save(caccount.getUserAccount());
		dataService.getUserAccountManager().changePassword(caccount.getUserAccount(), form.getPassword());
		dataService.getConcreteUserAccountRepository().save(caccount);
		return true;
	}
}
