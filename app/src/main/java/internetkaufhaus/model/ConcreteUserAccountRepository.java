package internetkaufhaus.model;



import org.salespointframework.useraccount.Role;
import org.springframework.data.repository.CrudRepository;
import internetkaufhaus.model.ConcreteUserAccount;

public interface ConcreteUserAccountRepository extends CrudRepository<ConcreteUserAccount,Long>{
	ConcreteUserAccount findByEmail(String email); 
	Iterable<ConcreteUserAccount> findByRole(Role role);
} 


