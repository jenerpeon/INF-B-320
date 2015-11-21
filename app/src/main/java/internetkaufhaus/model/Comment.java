package internetkaufhaus.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "COMMENTS")
public class Comment implements Serializable {

	private static final long serialVersionUID = -7114101035786254953L;

	private @Id @GeneratedValue long id;

	private String text;
	private int rating;

	private Date date;
	private String formatedDate;

	@SuppressWarnings("unused")
	private Comment() {}

	public Comment(String text, int rating, Date dateTime, String t) {
	

		this.text = text;
		this.rating = rating;
		this.date = dateTime;
		this.formatedDate=t;
		
		
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
		return id;
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

