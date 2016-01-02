package internetkaufhaus.model;

import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import internetkaufhaus.repositories.ConcreteInventory;
/**
 * 
 * @author Wilhelm Mundt
 * A StockManager to manage the stock
 *
 */
@Component
public class StockManager {
	private ConcreteInventory<InventoryItem> inventory;

	/**
	 * Constructor of StockManager
	 * @param inventory
	 */
	@Autowired
	public StockManager(ConcreteInventory<InventoryItem> inventory) {
		this.inventory = inventory;
	}
	/**
	 * Orders a given amount of an article.
	 * @param prodId
	 * @param quantity
	 */
	public void orderArticle(ProductIdentifier prodId, Quantity quantity) {
		inventory.findByProductIdentifier(prodId).ifPresent(x -> {
			x.increaseQuantity(quantity);
			inventory.save(x);
		});
	}
	/**
	 * 
	 * Removes a given amount of an article.
	 * @param prodId
	 * @param quantity
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
