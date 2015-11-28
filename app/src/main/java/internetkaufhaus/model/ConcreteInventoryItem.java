package internetkaufhaus.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;

@Entity
@Table(name ="CITEM")
public class ConcreteInventoryItem extends InventoryItem {
	
	private static final long serialVersionUID = -4564237030032766712L;

	@SuppressWarnings({"unused","deprecation"})
	private ConcreteInventoryItem()
	{
		
	}
	public ConcreteInventoryItem(ConcreteProduct product, Quantity quantity)
	{
		super(product, quantity);
	}
}
