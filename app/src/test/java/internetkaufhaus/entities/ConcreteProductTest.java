package internetkaufhaus.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.salespointframework.core.Currencies.EURO;

import java.util.Date;
import java.util.Random;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.salespointframework.useraccount.UserAccount;

public class ConcreteProductTest {

	private ConcreteProduct model;
	// private UserAccountManager manager;
	// private ConcreteUserAccountRepository concretemanager;

	@Before
	public void init() {
		// UserAccount dummy = new UserAccount("hans","schranz",
		// Role.of("ROLE_CUSTOMER"));
		model = new ConcreteProduct("Zigarre", Money.of(1.99, EURO), Money.of(1.49, EURO), "Tabakwaren",
				"Eine gute alte Zigarre", "www.tabak.ru", "zigarre.png");
	}

	@Test
	public void testGetName() {
		assertTrue("Beschreibung erhalten", model.getName().equals("Zigarre"));
	}

	/*
	 * @Test public void testComments() { ConcreteUserAccount dummdummy = new
	 * ConcreteUserAccount("peon", "peon",Role.of("ROLE_CUSTOMER"), manager);
	 * concretemanager.save(dummdummy); Comment comment = new Comment(
	 * "das Produkt ist ekelerregend", 1, new Date(200000), "t");
	 * model.addComment(comment, dummdummy.getUserAccount().getIdentifier());
	 * assertTrue("Kommentar gesetzt", model.getComments().contains(comment));
	 * assertTrue("Kommentareigenschaft gesetzt", comment.getParent() == model);
	 * assertTrue("Kommentar in der Liste der nicht akzeptierten Kommentare",
	 * model.getUnacceptedComments().contains(comment)); assertFalse(
	 * "Kommentar nicht in der Liste der akzeptierten Kommentare",
	 * model.getAcceptedComments().contains(comment)); comment.accept();
	 * assertFalse(
	 * "Kommentar nicht in der Liste der nicht akzeptierten Kommentare",
	 * model.getUnacceptedComments().contains(comment)); assertTrue(
	 * "Kommentar in der Liste der akzeptierten Kommentare",
	 * model.getAcceptedComments().contains(comment));
	 * model.removeComment(comment); assertFalse("Kommentar geloescht",
	 * model.getComments().contains(comment)); assertTrue(
	 * "Kommentareigenschaft geloescht", comment.getParent() == null); Comment
	 * comment2 = new Comment( "Ich liebe dieses Produkt!", 5, new Date(200001),
	 * "t"); List<Comment> commentList = new ArrayList<Comment>();
	 * commentList.add(comment); commentList.add(comment2);
	 * model.setComments(commentList); assertTrue("Kommentarliste gesetzt",
	 * model.getComments() == commentList); assertTrue("Kommentar 1 gesetzt",
	 * model.getComments().contains(comment)); assertTrue("Kommentar 2 gesetzt",
	 * model.getComments().contains(comment2)); model.removeCommentAt(1);
	 * assertTrue("Kommentar 2 geloescht", model.getComments().size() == 1 &&
	 * !model.getComments().contains(comment2) &&
	 * model.getComments().contains(comment)); model.removeCommentAt(0);
	 * assertTrue("Kommentar 1 geloescht", model.getComments().size() == 0 &&
	 * !model.getComments().contains(comment) &&
	 * !model.getComments().contains(comment2)); }
	 */
	@Test
	public void testgetImagefile() {
		String imagefile = "";
		Random random = new Random();
		String availableChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-_:;/()&?=\"!+*#äöüß$ "; // TODO:
																															// please
																															// review.
		for (int i = 1; i < 200; i++) {
			imagefile = "";
			for (int j = 0; j < i; j++) { // maximum image length is 200 (?) so
											// we are testing for all image
											// lengths between 0 and 200.
				imagefile += availableChars.charAt(random.nextInt(availableChars.length()));
			}
			model.setImagefile(imagefile);
			assertTrue("Imagefile gesetzt", model.getImagefile() == imagefile);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyImageFile() throws Exception {
		model.setImagefile("");
	}

	@Test
	public void testDescription() {
		String description = "";
		Random random = new Random();
		String availableChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-_:;/()&?=\"!+*#äöüß$ "; // TODO:
																															// please
																															// review.
		for (int i = 1; i < 2000; i++) {
			description = "";
			for (int j = 0; j < i; j++) { // maximum description length is 2000
											// (?) so we are testing for image
											// lengths between 0 and 2000.
				description += availableChars.charAt(random.nextInt(availableChars.length()));
			}
			model.setDescription(description);
			assertTrue("Description gesetzt", model.getDescription() == description);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyDescription() throws Exception {
		model.setDescription("");
	}

	@Test
	public void testWebLink() {
		String webLink = "";
		Random random = new Random();
		String availableChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-_:;/()&?=\"!+*#äöüß$ "; // TODO:
																															// please
																															// review.
		for (int i = 1; i < 2000; i++) {
			webLink = "";
			for (int j = 0; j < i; j++) { // maximum description length is 2000
											// (?) so we are testing for image
											// lengths between 0 and 2000.
				webLink += availableChars.charAt(random.nextInt(availableChars.length()));
			}
			model.setWebLink(webLink);
			assertTrue("Weblink gesetzt", model.getWebLink() == webLink);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyWebLink() throws Exception {
		model.setWebLink("");
	}

	@Test
	public void testCategory() {
		String category = "";
		Random random = new Random();
		String availableChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-_:;/()&?=\"!+*#äöüß$ "; // TODO:
																															// please
																															// review.
		for (int i = 1; i < 2000; i++) {
			category = "";
			for (int j = 0; j < i; j++) { // maximum description length is 2000
											// (?) so we are testing for image
											// lengths between 0 and 2000.
				category += availableChars.charAt(random.nextInt(availableChars.length()));
			}
			model.setCategory(category);
			assertTrue("Category gesetzt", model.getCategory() == category);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyCategory() throws Exception {
		model.setCategory("");
	}

	@Test
	public void testGetQuantity() {
		// TODO: Keine Ahnung, wie man das hier macht => Christoph fragen wegen
		// Spring Test Framework
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNameError() throws IllegalArgumentException {
		model = new ConcreteProduct("", Money.of(1.99, EURO), Money.of(1.49, EURO), "Tabakwaren",
				"Eine gute alte Zigarre", "www.tabak.ru", "zigarre.png");
	}

	@Test
	public void testSetBuyingPrice() {
		model.setBuyingPrice(Money.of(200.35, EURO));
		assertEquals("buyingPrice getter/setter don't seem to work.", model.getBuyingPrice(), Money.of(200.35, EURO));
	}
	@Test
	public void testGetComments()
	{
		Comment c = new Comment("Text",5,new Date(),"");
		ConcreteUserAccount acc = mock(ConcreteUserAccount.class);
		model.addComment(c, acc);
		assertTrue("GetComments defekt", model.getComments().iterator().next().equals(c));
	}
	@Test
	public void testAddComment()
	{
		Comment c = new Comment("Text", 5, new Date(), "");
		ConcreteUserAccount acc = mock(ConcreteUserAccount.class);
		model.addComment(c, acc);
		assertTrue("AddComment defekt", model.getComments().iterator().next().equals(c));
	}
	@Test
	public void testIsCommentator()
	{
		Comment c = new Comment("Text", 5, new Date(), "");
		ConcreteUserAccount acc1 = mock(ConcreteUserAccount.class);
		ConcreteUserAccount acc2 = mock(ConcreteUserAccount.class);
		model.addComment(c, acc1);
		assertTrue("IsCommentator defekt", model.isCommentator(acc1));
		assertFalse("IsCommentator defekt", model.isCommentator(acc2));
	}
	@Test
	public void testGetRating()
	{
		assertTrue("GetRating defekt", model.getRating()==null);
		Comment a = new Comment("Text", 5, new Date(), "");
		a.setAccepted(true);
		ConcreteUserAccount acc1 = mock(ConcreteUserAccount.class);
		model.addComment(a, acc1);
		assertTrue("GetRating defekt", model.getRating().iterator().hasNext()==true);
	}
	@Test
	public void testupdateAverageRating()
	{
		Comment a = new Comment("Text", 5, new Date(), "");
		Comment b = new Comment("Text", 3, new Date(), "");
		a.setAccepted(true);
		b.setAccepted(true);
		ConcreteUserAccount acc1 = mock(ConcreteUserAccount.class);
		ConcreteUserAccount acc2 = mock(ConcreteUserAccount.class);
		model.addComment(a, acc1);
		model.addComment(b, acc2);
		model.updateAverageRating();
		assertTrue("updateAverageRating defekt", model.getAverageRating()==4);
	}
	@Test
	public void testgetAverageRating()
	{
		Comment a = new Comment("Text", 5, new Date(), "");
		Comment b = new Comment("Text", 3, new Date(), "");
		a.setAccepted(true);
		b.setAccepted(true);
		ConcreteUserAccount acc1 = mock(ConcreteUserAccount.class);
		ConcreteUserAccount acc2 = mock(ConcreteUserAccount.class);
		model.addComment(a, acc1);
		model.addComment(b, acc2);
		model.updateAverageRating();
		assertTrue("getAverageRating defekt", model.getAverageRating()==4);
	}
	@Test
	public void testremoveComment()
	{
		Comment a = new Comment("Text", 5, new Date(), "");
		ConcreteUserAccount acc = mock(ConcreteUserAccount.class);
		model.addComment(a, acc);
		model.removeComment(a);
		assertTrue("removeComment defekt", model.getComments().iterator().hasNext()==false);
	}
	@Test
	public void testgetAcceptedComments()
	{
		Comment a = new Comment("Text", 5, new Date(), "");
		Comment b = new Comment("Text", 3, new Date(), "");
		a.setAccepted(true);
		b.setAccepted(true);
		ConcreteUserAccount acc1 = mock(ConcreteUserAccount.class);
		ConcreteUserAccount acc2 = mock(ConcreteUserAccount.class);
		model.addComment(a, acc1);
		model.addComment(b, acc2);
		assertTrue("getAcceptedComments defekt", model.getAcceptedComments().size()==2);
		assertTrue("getAcceptedComments defekt", model.getAcceptedComments().contains(a));
		assertTrue("getAcceptedComments defekt", model.getAcceptedComments().contains(b));
	}
	@Test
	public void testgetUnacceptedComments()
	{
		Comment a = new Comment("Text", 5, new Date(), "");
		Comment b = new Comment("Text", 3, new Date(), "");
		ConcreteUserAccount acc1 = mock(ConcreteUserAccount.class);
		ConcreteUserAccount acc2 = mock(ConcreteUserAccount.class);
		model.addComment(a, acc1);
		model.addComment(b, acc2);
		assertTrue("getUnacceptedComments defekt", model.getUnacceptedComments().size()==2);
		assertTrue("getUnacceptedComments defekt", model.getUnacceptedComments().contains(a));
		assertTrue("getUnacceptedComments defekt", model.getUnacceptedComments().contains(b));
	}
	@Test
	public void testgetBuyingPrice()
	{
		assertTrue("getBuyingPrice defekt", model.getBuyingPrice().isEqualTo(Money.of(1.49, EURO)));
	}
	@Test
	public void testgetPriceFloat()
	{
		assertTrue("getPriceFloat defekt ", model.getPriceFloat().equals("1,99€"));
	}
	@Test
	public void testincreaseSold()
	{
		model.increaseSold(10);
		assertTrue("increaseSold defekt", model.getSold()==10);
	}
	@Test
	public void testgetSold()
	{
		assertTrue("getSold defekt", model.getSold()==0);
	}
}
