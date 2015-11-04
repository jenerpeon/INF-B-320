package internetkaufhaus.model;

import javax.persistence.Entity;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;

@Entity
public class ConcreteProduct extends Product {
	private static final long serialVersionUID = 3602164805477720501L;

	public static enum ProdType{
		Fuzz, Trash, Garbage;
	}

	private ProdType prodType;

		
    private ConcreteProduct(){}
    
	public ConcreteProduct(String name, Money price, ProdType prodType){
        super(name, price);
		this.prodType=prodType;
	}
}

