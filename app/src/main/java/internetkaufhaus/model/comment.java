package internetkaufhaus.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "COMMENTS")
public class comment implements Serializable {

	private static final long serialVersionUID = -7114101035786254953L;

	private @Id @GeneratedValue long id;

	private String text;
	private int rating;

	private LocalDateTime date;

	@SuppressWarnings("unused")
	private comment() {}

	public comment(String text, int rating, LocalDateTime dateTime) {

		this.text = text;
		this.rating = rating;
		this.date = dateTime;
	}

	public long getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public LocalDateTime getDate() {
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

