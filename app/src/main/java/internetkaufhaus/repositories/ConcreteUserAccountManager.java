package internetkaufhaus.repositories;

import java.util.Iterator;
import java.util.Optional;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConcreteUserAccountManager {
	
	Role adminRole;
	UserAccountManager manager;
	
	
	@Autowired
	public ConcreteUserAccountManager(UserAccountManager manager)
	{
		this.manager = manager;
		this.adminRole = Role.of("ROLE_ADMIN");
	}

	public void find()
	{
		
		System.out.println("Hello World");
		if(manager == null)
		{
		System.out.println("Leider Nein");
		}
		UserAccount acc = manager.create("dumdi", "dumdi", adminRole);
		manager.save(acc);
//		Optional <UserAccount> usacc = manager.findByUsername("peon");
//		System.out.println(usacc.get().getUsername());
		Iterator <UserAccount> iter = manager.findAll().iterator();


		
	}



}
