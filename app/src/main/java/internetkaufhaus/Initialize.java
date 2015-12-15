package internetkaufhaus;

import static org.salespointframework.core.Currencies.EURO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Catalog;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.Cart;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Component;

import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.entities.ConcreteProduct;
import internetkaufhaus.entities.ConcreteUserAccount;
import internetkaufhaus.model.AccountAdministration;
import internetkaufhaus.model.Search;
import internetkaufhaus.repositories.ConcreteOrderRepository;
import internetkaufhaus.repositories.ConcreteProductRepository;
import internetkaufhaus.repositories.ConcreteUserAccountRepository;

@Component
public class Initialize implements DataInitializer {
	private final ConcreteUserAccountRepository ConcreteUserAccountManager;
	private final UserAccountManager userAccountManager;
	private final Inventory<InventoryItem> inventory;
	private final Catalog<ConcreteProduct> productCatalog;
	private final OrderManager<Order> orderManager;
	private final AccountAdministration accountAdministration;
	private final MailSender sender;
	private final ConcreteProductRepository concreteProductRepository;
	private final ConcreteOrderRepository concreteOrderRepo;
	private final Search productSearch;


	@Autowired
	public Initialize(ConcreteOrderRepository concreteOrderRepo,Catalog<ConcreteProduct> productCatalog, UserAccountManager userAccountManager, ConcreteUserAccountRepository ConcreteUserAccountManager, Inventory<InventoryItem> inventory, OrderManager<Order> orderManager, Search productSearch, AccountAdministration accountAdministration, MailSender sender, ConcreteProductRepository concreteProductRepository) {
		this.inventory = inventory;
		this.ConcreteUserAccountManager = ConcreteUserAccountManager;
		this.userAccountManager = userAccountManager;
		this.productCatalog = productCatalog;
		this.productSearch = productSearch;
		this.productSearch.setCatalog(productCatalog);
		this.orderManager = orderManager;
		this.concreteProductRepository = concreteProductRepository;
		this.sender = sender;
		this.accountAdministration = accountAdministration;
		this.accountAdministration.setUserAccountManager(this.userAccountManager);
		this.accountAdministration.setConcreteUserAccountManager(this.ConcreteUserAccountManager);
		this.accountAdministration.setMailSender(this.sender);
		this.concreteOrderRepo = concreteOrderRepo;
	}

	@Override
	public void initialize() {
		// fill the user database
		initializeUsers(userAccountManager, ConcreteUserAccountManager);
		// fill the Catalog with Items
		initializeCatalog(productCatalog, inventory, productSearch);
		// fill inventory with Inventory items
		// Inventory Items consist of one ConcreteProduct and a number
		// representing the stock
		initializeInventory(productCatalog, inventory);
		initializeOrders(concreteOrderRepo ,concreteProductRepository, orderManager, ConcreteUserAccountManager);

	}

