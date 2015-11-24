package internetkaufhaus.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.salespointframework.core.Currencies.EURO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;

public class ConcreteProductTest {
	
	private ConcreteProduct model;
	
	@Before
	public void init(){
		model = new ConcreteProduct("Zigarre", Money.of(1.99, EURO), "Tabakwaren", "Eine gute alte Zigarre", "www.tabak.ru", "zigarre.png");		
	}
	
	@Test
	public void testGetName() {
		assertTrue("Beschreibung erhalten", model.getName().equals("Zigarre"));
	}
	
	@Test
	public void testComments() {
		Comment comment = new Comment("das Produkt ist ekelerregend", 1, new Date(200000), "t");
		model.addComment(comment);
		assertTrue("Kommentar gesetzt", model.getComments().contains(comment));
		assertTrue("Kommentareigenschaft gesetzt", comment.getParent() == model);
		assertTrue("Kommentar in der Liste der nicht akzeptierten Kommentare", model.getUnacceptedComments().contains(comment));
		assertFalse("Kommentar nicht in der Liste der akzeptierten Kommentare", model.getAcceptedComments().contains(comment));
		comment.accept();
		assertFalse("Kommentar nicht in der Liste der nicht akzeptierten Kommentare", model.getUnacceptedComments().contains(comment));
		assertTrue("Kommentar in der Liste der akzeptierten Kommentare", model.getAcceptedComments().contains(comment));
		model.removeComment(comment);
		assertFalse("Kommentar geloescht", model.getComments().contains(comment));
		assertTrue("Kommentareigenschaft geloescht", comment.getParent() == null);
		Comment comment2 = new Comment("Ich liebe dieses Produkt!", 5, new Date(200001), "t");
		List<Comment> commentList = new ArrayList<Comment>();
		commentList.add(comment);
		commentList.add(comment2);
		model.setComments(commentList);
		assertTrue("Kommentarliste gesetzt", model.getComments() == commentList);
		assertTrue("Kommentar 1 gesetzt", model.getComments().contains(comment));
		assertTrue("Kommentar 2 gesetzt", model.getComments().contains(comment2));
		model.removeCommentAt(1);
		assertTrue("Kommentar 2 geloescht", model.getComments().size() == 1 && !model.getComments().contains(comment2) && model.getComments().contains(comment));
		model.removeCommentAt(0);
		assertTrue("Kommentar 1 geloescht", model.getComments().size() == 0 && !model.getComments().contains(comment) && !model.getComments().contains(comment2));
	}
	
	@Test
	public void testImagefile() {
		String imagefile = "";
		Random random = new Random();
		String availableChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-_:;/()&?=\"!+*#äöüß$ "; // TODO: please review.
		for (int i = 1; i < 200; i++) {
			imagefile = "";
			for (int j = 0; j < i; j++) { // maximum image length is 200 (?) so we are testing for all image lengths between 0 and 200.
				imagefile += availableChars.charAt(random.nextInt(availableChars.length()));
			}
			model.setImagefile(imagefile);
			assertTrue("Imagefile gesetzt", model.getImagefile() == imagefile);
		}
	}

	@Test(expected=IllegalArgumentException.class)
	public void testEmptyImageFile() throws Exception{
		model.setImagefile("");
	}

	@Test
	public void testDescription() {
		String description = "";
		Random random = new Random();
		String availableChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-_:;/()&?=\"!+*#äöüß$ "; // TODO: please review.
		for (int i = 1; i < 2000; i++) {
			description = "";
			for (int j = 0; j < i; j++) { // maximum description length is 2000 (?) so we are testing for image lengths between 0 and 2000.
				description += availableChars.charAt(random.nextInt(availableChars.length()));
			}
			model.setDescription(description);
			assertTrue("Description gesetzt", model.getDescription() == description);
		}
	}

	@Test(expected=IllegalArgumentException.class)
	public void testEmptyDescription() throws Exception{
		model.setDescription("");
	}

	@Test
	public void testWebLink() {
		String webLink = "";
		Random random = new Random();
		String availableChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-_:;/()&?=\"!+*#äöüß$ "; // TODO: please review.
		for (int i = 1; i < 2000; i++) {
			webLink = "";
			for (int j = 0; j < i; j++) { // maximum description length is 2000 (?) so we are testing for image lengths between 0 and 2000.
				webLink += availableChars.charAt(random.nextInt(availableChars.length()));
			}
			model.setWebLink(webLink);
			assertTrue("Weblink gesetzt", model.getWebLink() == webLink);
		}
	}

	@Test(expected=IllegalArgumentException.class)
	public void testEmptyWebLink() throws Exception{
		model.setWebLink("");
	}

	@Test
	public void testCategory() {
		String category = "";
		Random random = new Random();
		String availableChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-_:;/()&?=\"!+*#äöüß$ "; // TODO: please review.
		for (int i = 1; i < 2000; i++) {
			category = "";
			for (int j = 0; j < i; j++) { // maximum description length is 2000 (?) so we are testing for image lengths between 0 and 2000.
				category += availableChars.charAt(random.nextInt(availableChars.length()));
			}
			model.setCategory(category);
			assertTrue("Category gesetzt", model.getCategory() == category);
		}
	}

	@Test(expected=IllegalArgumentException.class)
	public void testEmptyCategory() throws Exception{
		model.setCategory("");
	}

	@Test
	public void testGetQuantity() {
		// TODO: Keine Ahnung, wie man das hier macht => Christoph fragen wegen Spring Test Framework
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetNameError() throws Exception{
		model = new ConcreteProduct("", Money.of(1.99, EURO), "Tabakwaren", "Eine gute alte Zigarre", "www.tabak.ru", "zigarre.png");
	}

}
