package internetkaufhaus.forms;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.salespointframework.catalog.ProductIdentifier;

// TODO: Auto-generated Javadoc
/**
 * The Class StockForm.
 */
public class StockForm {

	/** The quantity. */
	@NotNull(message = "leer")
	@Min(1)
	private int quantity;

	/** The prod id. */
	private ProductIdentifier prodId;

	/**
	 * This comment is just here because sonarcube is a little bitch.
	 */
	public StockForm() {
		/**
		 * This comment is just here because sonarcube is a little bitch.
		 */
	}

	/**
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Sets the quantity.
	 *
	 * @param quantity the new quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * Gets the prod id.
	 *
	 * @return the prod id
	 */
	public ProductIdentifier getProdId() {
		return prodId;
	}

	/**
	 * Sets the prod id.
	 *
	 * @param prodId the new prod id
	 */
	public void setProdId(ProductIdentifier prodId) {
		this.prodId = prodId;
	}

}
