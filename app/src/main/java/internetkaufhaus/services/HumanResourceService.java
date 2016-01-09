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
 *A service class for Human Resource
 *
 */
@Service
public class HumanResourceService {
	
	@Autowired
	DataService dataService;
	@Autowired
	AccountingService accService;
	/**
	 * 
	 */
	public HumanResourceService()
	{
		
	}

	/**
	 * Hires an employee
	 * @param form The CreateUserForm
	 * @return
	 */
	public boolean hireEmployee(CreateUserForm form) {
		ConcreteUserAccount acc = null;
		// Checks if Email or username is already in use
		if(accService.isRegistered(form.getEmail()) && !dataService.getUserAccountManager().findByUsername(form.getName()).isPresent())
		{
			return false;
		}
		if(!(form.getPassword().equals(form.getPasswordrepeat()))) {
			return false;
		}
		try
		{
			System.out.println("Test2");
			acc = new ConcreteUserAccount(form.getEmail(), form.getName(), form.getFirstname(),
					form.getLastname(), form.getAddress(), form.getZipCode(), form.getCity(),
					form.getPassword(), Role.of(form.getRolename()), dataService.getUserAccountManager());
			accService.addUser(acc);
		}
		catch(Exception e)
		{
			System.out.println("Error");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * Fires an employee
	 * @param id The employee's id
	 * @param admin The admin responsible for the action
	 * @return
	 */
	public boolean fireEmployee(Long id, Optional<UserAccount> admin)
	{
		if(!(admin.isPresent()))
			return false;
		if(dataService.getConcreteUserAccountRepository().findOne(id).getUserAccount().getIdentifier().equals(admin.get().getIdentifier()))
		{
			return false;
		}
		System.out.println(dataService.getConcreteUserAccountRepository().findOne(id).getUserAccount().getIdentifier().toString()+"/n");
		System.out.println(admin.map(UserAccount::getIdentifier).get().toString()+"/n");
		System.out.println(dataService.getConcreteUserAccountRepository().findOne(id).getUserAccount().getIdentifier().equals(admin.map(UserAccount::getIdentifier).get()));
		dataService.getUserAccountManager().disable(dataService.getConcreteUserAccountRepository().findOne(id).getUserAccount().getId());
		dataService.getConcreteUserAccountRepository().delete(id);
		return true;
	}
	/**
	 * Changes an employee
	 * @param form The EditUserForm
	 * @param admin The admin responsible for the action
	 * @return
	 */
	public boolean changeEmployee(EditUserForm form, Optional<UserAccount> admin) {
		ConcreteUserAccount acc = dataService.getConcreteUserAccountRepository().findOne(form.getId());
		if (acc == null) {
			return false;
		}
		if(!(admin.isPresent()))
		{
			return false;
		}
		if(acc.getUserAccount().getId().equals(admin.get().getIdentifier()) && !(form.getRolename().equals("ROLE_ADMIN")))
		{
			return false;
		}
		UserAccount usacc = acc.getUserAccount();
		usacc.remove(Role.of("ROLE_ADMIN"));
		usacc.remove(Role.of("ROLE_EMPLOYEE"));
		usacc.remove(Role.of("ROLE_CUSTOMER"));
		usacc.add(Role.of(form.getRolename()));
		dataService.getUserAccountManager().save(usacc);
		acc.setRole(Role.of(form.getRolename()));
		dataService.getUserAccountManager().changePassword(usacc, form.getPassword());
		dataService.getConcreteUserAccountRepository().save(acc);
		return true;
	}
	
	public boolean changeCustomer(EditCustomerForm form, Optional<UserAccount> customer) {
		if(!(form.getPassword().equals(form.getPasswordrepeat())))
		{
			return false;
		}
		ConcreteUserAccount caccount = dataService.getConcreteUserAccountRepository().findByUserAccount(customer.get()).get();
		caccount.setEmail(form.getEmail());
		caccount.setAddress(form.getAddress());
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
