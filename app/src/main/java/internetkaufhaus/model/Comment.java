package internetkaufhaus.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "COMMENTS")
public class Comment implements Serializable {

	private static final long serialVersionUID = 2L;

	@Id 
	@GeneratedValue 
    @Column(name="ID")
    private Long commentid;
    
	@Column(name="TEXT")
	private String text;
	
	@Column(name="RATE")
	private int rating;

	@Column(name="DATE")
	private Date date;
	
	@Column(name="REVIEWED")
	private boolean rev;
	
	@Column(name="REMOVED")
	private boolean rm;
	
	private String formatedDate;
	
    @ManyToOne
    @JoinColumn(name="CPRODUCT_ID", nullable = false)
    private ConcreteProduct product;
    
    public ConcreteProduct getParent() {
        return product;
     }
    public void setParent(ConcreteProduct product){
    	this.product = product;
    }
    

	@SuppressWarnings("unused")
	private Comment() {}

	public Comment(String text, int rating, Date dateTime, String t) {
	

		this.text = text;
		this.rating = rating;
		this.date = dateTime;
		this.formatedDate=t;
		this.rev = false;
		this.rm = false;
		
	}

	public boolean isRev(){
		return this.rev;
	}
	public void setRev(){
		this.rev = true;
	}
	
	public void setFormatedDate(Date date){
		SimpleDateFormat formatter= new SimpleDateFormat("EEE, d MMM yyyy HH:mm", Locale.GERMANY);
		String formatedDate= formatter.format(date);
		this.formatedDate=formatedDate;
		
	}
	public String getFormatedDate(){
		return formatedDate;
	}

	public long getId() {
		return commentid;
	}

	public String getText() {
		return text;
	}

	public Date getDate() {
		return date;
	}

	public int getRating() {
		return rating;
	}

	@Override
	public String toString() {
		return text;
	}
}

