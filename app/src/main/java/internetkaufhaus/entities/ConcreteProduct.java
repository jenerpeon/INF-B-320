package internetkaufhaus.entities;

import static org.salespointframework.core.Currencies.EURO;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;

// TODO: Auto-generated Javadoc
/**
 * The Class ConcreteProduct.
 */
@Entity
@Table(name = "CPRODUCT")
public class ConcreteProduct extends Product {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The category. */
	@Column(name = "CATEGORY")
	private String category;

	/** The imagefile. */
	private String imagefile;

	/** The description. */
	@Column(name = "DESCRIPTION", length = 100000)
	private String description;

	/** The web link. */
	@Column(name = "WEBLINK")
	private String webLink;

	/** The amount products sold. */
	@Column(name = "SOLD")
	private long amountProductsSold = 0;

	/** The buying price. */
	@Column(name = "BUYING_PRICE")
	private BigDecimal buyingPrice;
	
	@Column(name = "PRICE_DECIMAL")
	private BigDecimal priceDecimal;

	/** The average rating. */
	@Column
	private float averageRating = 0;

	/** The comments. */
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH,
			CascadeType.REMOVE }, mappedBy = "product", orphanRemoval = true)
	private List<Comment> comments = new LinkedList<Comment>();

	/**
	 * Instantiates a new concrete product.
	 */
	@SuppressWarnings({ "unused", "deprecation" })
	private ConcreteProduct() {
	}

	/**
	 * Instantiates a new concrete product.
	 *
	 * @param name
	 *            the name
	 * @param price
	 *            the price
	 * @param buyingPrice
	 *            the buying price
	 * @param category
	 *            the category
	 * @param description
	 *            the description
	 * @param webLink
	 *            the web link
	 * @param imagefile
	 *            the imagefile
	 */
	public ConcreteProduct(String name, Money price, Money buyingPrice, String category, String description,
			String webLink, String imagefile) {
		super(name, price);
		this.addCategory(category);
		this.buyingPrice = buyingPrice.getNumberStripped();
		this.priceDecimal = price.getNumberStripped();
		this.description = description;
		this.webLink = webLink;
		this.imagefile = imagefile;
		this.category = category;
	}

	/**
	 * Gets the ratings.
	 *
	 * @return the ratings
	 */
	public int getRatings() {
		return this.getAcceptedComments().size();
	}

	/**
	 * Checks if is commentator.
	 *
	 * @param user
	 *            the user
	 * @return true, if is commentator
	 */
	public boolean isCommentator(ConcreteUserAccount user) {
		for (Comment c : comments) {
			if (c.getUserAccount().equals(user))
				return true;
		}
		return false;
	}

	/**
	 * Update average rating.
	 */
	public void updateAverageRating() {
		int rating = 0;
		for (Comment comm : this.getAcceptedComments()) {
			rating += comm.getRating();
		}
		this.averageRating = (float) rating / this.getAcceptedComments().size();
	}

	/**
	 * Gets the average rating.
	 *
	 * @return the average rating
	 */
	public float getAverageRating() {
		updateAverageRating();
		return Math.round(this.averageRating);
	}

	/**
	 * Gets the comments.
	 *
	 * @return the comments
	 */
	public Iterable<Comment> getComments() {
		return comments;
	}

	/**
	 * Adds the comment.
	 *
	 * @param c
	 *            the c
	 * @param userAccount
	 *            the user account
	 * @return the string
	 */
	public String addComment(Comment c, ConcreteUserAccount userAccount) {
		// if (isCommentator(userAccount))
		// return "Sie haben dieses Produkt bereits bewertet";
		c.setProduct(this);
		c.setUser(userAccount);
		userAccount.addComment(c);
		this.comments.add(c);
		return "Vielen Dank fuer Ihre Bewertung";
	}

	/**
	 * Removes the comment.
	 *
	 * @param c
	 *            the c
	 * @return true, if successful
	 */
	public boolean removeComment(Comment c) {
		c.getUserAccount().removeComment(c);
		c.setProduct(null);
		c.setUser(null);
		this.comments.remove(c);
		return true;
	}

	/**
	 * Gets the accepted comments.
	 *
	 * @return the accepted comments
	 */
	public List<Comment> getAcceptedComments() {
		List<Comment> l = new LinkedList<Comment>();
		for (Comment c : this.comments) {
			if (c.isAccepted()) {
				l.add(c);
			}
		}
		return l;
	}

	/**
	 * Gets the unaccepted comments.
	 *
	 * @return the unaccepted comments
	 */
	public List<Comment> getUnacceptedComments() {
		List<Comment> l = new LinkedList<Comment>();
		for (Comment c : this.comments) {
			if (!c.isAccepted()) {
				l.add(c);
			}
		}
		return l;
	}

	/**
	 * Gets the imagefile.
	 *
	 * @return the imagefile
	 */
	public String getImagefile() {
		return imagefile;
	}

	/**
	 * Gets the price float.
	 *
	 * @return the price float
	 */
	public String getPriceFloat() {
		DecimalFormat formatter = new DecimalFormat("0.00€");
		return formatter.format(this.getPrice().getNumberStripped());
	}

	/**
	 * Sets the imagefile.
	 *
	 * @param imagefile
	 *            the new imagefile
	 */
	public void setImagefile(String imagefile) {
		if (imagefile.length() == 0) {
			throw new IllegalArgumentException();
		}
		this.imagefile = imagefile;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		if (description.length() == 0) {
			throw new IllegalArgumentException();
		}
		this.description = description;

	}

	/**
	 * Gets the web link.
	 *
	 * @return the web link
	 */
	public String getWebLink() {
		return webLink;
	}

	/**
	 * Sets the web link.
	 *
	 * @param webLink
	 *            the new web link
	 */
	public void setWebLink(String webLink) {
		if (webLink.length() == 0) {
			throw new IllegalArgumentException();
		}
		this.webLink = webLink;
	}

	/**
	 * Gets the category.
	 *
	 * @return the category
	 */
	public String getCategory() {
		return this.category;
	}

	/**
	 * Sets the category.
	 *
	 * @param category
	 *            the new category
	 */
	public void setCategory(String category) {
		if (category.length() == 0) {
			throw new IllegalArgumentException();
		}
		this.category = category;
	}

	/**
	 * Increase sold.
	 *
	 * @param sell
	 *            the sell
	 */
	public void increaseSold(int sell) {
		this.amountProductsSold += sell;
	}

	/**
	 * Gets the sold.
	 *
	 * @return the sold
	 */
	public long getSold() {
		return this.amountProductsSold;
	}

	/**
	 * Gets the buying price.
	 *
	 * @return the buying price
	 */
	public Money getBuyingPrice() {
		return Money.of(this.buyingPrice, EURO);
	}

	/**
	 * Sets the buying price.
	 *
	 * @param buyingPrice
	 *            the new buying price
	 */
	public void setBuyingPrice(Money buyingPrice) {
		this.buyingPrice = buyingPrice.getNumberStripped();
	}
	
	@Override
	public void setPrice(Money price) {
		super.setPrice(price);
		this.priceDecimal = price.getNumberStripped();
	}
	
	public BigDecimal getPriceDecimal() {
		return priceDecimal;
	}

}
