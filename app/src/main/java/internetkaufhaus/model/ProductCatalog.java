package internetkaufhaus.model;

import org.salespointframework.catalog.Catalog;

import internetkaufhaus.model.ConcreteProduct.ProdType;



public interface ProductCatalog extends Catalog<ConcreteProduct>{

	
	Iterable<ConcreteProduct> findByType(ProdType type);
}
