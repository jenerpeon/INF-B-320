package internetkaufhaus.repositories;

import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;

public interface ConcreteInventory<T extends InventoryItem> extends Inventory<T> {
	public Iterable<T> findByQuantity(Quantity quantity);

}
