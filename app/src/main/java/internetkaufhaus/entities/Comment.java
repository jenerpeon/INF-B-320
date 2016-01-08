package internetkaufhaus.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

// TODO: Auto-generated Javadoc
/**
 * The Class Comment.
 */
@Entity
@Table(name = "COMMENTS")
public class Comment implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2L;

	/** The commentid. */
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long commentid;

	/** The title. */
	@Column(name = "TITLE",length = 100000)
	private String title;
	
	/** The text. */
	// @NotEmpty(message="{Comment.text.NotEmpty}")
	@Column(name = "TEXT",length = 100000)
	private String text;

	/** The rating. */
	@Column(name = "RATE")
	private int rating;

	/** The date. */
	@Column(name = "DATE")
	private LocalDateTime date;

	/** The accepted. */
	@Column(name = "REVIEWED")
	private boolean accepted;

	/** The formated date. */
	private String formatedDate;

	/** The product. */
	@ManyToOne
	@JoinColumn(name = "CPRODUCT_ID", nullable = false)
	private ConcreteProduct product;

	/** The user. */
	@OneToOne
	@JoinColumn(name = "CACCOUNT_ID", nullable = false)
	private ConcreteUserAccount user;

	/**
	 * Instantiates a new comment.
	 */
	@SuppressWarnings("unused")
	private Comment() {
	}

	/**
	 * Instantiates a new comment.
	 *
	 * @param text
	 *            the text
	 * @param rating
	 *            the rating
	 * @param dateTime
	 *            the date time
	 * @param t
	 *            the t
	 */
	public Comment(String title, String text, int rating, LocalDateTime dateTime, String t) {
		this.title = title;
		this.text = text;
		this.rating = rating;
		this.date = dateTime;
		this.formatedDate = t;
		this.accepted = false;
	}

	/**
	 * Gets the user account.
	 *
	 * @return the user account
	 */
	public ConcreteUserAccount getUserAccount() {
		return this.user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user
	 *            the new user
	 */
	public void setUser(ConcreteUserAccount user) {
		this.user = user;
	}

	/**
	 * Gets the commentid.
	 *
	 * @return the commentid
	 */
	public Long getCommentid() {
		return commentid;
	}

	/**
	 * Sets the commentid.
	 *
	 * @param commentid
	 *            the new commentid
	 */
	public void setCommentid(Long commentid) {
		this.commentid = commentid;
	}

	/**
	 * Gets the product.
	 *
	 * @return the product
	 */
	public ConcreteProduct getProduct() {
		return this.product;
	}

	/**
	 * Sets the product.
	 *
	 * @param product
	 *            the new product
	 */
	public void setProduct(ConcreteProduct product) {
		this.product = product;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Sets the text.
	 *
	 * @param text
	 *            the new text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Sets the rating.
	 *
	 * @param rating
	 *            the new rating
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * Sets the date.
	 *
	 * @param date
	 *            the new date
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	/**
	 * Checks if is accepted.
	 *
	 * @return true, if is accepted
	 */
	public boolean isAccepted() {
		return this.accepted;
	}

	/**
	 * Accept.
	 */
	public void accept() {
		this.accepted = true;
	}

	/**
	 * Sets the accepted.
	 *
	 * @param accepted
	 *            the new accepted
	 */
	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	/**
	 * Sets the formated date.
	 *
	 * @param formatedDate
	 *            the new formated date
	 */
	public void setFormatedDate(String formatedDate) {
		this.formatedDate = formatedDate;
	}

	/**
	 * Sets the formated date.
	 *
	 * @param date
	 *            the new formated date
	 */
	public void setFormatedDate(LocalDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm", Locale.GERMANY);
		String formatedDate = formatter.format(date);
		this.formatedDate = formatedDate;

	}

	/**
	 * Gets the formated date.
	 *
	 * @return the formated date
	 */
	public String getFormatedDate() {
		return formatedDate;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return commentid;
	}
	
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * Gets the rating.
	 *
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return text;
	}
}
