package internetkaufhaus.entities;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "COMMENTS")
public class Comment implements Serializable {

	private static final long serialVersionUID = 2L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long commentid;

	// @NotEmpty(message="{Comment.text.NotEmpty}")
	@Column(name = "TEXT")
	private String text;

	@Column(name = "RATE")
	private int rating;

	@Column(name = "DATE")
	private Date date;

	@Column(name = "REVIEWED")
	private boolean accepted;

	private String formatedDate;

	@ManyToOne
	@JoinColumn(name = "CPRODUCT_ID", nullable = false)
	private ConcreteProduct product;

	@OneToOne
	@JoinColumn(name = "CACCOUNT_ID", nullable = false)
	private ConcreteUserAccount user;

	@SuppressWarnings("unused")
	private Comment() {
	}

	public Comment(String text, int rating, Date dateTime, String t) {
		this.text = text;
		this.rating = rating;
		this.date = dateTime;
		this.formatedDate = t;
		this.accepted = false;
	}

	public ConcreteUserAccount getUserAccount() {
		return this.user;
	}

	public void setUser(ConcreteUserAccount user) {
		this.user = user;
	}

	public Long getCommentid() {
		return commentid;
	}

	public void setCommentid(Long commentid) {
		this.commentid = commentid;
	}

	public ConcreteProduct getProduct() {
		return this.product;
	}

	public void setProduct(ConcreteProduct product) {
		this.product = product;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isAccepted() {
		return this.accepted;
	}

	public void accept() {
		this.accepted = true;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public void setFormatedDate(String formatedDate) {
		this.formatedDate = formatedDate;
	}

	public void setFormatedDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy", Locale.GERMANY);
		String formatedDate = formatter.format(date);
		this.formatedDate = formatedDate;

	}

	public String getFormatedDate() {
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
