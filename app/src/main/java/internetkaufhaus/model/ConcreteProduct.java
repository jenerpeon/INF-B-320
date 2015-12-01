package internetkaufhaus.model;

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

@Entity
@Table(name = "CPRODUCT")
public class ConcreteProduct extends Product {
	private static final long serialVersionUID = 1L;

	@Column(name = "CATEGORY")
	private String category;

	private String imagefile;
	private String description;
	private String webLink;
	private int comamounts;
	private float rating;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE }, mappedBy = "product", orphanRemoval = true)
	private List<Comment> comments = new LinkedList<Comment>();

	@SuppressWarnings({ "unused", "deprecation" })
	private ConcreteProduct() {

	}

	public ConcreteProduct(String name, Money price, String category, String description, String webLink, String imagefile) {
		super(name, price);
		this.addCategory(category);
		this.description = description;
		this.webLink = webLink;
		this.imagefile = imagefile;
		this.category = category;
	    this.rating = 0;
	}
	public int getRatings(){
		this.comamounts = comments.size();
		return comamounts;
	}
	
	public float getRating(){
        float rating = 0;
        if(comments.isEmpty())
        	return 0;
		for(Comment c : comments){
            rating += c.getRating();			
		}
		this.rating = rating/comments.size();
		return this.rating;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public boolean addComment(Comment c) {
		c.setParent(this);
		return this.comments.add(c);
	}

	public boolean removeComment(Comment c) {
		c.setParent(null);
		return comments.remove(c);
	}

	public Comment removeCommentAt(int i) {
		comments.get(i).setParent(null);
		return comments.remove(i);
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

}
