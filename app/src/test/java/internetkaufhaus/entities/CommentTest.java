package internetkaufhaus.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.salespointframework.core.Currencies.EURO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;

import internetkaufhaus.entities.Comment;
import internetkaufhaus.entities.ConcreteProduct;

public class CommentTest {
	
	private Comment model;
	 
	@Before 
	public void init(){
		model= new Comment("Das hier ist ein Kommentar",5, new Date(200000),"t");
	}
	
	@Test
	public void testGetCommentId(){
		long number= new Long(232424);
		model.setCommentid(number);
		assertTrue("Id wird zurückgegeben", model.getCommentid()==number);	
	}
	
	@Test
	public void testRemove(){
		model.setRemoved(true);
		assertTrue("Kommentar ist auf remove gesetzt", model.isRemoved()==true);
		model.setRemoved(false);
		assertTrue("Kommentar ist nicht auf remove gesetzt", model.isRemoved()==false);
	}

	@Test
	public void testProduct(){
		ConcreteProduct prod = new ConcreteProduct("Zigarre", Money.of(1.99, EURO), "Tabakwaren", "Eine gute alte Zigarre", "www.tabak.ru", "zigarre.png");
		model.setProduct(prod);
		assertTrue("Product wird zurückgegeben", model.getProduct()==prod);
	}

	@Test
	public void testText(){
		String text="Dieser Text soll angezeigt werden";
		model.setText(text); assertTrue("Text gesetzt", model.getText()==text);
		assertTrue("Text gesetzt", model.toString()==text);
	}
	
	@Test
	public void testRating(){
		int rate=5;
		model.setRating(rate);
		assertTrue("Rating gesetzt", model.getRating()==rate);
	}
	
	@Test
	public void testDate(){
		Date date = new Date(20000);
		model.setDate(date);
		assertTrue("Datum gesetzt", model.getDate()==date);
	}
	
	@Test
	public void testAccept(){
		model.accept();
		assertTrue("Kommentar ist auf accept gesetzt", model.isAccepted()==true);
		model.setAccepted(false);
		assertTrue("Kommentar ist nicht auf accept gesetzt", model.isAccepted()==false);
	}
	
	@Test
	public void testFormattedDate(){
		Date date= new Date(20000);
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm", Locale.GERMANY);
		String formattedDate = formatter.format(date);
		model.setFormatedDate(date);
		assertEquals("FormattedDate gesetzt", model.getFormatedDate(),formattedDate);
		
	}
	
}
