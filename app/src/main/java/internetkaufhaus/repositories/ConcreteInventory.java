package internetkaufhaus.repositories;

import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;

// TODO: Auto-generated Javadoc
/**
 * The Interface ConcreteInventory.
 *
 * @param <T> the generic type
 */
public interface ConcreteInventory<T extends InventoryItem> extends Inventory<T> {
	
	/**
	 * Find by quantity.
	 *
	 * @param quantity the quantity
	 * @return the iterable
	 */
	public Iterable<T> findByQuantity(Quantity quantity);

}
