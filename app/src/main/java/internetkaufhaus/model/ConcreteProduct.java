package internetkaufhaus.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;

@Entity
public class ConcreteProduct extends Product {
	private static final long serialVersionUID = 3602164805477720501L;

	private String description;
	private String webLink;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Comment> comments = new LinkedList<Comment>();

	@SuppressWarnings({ "unused", "deprecation" })
	private ConcreteProduct() {
	}

	public ConcreteProduct(String name, Money price, String category, String description, String webLink) {
		super(name, price);
		this.addCategory(category);
		this.description = description;
		this.webLink = webLink;

	}

	public String getDescription() {
		return description;
	}

	public String getWebLink() {
		return webLink;
	}

	public Iterable<Comment> getComments() {
		return comments;
	}

	public void addComment(Comment comment) {
		comments.add(comment);
	}

	public Quantity getQuantity(Inventory<InventoryItem> inventory) {
		Optional<InventoryItem> item = inventory.findByProductIdentifier(this.getIdentifier());
		return item.map(InventoryItem::getQuantity).orElse(Quantity.of(0));
	}

}
