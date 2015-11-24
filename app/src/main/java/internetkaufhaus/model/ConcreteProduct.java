package internetkaufhaus.model;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;

@Entity
@Table(name="CPRODUCT")
public class ConcreteProduct extends Product {
	private static final long serialVersionUID = 1L;
	
	@Column(name="CATEGORY")
	private String category;
	
	private String imagefile;
	private String description;
	private String webLink;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE},mappedBy="product", orphanRemoval=true)
    private List<Comment> comments =  new LinkedList<Comment>();
    
    public List<Comment> getComments(){	return comments; }
    
    public void setComments(List<Comment> comments){ this.comments=comments; }


	@SuppressWarnings({ "unused", "deprecation" })
	private ConcreteProduct() {
		
	}

	public ConcreteProduct(String name, Money price, String category, String description, String webLink,String imagefile) {
		super(name, price);
		this.addCategory(category);
		this.description = description;
		this.webLink = webLink;
		this.imagefile=imagefile;
		this.category = category;

	}

	public List<Comment> getRevComments(){
		List<Comment> l = new LinkedList<Comment>();
 		for(Comment c : this.comments){
		    if(c.isRev()){
		    	l.add(c);
		    }
		}
 		return l;
	}
	public List<Comment> getURevComments(){
		List<Comment> l = new LinkedList<Comment>();
 		for(Comment c : this.comments){
		    if(!c.isRev()){
		    	l.add(c);
		    }
		}
 		return l;
	}
	public void addnewComment(Comment c){
		c.setParent(this);
		this.comments.add(c);
	}
	public void removeComment(Comment c){
		c.setParent(null);
		int index = comments.indexOf(c);
        comments.remove(index);
	}

	public String getImagefile() {
		return imagefile;
	}

	public void setImagefile(String imagefile) {
		this.imagefile = imagefile;
	}

	public String getDescription() {
		return description;
	}

	public String getWebLink() {
		return webLink;
	}

    public String getCategory(){
    	return this.category;
    }


	public Quantity getQuantity(Inventory<InventoryItem> inventory) {
		Optional<InventoryItem> item = inventory.findByProductIdentifier(this.getIdentifier());
		return item.map(InventoryItem::getQuantity).orElse(Quantity.of(0));
	}

	public void setDescription(String description2) {
		this.description=description2;
		
	}

}
