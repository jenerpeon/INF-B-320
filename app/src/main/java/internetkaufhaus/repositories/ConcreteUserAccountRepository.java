package internetkaufhaus.repositories;

import java.util.Optional;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import internetkaufhaus.entities.ConcreteUserAccount;

// TODO: Auto-generated Javadoc
/**
 * The Interface ConcreteUserAccountRepository.
 */
public interface ConcreteUserAccountRepository extends CrudRepository<ConcreteUserAccount, Long> {
	
	/**
	 * Find by email.
	 *
	 * @param email the email
	 * @return the concrete user account
	 */
	Optional<ConcreteUserAccount> findByEmail(String email);

	/**
	 * Find by role.
	 *
	 * @param role the role
	 * @return the iterable
	 */
	Iterable<ConcreteUserAccount> findByRole(Role role);

	/**
	 * Find by user account.
	 *
	 * @param acc the acc
	 * @return the concrete user account
	 */
	Optional<ConcreteUserAccount> findByUserAccount(UserAccount acc);
	
	Page<ConcreteUserAccount> findByRole(Role role, Pageable pageable);

}
