package internetkaufhaus.entities;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;
import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;

@Entity
@Table(name = "CPRODUCT")
public class ConcreteProduct extends Product {
	private static final long serialVersionUID = 1L;

	@Column(name = "CATEGORY")
	private String category;
	private String imagefile;
	
	@Lob
	@Column( length = 100000 )
	private String description;
	
	private String webLink;
	
	private long selled = 0;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE }, mappedBy = "product", orphanRemoval = true)
	private List<Comment> comments = new LinkedList<Comment>();

	@SuppressWarnings({ "unused", "deprecation" })
	private ConcreteProduct() {}

	public ConcreteProduct(String name, Money price, String category, String description, String webLink, String imagefile) {
		super(name, price);
		this.addCategory(category);
		this.description = description;
		this.webLink = webLink;
		this.imagefile = imagefile;
		this.category = category;
	}

	public int getRatings() {
		return this.getAcceptedComments().size();
	}

	public boolean isCommentator(ConcreteUserAccount user) {
		for (Comment c : comments) {
			if (c.getUserAccount().equals(user))
				return true;
		}
		return false;
	}

	public List<Integer> getRating() {
		double rating = 0;
		if (comments.isEmpty())
			return null;
		for (Comment c : this.getAcceptedComments()){
			System.out.println("single rating"+c.getRating());
			rating += c.getRating();}
		rating = (rating / (this.getAcceptedComments().size()) + (0.5));
		System.out.println("size:"+this.getAcceptedComments().size());
		System.out.println("rating"+rating);
		return IntStream.range(0, (int)rating).boxed().collect(Collectors.toList());
	}

	public Iterable<Comment> getComments() {
		return comments;
	}

	public String addComment(Comment c, ConcreteUserAccount userAccount) {
//		if (isCommentator(userAccount))
//			return "Sie haben dieses Produkt bereits bewertet";
		c.setProduct(this);
		c.setUser(userAccount);
		userAccount.addComment(c);
		this.comments.add(c);
		return "Vielen Dank fuer Ihre Bewertung";
	}

	public boolean removeComment(Comment c) {
		c.setProduct(null);
		c.setUser(null);
		comments.remove(c);
		return true;
	}

	public List<Comment> getAcceptedComments() {
		List<Comment> l = new LinkedList<Comment>();
		for (Comment c : this.comments) {
			if (c.isAccepted()) {
				l.add(c);
			}
		}
		return l;
	}

	public List<Comment> getUnacceptedComments() {
		List<Comment> l = new LinkedList<Comment>();
		for (Comment c : this.comments) {
			if (!c.isAccepted()) {
				l.add(c);
			}
		}
		return l;
	}

	public String getImagefile() {
		return imagefile;
	}

	public void setImagefile(String imagefile) {
		if (imagefile.length() == 0) {
			throw new IllegalArgumentException();
		}
		this.imagefile = imagefile;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description.length() == 0) {
			throw new IllegalArgumentException();
		}
		this.description = description;

	}

	public String getWebLink() {
		return webLink;
	}

	public void setWebLink(String webLink) {
		if (webLink.length() == 0) {
			throw new IllegalArgumentException();
		}
		this.webLink = webLink;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		if (category.length() == 0) {
			throw new IllegalArgumentException();
		}
		this.category = category;
	}

	public Quantity getQuantity(Inventory<InventoryItem> inventory) {
		Optional<InventoryItem> item = inventory.findByProductIdentifier(this.getIdentifier());
		return item.map(InventoryItem::getQuantity).orElse(Quantity.of(0));
	}
	
	public void increaseSelled(int sell) {
		this.selled += sell;
	}
	
	public long getSelled() {
		return this.selled;
	}
	
	
}
