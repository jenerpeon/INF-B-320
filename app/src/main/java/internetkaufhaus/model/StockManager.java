package internetkaufhaus.model;

import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import internetkaufhaus.repositories.ConcreteInventory;

@Component
public class StockManager {

	/**
	 * The StockManager class is responsible for changing the amount of products at the depot.
	 */
	private ConcreteInventory<InventoryItem> inventory;

	@Autowired
	public StockManager(ConcreteInventory<InventoryItem> inventory) {
		this.inventory = inventory;
	}

	
	/**
	 * The orderArticle method is used to increase the amount of given products.
	 * 
	 * @param prodId select product via it's id
	 * @param quantity amount of products to add
	 * 
	 */
	public void orderArticle(ProductIdentifier prodId, Quantity quantity) {
		inventory.findByProductIdentifier(prodId).ifPresent(x -> {
			x.increaseQuantity(quantity);
			inventory.save(x);
		});
	}

	/**
	 * The removeArticle method is used to decrease the amount of given products.
	 * 
	 * @param prodId select product via it's id
	 * @param quantity amount of products to remove
	 * 
	 */
	public void removeArticle(ProductIdentifier prodId, Quantity quantity) {
		inventory.findByProductIdentifier(prodId).ifPresent(x -> {
			if (x.hasSufficientQuantity(quantity)) {
				x.decreaseQuantity(quantity);
				inventory.save(x);
			}
		});
	}
}
