package internetkaufhaus.model;

import org.springframework.data.repository.CrudRepository;
import internetkaufhaus.model.ConcreteUserAccount; 

public interface ConcreteUserAccountRepository extends CrudRepository<ConcreteUserAccount,Long>{
    
} 


