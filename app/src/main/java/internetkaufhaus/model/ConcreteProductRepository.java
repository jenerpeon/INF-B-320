package internetkaufhaus.model;

import java.io.Serializable;
import java.util.List;

import org.salespointframework.catalog.Catalog;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ConcreteProductRepository extends PagingAndSortingRepository<ConcreteProduct,Long>{
    //Iterable<ConcreteProduct> findByCategory(String category, Sort sort);
	Page<ConcreteProduct> findByCategory(String category, Pageable pageable);
	Iterable<ConcreteProduct> findAll(Sort sort);
	Page<ConcreteProduct> findAll(Pageable pageable);


}