	private void initializeCatalog(Catalog<ConcreteProduct> productCatalog, Inventory<InventoryItem> inventory, Search productSearch) {
		// prevents the Initializer to run in case of data persistance
		if (productCatalog.count() > 0) {
			return;
		}
		ConcreteProduct p1 = new ConcreteProduct("Delikatesse 1", Money.of(0.99, EURO), "Delikatessen", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz", "delikatessen.png");
		ConcreteProduct p2 = new ConcreteProduct("Delikatesse 2", Money.of(0.99, EURO), "Delikatessen", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz", "delikatessen.png");
		ConcreteProduct p3 = new ConcreteProduct("Delikatesse 3", Money.of(0.99, EURO), "Delikatessen", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz", "delikatessen.png");

		ConcreteProduct p4 = new ConcreteProduct("Wein 1", Money.of(0.99, EURO), "Wein und Gourmet", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz", "wein.jpg");
		ConcreteProduct p5 = new ConcreteProduct("Wein 2", Money.of(0.99, EURO), "Wein und Gourmet", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz", "wein.jpg");
		ConcreteProduct p6 = new ConcreteProduct("Wein 3", Money.of(0.99, EURO), "Wein und Gourmet", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz", "wein.jpg");

		ConcreteProduct p7 = new ConcreteProduct("Zigarre 1", Money.of(0.99, EURO), "Tabakwaren", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz", "zagarre.jpg");
		ConcreteProduct p8 = new ConcreteProduct("Zigarre 2", Money.of(0.99, EURO), "Tabakwaren", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz", "zagarre.jpg");
		ConcreteProduct p9 = new ConcreteProduct("Zigarre 3", Money.of(0.99, EURO), "Tabakwaren", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz", "zagarre.jpg");
		ConcreteProduct p10 = new ConcreteProduct("Zigarre 4", Money.of(0.99, EURO), "Tabakwaren", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz", "zagarre.jpg");
		ConcreteProduct p11 = new ConcreteProduct("Zigarre 5", Money.of(0.99, EURO), "Tabakwaren", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz", "zagarre.jpg");
		
		
		
		/*
		 * Comment p= new Comment( "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren" ,4, new Date(),"");
		 * 
		 * p11.addreviewedComments(p); p3.addreviewedComments(p); p6.addreviewedComments(p); p1.addreviewedComments(p); p9.addreviewedComments(p); p5.addreviewedComments(p);
		 */

		List<ConcreteProduct> prods = new ArrayList<ConcreteProduct>(Arrays.asList( p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
		
		prods.add(new ConcreteProduct("CIMAROSA Sauvignon Blanc Marlborough Estate Selection, Weißwein 2014", Money.of(5.49, EURO), "Wein", "„Nahezu mühelos produziert Neuseeland helle, aromatische Sauvignons, die eine Geschmackstiefe erreichen, ohne dieselbe Heftigkeit am Gaumen zu zeigen, die diese beliebte Rebsorte oft charakterisiert. Frisch und nicht zu trocken ist dieser Wein leicht zu trinken – alleine oder zu Salaten, Fisch und weißem Fleisch.\" RICHARD BAMPFIELD.", "https://eng.wikipedia.org/wiki/Fuzz", "cimarosa-sauvignon-blanc-marlborough-estate-selection-weisswein-2014.jpg"));
		prods.add(new ConcreteProduct("Allini Pinot Chardonnay, Schaumwein", Money.of(2.49, EURO), "Wein", "Mit diesem prickelnd sanften Spumante, von Pinot & Chardonnay steht dem endlosen Trinkgenuss nichts mehr im Wege. Zart strohgelb im Glas, mit einer Aromatik von Marille und weißer Mandelblüte, einer belebenden Säure und einem spritzig fruchtigen Mundgefühl, schmeckt dieser prickelnde Gefährte hervorragend gut gekühlt bei 8-10°C als Aperitif oder zu knackigen Salaten.", "https://eng.wikipedia.org/wiki/Fuzz", "allini-pinot-chardonnay-schaumwein--1.jpg"));
		prods.add(new ConcreteProduct("Agostón Cariñena D.O. Joven, Roséwein 2014", Money.of(3.49, EURO), "Wein", "Frisch und jung im Edelstahltank ausgebaut, um seine aromatische Rebsortenfrucht und Frische zu bewahren. Das fruchtbetonte Cuvée aus den Rebsorten Garnacha und Cabernet Sauvignon wurde als Rosé vinifiziert und zeigt Aromen von Cassis, Himbeeren, Kirschen, Erdbeeren, Rosen und Veilchen. Am Gaumen frisch, mittelgewichtig, ausgewogen, feine Säure, viel Frucht, enormer Charme und ein schöner Nachhall.", "https://eng.wikipedia.org/wiki/Fuzz", "agoston-carinena-d-o-joven-rosewein-2014.jpg"));
		prods.add(new ConcreteProduct("Banyuls AOP Baucéant, Rotwein 2008", Money.of(3.99, EURO), "Wein", "Aus dem berühmten Süßweingebiet am Mittelmeer, am Fuße der Pyrenäen, stammt dieser dichte, schmelzige Wein mit intensiven Aromen von Feigen, kandierten Orangen und Blockmalz mit einem Hauch von gerösteten Kakaobohnen.", "https://eng.wikipedia.org/wiki/Fuzz", "banyuls-aop-bauceant-rotwein-2008--1.jpg"));
		prods.add(new ConcreteProduct("Barceliño Catalunya DO Barrica, Rotwein 2013", Money.of(3.99, EURO), "Wein", "Hier ist der ideale Rotwein für alle, die es nicht zu trocken oder zu schwer mögen. Durch sorgfältiges Reifen in Eiche ist er voller Geschmack und abgerundet, leicht zu trinken und gut mit oder ohne Essen.", "https://eng.wikipedia.org/wiki/Fuzz", "barcelino-catalunya-do-barrica-rotwein-2013.jpg"));
		prods.add(new ConcreteProduct("Alliance Vin Mousseux, Schaumwein ", Money.of(3.99, EURO), "Wein", "Blass Grüngelb in der Farbe, lädt das sanfte Prickeln dieses Vin Mousseux Brut aus Frankreich zum verkosten ein. Lebendig erfrischend wirkt er durch das zarte Mousse. In der Nase betören Aromen von weißer Grapefruit und grünem Apfel neben einer mineralisch, erdigen Note. Gut gekühlt serviert bei einer Serviertemperatur von 8-10°C ist dies ihr perfekter Aperitif oder Begleiter zu knackig, würzigen Salaten.", "https://eng.wikipedia.org/wiki/Fuzz", "alliance-vin-mousseux-schaumwein-brut.jpg"));
		prods.add(new ConcreteProduct("5 Oros Rioja D.O.Ca Joven, Rotwein 2014", Money.of(3.99, EURO), "Wein", "In kräftigem Purpurrot präsentiert sich dieser Rioja Joven. In der Nase fruchtige Töne von Heidelbeere und Brombeere zeigend, ist er am Gaumen rund und reif, mit mittlerem Körper und einem langanhaltenden würzigen Abgang. Hervorragend eignet er sich zu diversen Ragouts und würzigem Käse. Genießen Sie ihn bei seiner optimalen Temperatur von 16°C.", "https://eng.wikipedia.org/wiki/Fuzz", "5-oros-rioja-d-o-ca-joven-rotwein-2014--1.jpg"));
		prods.add(new ConcreteProduct("Cà de Monaci Salice Salentino DOC, Rotwein 2010", Money.of(4.99, EURO), "Wein", "Traditionell in großen Holzfässern gereift, kombiniert dieser Wein seine Noten nach Leder und Kaffee mit überraschend erfrischenden Säuren nach Kirsche und Zwetschgen. Er hat angenehme, gut integrierte Tannine, die ihn zum guten Begleiter für Fleischgerichte macht.", "https://eng.wikipedia.org/wiki/Fuzz", "ca-de-monaci-salice-salentino-doc-rotwein-2010--2.jpg"));
		prods.add(new ConcreteProduct("Canforrales Jumilla DOC, Weißwein 2014", Money.of(4.99, EURO), "Wein", "In mittlerem Zitronengelb zeigt sich dieser Wein. In der Nase zeigt er eine ganz besondere Würze von Sternfrucht und Physalis und weißem Pfeffer. Viskos und mit guter Textur versehen, belebt die reife Säure und Töne von frischen Walnüssen diesen wahren Gastronomiewein. Bei 10-12°C genossen, ist er hervorragender Begleiter von Gerichten mit weißem Fleisch.", "https://eng.wikipedia.org/wiki/Fuzz", "canforrales-jumilla-doc-weisswein-2014--1 (1).jpg"));
		prods.add(new ConcreteProduct("CAÑO Toro do Vino de Autor DO, Rotwein 2011", Money.of(5.49, EURO), "Wein", "Dieser Wein strebt die perfekte Ausgewogenheit zwischen der Fruchtigkeit eines jungen Weines und der Eleganz, wie sie durch eine Reifung in Eichenholz erzielt wird, an. Von der Farbe her präsentiert er sich dunkelkirschrot, mit purpurnen Facetten.", "https://eng.wikipedia.org/wiki/Fuzz", "cano-toro-do-vino-de-autor-do-rotwein-2011--2.jpg"));
		prods.add(new ConcreteProduct("Marquis de Plagne Crémant de Limoux AOP, Schaumwein", Money.of(5.99, EURO), "Wein", "Wer’s herb und knackig frisch mag: für denjenigen ist dieser Crémant de Limoux die passende Alternative zu Champagner oder Cava. Dieser Schaumwein, eine Cuvée von den Rebsorten Chardonnay, Chenin & Mauzac zeigt ein fantastisch feines Mousse und präsentiert sich in blassem Zitronengelb im Glas. Die Aromatik ist geprägt von dezenten Noten von weißer Johannisbeere und hefigen Aromen von Brioche. Mineralisch wirkt er am Gaumen mit einer sehr erfrischenden und präsenten Säure und einem sanften Körper. Besonders vielschichtig ist dieser Schaumwein und weist einen besonders langen Abgang auf. Gut lässt er sich noch bis zu 5 Jahre lagern und gewinnt dabei noch mehr an Komplexität. Genießen Sie ihn bei 8°C als Aperitif, zu Spargel oder Krustentieren.", "https://eng.wikipedia.org/wiki/Fuzz", "marquis-de-plagne-cremant-de-limoux-aop-schaumwein-brut--2.jpg"));
		prods.add(new ConcreteProduct("Sella&Mosca Vermentino di Sardegna, Weißwein 2014", Money.of(5.99, EURO), "Wein", "Wahre Schätze sind die Weine Sardiniens. Das warme Klima mit den zahlreichen Sonnenscheinstunden und die Lage nah zum Meer, sorgt für ausgezeichnete Bedingungen für den Weinbau. Da die Weine dieser Insel Hauptsächlich im Land konsumiert werden, kennt sie außerhalb kaum jemand. Schade eigentlich, wenn man diesen Vertreter hier verkostet: Elegant in blassem Grüngelb schimmernd, präsentiert er sich im Glas mit einem intensiv würzig herben Aroma. Fast schon salzig im Charakter, mit einer besonderen Mineralität, die auch am Gaumen spürbar wird, erscheinen Töne von weißer Cassis, Stachelbeere und Brennnessel. Gut gekühlt bei einer Serviertemperatur von 8-10°C ist dieser Wein sehr vielschichtig kombinierbar, egal ob zu fruchtigen Salaten, Meeresfrüchten oder Huhn.", "https://eng.wikipedia.org/wiki/Fuzz", "sella-mosca-vermentino-di-sardegna-weisswein-2014.jpg"));
		prods.add(new ConcreteProduct("Chandesais Chablis AOP, Weißwein 2011", Money.of(7.99, EURO), "Wein", "Das nördlichste Weinbaugebiet des Burgund, das Chablis, ist bekannt für seinen ganz eigenen Weinstil aus der Chardonnaytraube. Hier präsentiert sich ein charmantes Exemplar mit den typischen Aromen von gelben Äpfeln und Citrusfrüchten und der feinen mineralischen Note.", "https://eng.wikipedia.org/wiki/Fuzz", "chandesais-chablis-aop-weisswein-2011--1.jpg"));
		prods.add(new ConcreteProduct("NIGRA Riesling trocken, Deutscher Jahrgangs-/Winzersekt 2013", Money.of(8.49, EURO), "Wein", "Ein herzhafter Sekt mit exotischen Fruchtaromen, die von einer dezenten Süße begleitet werden. Dardurch entsteht eine große geschmackliche Harmonie. Traditionelle Flaschengärung.", "https://eng.wikipedia.org/wiki/Fuzz", "nigra-riesling-trocken-deutscher-jahrgangs-winzersekt-2013--2.jpg"));
		prods.add(new ConcreteProduct("Carpe Diem Málaga D.O., Rotwein", Money.of(8.99, EURO), "Wein", "Ein besonderer Dessertwein aus der Region Malaga. Traditionellerweise werden die Trauben der roten Rebsorte Pedro Ximenez in der Sonne getrockent und danach gepresst und vinifiziert. Dieser Vorgang macht diesen Wein so besonders und gibt ihm die honigähnlichen Nuancen und Viskosität. Nach dreijähriger Reifung in amerikanischen Eichenfässern zeigt er nun ausgeprägte süße Nuss- und Karamellnoten, die sich vollmundig und schmelzig präsentieren. Er passt hervorragend zu Desserts oder zu einer vielfältigen Käseplatte.", "https://eng.wikipedia.org/wiki/Fuzz", "carpe-diem-malaga-d-o-rotwein.jpg"));
		prods.add(new ConcreteProduct("Château D'Arcole Saint-Emilion Grand Cru AOP, Rotwein 2011", Money.of(15.99, EURO), "Wein", "\"Guter, jugendfrischer Saint Emilion mit Kirsch- und Pflaumenaromen, unterstützt von einer schokoladigen Eichennote. Mit viel Geschmack und Frische am Abgang, kann er bereits jetzt mit Genuss getrunken werden und wird sich im nächsten und kommenden Jahr noch verbessern.\" RICHARD BAMPFIELD.", "https://eng.wikipedia.org/wiki/Fuzz", "chateau-d-arcole-saint-emilion-grand-cru-aop-rotwein-2011.jpg"));
		prods.add(new ConcreteProduct("VIAJERO Casato dei Medici Riccardi Bolgheri DOC, Rotwein 2012", Money.of(11.99, EURO), "Wein", "\"Bolgheri ist eine neuere und modische Weinregion an der toskanischen Mittelmeerküste. Aus den klassischen Bordeauxsorten gekeltert, und davon Cabernet Sauvignon im Hauptanteil, ist das hier ein Rotwein, der meiner Meinung nach eher nach Bordeaux als nach Italien schmeckt. Mit hellen Aromen schwarzer Früchte und guter Reife am Gaumen wird der Wein grossen Trinkspass in den kommenden 2 bis 3 jarhen bieten.\" Richard Bampfield.", "https://eng.wikipedia.org/wiki/Fuzz", "viajero-casato-dei-medici-riccardi-bolgheri-doc-rotwein-2012.jpg"));
		prods.add(new ConcreteProduct("VIAJERO Casato dei Medici Riccardi Sangiovese Shiraz IGT, Rotwein 2010", Money.of(15.99, EURO), "Wein", "\"Shiraz ist dafür bekannt, in ganz vollmundige Rotweine gekeltert zu werden. In diesem Falle verleiht er den sanfteren Noten des Sangiovese mehr Geschmack und Würze. Mit den Röstaromen der Eiche und seinem wärmenden Wesen haben wir hier einen wirklich vollmundigen Rotwein, der am besten zum Essen serviert wird.\" RICHARD BAMPFIELD.", "https://eng.wikipedia.org/wiki/Fuzz", "viajero-casato-dei-medici-riccardi-sangiovese-shiraz-igt-rotwein-2010.jpg"));
		prods.add(new ConcreteProduct("Riesling Mosel QbA, Weißwein 2014", Money.of(3.29, EURO), "Wein", "Dieser feinherbe Riesling begeistert durch seine Eleganz und erinnert an Pfirsich- und Aprikosenaromen. Ein idealer Begleiter zur Brotzeit, frischem Salat und Geflügel.", "https://eng.wikipedia.org/wiki/Fuzz", "riesling-mosel-qba-weisswein-2014--1.jpg"));
		prods.add(new ConcreteProduct("Domaine de Gagnebert Cabernet d'Anjou AOP, Roséwein 2013", Money.of(3.99, EURO), "Wein", "Der Roséklassiker von der Loire überrascht nebst seinen zarten Beerenaromen mit dem würzigen Duft nach Liebstöckel und Majoran. Er ist weich am Gaumen und hat einen verspielten Abgang, der Lust auf mehr macht.", "https://eng.wikipedia.org/wiki/Fuzz", "domaine-de-gagnebert-cabernet-d-anjou-aop-rosewein-2013--1.jpg"));
		
		productCatalog.save(prods);
		concreteProductRepository.save(prods);

		productSearch.addProds(productCatalog.findAll());
	}

	private void initializeInventory(Catalog<ConcreteProduct> productCatalog, Inventory<InventoryItem> inventory) {
		// prevents the Initializer to run in case of data persistance
		for (ConcreteProduct prod : productCatalog.findAll()) {
			InventoryItem inventoryItem = new InventoryItem(prod, Quantity.of(50));
			inventory.save(inventoryItem);
		}
	}

	private void initializeUsers(UserAccountManager userAccountManager, ConcreteUserAccountRepository ConcreteUserAccountManager) {
		// prevents the Initializer to run in case of data persistance
		if (userAccountManager.findByUsername("peon").isPresent()) {
			return;
		}

		final Role adminRole = Role.of("ROLE_ADMIN");
		final Role customerRole = Role.of("ROLE_CUSTOMER");
		final Role employeeRole = Role.of("ROLE_EMPLOYEE");

		

		List<ConcreteUserAccount> userAccounts = new ArrayList<ConcreteUserAccount>();
		userAccounts.add(new ConcreteUserAccount("peon", "peon", adminRole, userAccountManager));
		userAccounts.add(new ConcreteUserAccount("saul", "saul", employeeRole, userAccountManager));
		userAccounts.add(new ConcreteUserAccount("adminBehrens@todesstern.ru", "admin", "admin", "Behrens", "Musterstraße", "01069", "Definitiv nicht Dresden", "admin", customerRole, userAccountManager));
		userAccounts.add(new ConcreteUserAccount("behrens_lars@gmx.de", "lars", "Lars", "Behrens", "Musterstraße", "01069", "Definitiv nicht Dresden", "lars", customerRole, userAccountManager));
		


//		100 weitere ConcreteUserAccounts; auskommentiert aus Zeitgründen
//		
//		int i = 0;
//		for(i=0;i<100;i++)
//		{
//			userAccounts.add(new ConcreteUserAccount("kunde"+i+"@todesstern.ru", "kunde"+i, "Kunde"+i, "Kundenname"+i, "Kundenstraße"+i, "12345", "Definitiv nicht Dresden", "kunde"+i, customerRole, userAccountManager,"lars", i));
//		}
		
	
		

		/*
		 * RegistrationForm reg = new RegistrationForm(); reg.setEmail("behrens_lars@gmx.de"); reg.setName("peons"); reg.setPassword("asdf"); reg.setPasswordrepeat("asdf");
		 * 
		 */

		for (ConcreteUserAccount acc : userAccounts) {
			userAccountManager.save(acc.getUserAccount());
			ConcreteUserAccountManager.save(acc);
				
				}
		
		ConcreteUserAccountManager.findByUserAccount(userAccountManager.findByUsername("lars").get()).setRecruits(ConcreteUserAccountManager.findByEmail("adminBehrens@todesstern.ru"));
		ConcreteUserAccountManager.findByUserAccount(userAccountManager.findByUsername("admin").get()).setRecruits(ConcreteUserAccountManager.findByEmail("behrens_lars@gmx.de"));
	
	}

		
		


	

	private void initializeOrders(ConcreteOrderRepository concreteOrderRepo, ConcreteProductRepository prods, OrderManager<Order> orderManager, ConcreteUserAccountRepository ConcreteUserAccountManager) {
		
		Cart c = new Cart();
		for (ConcreteProduct p : prods.findAll()) {
			c.addOrUpdateItem(p, Quantity.of(1));
		}
		for (ConcreteUserAccount u : ConcreteUserAccountManager.findByRole(Role.of("ROLE_CUSTOMER"))) {
		

   			ConcreteOrder order = new ConcreteOrder(u.getUserAccount(), Cash.CASH);
			c.addItemsTo(order.getOrder());

            Order o = order.getOrder();
			c.addItemsTo(o);

			order.setBillingGender("Herr");
			order.setBillingFirstName(u.getUserAccount().getFirstname());
			order.setBillingLastName(u.getUserAccount().getLastname());
			order.setBillingStreet(u.getAddress());
			order.setBillingHouseNumber("2");
			order.setBillingTown(u.getCity());
			order.setBillingZipCode(u.getZipCode());
			
			order.setShippingGender("Herr");
			order.setShippingFirstName(u.getUserAccount().getFirstname());
			order.setShippingLastName(u.getUserAccount().getLastname());
			order.setShippingStreet(u.getAddress());
			order.setShippingHouseNumber("2");
			order.setShippingTown(u.getCity());
			order.setShippingZipCode(u.getZipCode());
			
			orderManager.payOrder(o);// only set orderManager.payOrder(o), do not use orderManager.completeOrder(0), to complete Order look at the next line!
			order.setStatus(OrderStatus.COMPLETED); //to complete Order do not use orderManager.completeOrder
            order.setDateOrdered(LocalDateTime.now().minusDays(31));
        	concreteOrderRepo.save(order);
    		orderManager.save(o);
           


		}

		c.clear();

	}
}
