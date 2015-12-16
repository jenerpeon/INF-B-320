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
		
		/*
		 * Comment p= new Comment( "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren" ,4, new Date(),"");
		 * 
		 * p11.addreviewedComments(p); p3.addreviewedComments(p); p6.addreviewedComments(p); p1.addreviewedComments(p); p9.addreviewedComments(p); p5.addreviewedComments(p);
		 */

		List<ConcreteProduct> prods = new ArrayList<ConcreteProduct>();
		
		prods.add(new ConcreteProduct("CIMAROSA Sauvignon Blanc Marlborough Estate Selection, Weißwein 2014", Money.of(5.49, EURO), "Wein", "„Nahezu mühelos produziert Neuseeland helle, aromatische Sauvignons, die eine Geschmackstiefe erreichen, ohne dieselbe Heftigkeit am Gaumen zu zeigen, die diese beliebte Rebsorte oft charakterisiert. Frisch und nicht zu trocken ist dieser Wein leicht zu trinken – alleine oder zu Salaten, Fisch und weißem Fleisch.\" RICHARD BAMPFIELD.", "https://eng.wikipedia.org/wiki/Fuzz", "cimarosa-sauvignon-blanc-marlborough-estate-selection-weisswein-2014.jpg"));
		prods.add(new ConcreteProduct("Allini Pinot Chardonnay, Schaumwein", Money.of(2.49, EURO), "Wein", "Mit diesem prickelnd sanften Spumante, von Pinot & Chardonnay steht dem endlosen Trinkgenuss nichts mehr im Wege. Zart strohgelb im Glas, mit einer Aromatik von Marille und weißer Mandelblüte, einer belebenden Säure und einem spritzig fruchtigen Mundgefühl, schmeckt dieser prickelnde Gefährte hervorragend gut gekühlt bei 8-10°C als Aperitif oder zu knackigen Salaten.", "https://eng.wikipedia.org/wiki/Fuzz", "allini-pinot-chardonnay-schaumwein--1.jpg"));
		prods.add(new ConcreteProduct("Agostón Cariñena D.O. Joven, Roséwein 2014", Money.of(3.49, EURO), "Wein", "Frisch und jung im Edelstahltank ausgebaut, um seine aromatische Rebsortenfrucht und Frische zu bewahren. Das fruchtbetonte Cuvée aus den Rebsorten Garnacha und Cabernet Sauvignon wurde als Rosé vinifiziert und zeigt Aromen von Cassis, Himbeeren, Kirschen, Erdbeeren, Rosen und Veilchen. Am Gaumen frisch, mittelgewichtig, ausgewogen, feine Säure, viel Frucht, enormer Charme und ein schöner Nachhall.", "https://eng.wikipedia.org/wiki/Fuzz", "agoston-carinena-d-o-joven-rosewein-2014.jpg"));
		prods.add(new ConcreteProduct("Banyuls AOP Baucéant, Rotwein 2008", Money.of(3.99, EURO), "Wein", "Aus dem berühmten Süßweingebiet am Mittelmeer, am Fuße der Pyrenäen, stammt dieser dichte, schmelzige Wein mit intensiven Aromen von Feigen, kandierten Orangen und Blockmalz mit einem Hauch von gerösteten Kakaobohnen.", "https://eng.wikipedia.org/wiki/Fuzz", "banyuls-aop-bauceant-rotwein-2008--1.jpg"));
		prods.add(new ConcreteProduct("Barceliño Catalunya DO Barrica, Rotwein 2013", Money.of(3.99, EURO), "Wein", "Hier ist der ideale Rotwein für alle, die es nicht zu trocken oder zu schwer mögen. Durch sorgfältiges Reifen in Eiche ist er voller Geschmack und abgerundet, leicht zu trinken und gut mit oder ohne Essen.", "https://eng.wikipedia.org/wiki/Fuzz", "barcelino-catalunya-do-barrica-rotwein-2013.jpg"));
		prods.add(new ConcreteProduct("Alliance Vin Mousseux, Schaumwein ", Money.of(3.99, EURO), "Wein", "Blass Grüngelb in der Farbe, lädt das sanfte Prickeln dieses Vin Mousseux Brut aus Frankreich zum verkosten ein. Lebendig erfrischend wirkt er durch das zarte Mousse. In der Nase betören Aromen von weißer Grapefruit und grünem Apfel neben einer mineralisch, erdigen Note. Gut gekühlt serviert bei einer Serviertemperatur von 8-10°C ist dies ihr perfekter Aperitif oder Begleiter zu knackig, würzigen Salaten.", "https://eng.wikipedia.org/wiki/Fuzz", "alliance-vin-mousseux-schaumwein-brut.jpg"));
		prods.add(new ConcreteProduct("5 Oros Rioja D.O.Ca Joven, Rotwein 2014", Money.of(3.99, EURO), "Wein", "In kräftigem Purpurrot präsentiert sich dieser Rioja Joven. In der Nase fruchtige Töne von Heidelbeere und Brombeere zeigend, ist er am Gaumen rund und reif, mit mittlerem Körper und einem langanhaltenden würzigen Abgang. Hervorragend eignet er sich zu diversen Ragouts und würzigem Käse. Genießen Sie ihn bei seiner optimalen Temperatur von 16°C.", "https://eng.wikipedia.org/wiki/Fuzz", "5-oros-rioja-d-o-ca-joven-rotwein-2014--1.jpg"));
		prods.add(new ConcreteProduct("Cà de Monaci Salice Salentino DOC, Rotwein 2010", Money.of(4.99, EURO), "Wein", "Traditionell in großen Holzfässern gereift, kombiniert dieser Wein seine Noten nach Leder und Kaffee mit überraschend erfrischenden Säuren nach Kirsche und Zwetschgen. Er hat angenehme, gut integrierte Tannine, die ihn zum guten Begleiter für Fleischgerichte macht.", "https://eng.wikipedia.org/wiki/Fuzz", "ca-de-monaci-salice-salentino-doc-rotwein-2010--2.jpg"));
		prods.add(new ConcreteProduct("Canforrales Jumilla DOC, Weißwein 2014", Money.of(4.99, EURO), "Wein", "In mittlerem Zitronengelb zeigt sich dieser Wein. In der Nase zeigt er eine ganz besondere Würze von Sternfrucht und Physalis und weißem Pfeffer. Viskos und mit guter Textur versehen, belebt die reife Säure und Töne von frischen Walnüssen diesen wahren Gastronomiewein. Bei 10-12°C genossen, ist er hervorragender Begleiter von Gerichten mit weißem Fleisch.", "https://eng.wikipedia.org/wiki/Fuzz", "canforrales-jumilla-doc-weisswein-2014--1.jpg"));
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
		
		prods.add(new ConcreteProduct("Ron Botucal Reserva Exclusiva (ehem. Diplomatico) Premium Rum - 0,7L 40% vol ", Money.of(29.49, EURO), "Spirituosen", "Der Ron Botucal Reserva Exclusiva war eher unter dem Namen Diplomatico 12 Reserva Exclusiva bekannt. Er stammt aus Venezuela und ist eine Kombination aus unterschiedlichen Destillaten. Rund zwölf Jahre reift dieser Rum in Eichenholzfässern, bevor er in den recht massiven Flaschenkörper abgefüllt wird. Sein komplexer Duft erinnert an Fruchtkuchen, Zimt und Kakao. Im Mund setzen sich die fruchtigen Noten fort, außerdem feine Schokoladensoße, Karamell, Piment, Nüsse und außerdem Orange, die für eine angenehme Frische sorgt. Insgesamt ein reifer, ausgewogener Rum. Im Abgang ist er langanhaltend und süß. Er sollte unbedingt pur getrunken werden. Zum Mixen ist er wirklich zu schade. Der tiefgoldene, kupferfarbene Rum ist mehrfach ausgezeichnet worden. Sein Alkoholgehalt beträgt 40 %.", "https://eng.wikipedia.org/wiki/Fuzz", "Botucal_Reserva_Exclusiva_0-7_Liter_4298.jpg"));
		prods.add(new ConcreteProduct("Ron Zacapa 23 Solera Rum, Premium - 1 Liter 40% vol ", Money.of(49.69, EURO), "Spirituosen", "Dieser Rum ist in Guatemala zu Hause. Mehrmals wurde er mit der Goldmedaille in der Super Premium Kategorie geehrt, und beim internationalen Spirituosen-Wettbewerb erhielt er 2009 ebenfalls die Goldmedaille. Vor allem seine lange Reifezeit wird dafür verantwortlich gemacht, dass es sich um ein ganz besonderes Produkt mit hoher Qualität handelt. Ausgebrannte Bourbon-Whisky Eichenfässer werden dafür verwendet, die ihm eine besondere Note geben, und dann kommt er nochmals für eine kurze Zeit in spanische Sherryfässer, die ihm den letzten Schiff geben. Seine Farbe ist wie flüssiger Bernstein, sodass er optisch schon jeden Genießer anspricht. Süßlich dann Aroma und Geschmack, mit Karamell und Vanille sowie Mandel. Dazu kommen Nugat und Trockenfrüchte und ein Hauch Schokolade. Es folgt eine Spur von Honig, sodass sich der Rum vor allem an jene richtet, die das Süße in der Zuckerrohrspezialität suchen. Außerdem besticht der Ron Zacapa 23 Solera Rum durch milden Genuss. Guatemala ist bislang weniger für die Rumherstellung bekannt als andere Staaten der Karibik. Doch mit diesem Produkt dürfte es leicht fallen, sich gegen die Konkurrenz durchzusetzen. Der Hersteller ist Rum Creation and Products Inc. Hinter diesem Zusammenschluss guatemaltekischer Firmen verbergen sich Zuckerrohrproduzenten und Abfüller sowie Destillen. Der Rum Ron Zacapa 23 Solera Rum kam 1976 auf den Markt, zum hundertjährigen Bestehen des Ortes Zacapa. Der Weltspirituosenriese Diageo wurde auf das Sortiment aufmerksam und übernahm 2008 das Unternehmen sowie die Rechte an der Vermarktung und dem Vertrieb des Rums. Höchst ungewöhnlich ist, dass der Master Blender eine Frau ist. Sonst wird dieser Posten immer von Männern besetzt. Sie sorgt dafür, dass alle nur möglichen Risikofaktoren ausgeschaltet werden, die die Qualität des Rums beeinträchtigen könnten. Schon der Anbau des Zuckerrohrs wird sorgsam überwacht, das an der Südküste Guatemalas auf fruchtbaren Böden aus Vulkanasche wächst.", "https://eng.wikipedia.org/wiki/Fuzz", "Ron_Zacapa_1_Liter_2398.jpg"));
		prods.add(new ConcreteProduct("Monkey 47 Schwarzwald Dry Gin - 0,5L 47% vol ", Money.of(30.67, EURO), "Spirituosen", "Ein deutscher Gin aus dem Schwarzwald  das erinnert an Obstler und pfiffige Schwarzwälder Kirschwässerle. Doch der trockene Gin in seiner klaren und reinen Form ist ein regelrechter Premium-Schnaps unter den Wacholderbränden. Er beinhaltet fast 50 verschiedene Ingredienzien und kann gut pur und on the Rocks genossen werden, oder auch in hochwertigen Drinks seinen Platz finden. Kenner bezeichnen ihn als harmonisch und mit einem Aroma von Blumen, Frische, Holz und Zitrus. Natürlich schmeckt man den Wacholder und eine weitere Kräutermischung, die sich nicht genau zuordnen lässt. Dazu kommt der Geschmack von Floralem und von Frucht. Auszeichnungen hat der Gin aus dem Schwarzwald ebenfalls schon bekommen. Beim World Spirits Award gab es 2011 Gold. Und bei den International Wine and Spirits Competition war er der Klassenbeste in der Kategorie Gin Worldwide. Viele bekannte Gins kommen aus Großbritannien, wo der Gin eine lange Tradition hat. Doch die Ursprünge des Wacholderbrennens sind in Holland zu suchen. Und immer mehr andere Länder steuern dem Markt beachtliche Gins bei, wie Deutschland mit dem Monkey 47 Schwarzwald Dry Gin.Qualität Made in Germany ist hier noch etwas wert  mitten in Baden-Württemberg ist das Unternehmen ansässig, genauer in Durbach. Doch zugegeben: das Rezept stammt nicht aus der Gegend der Kuckucksuhren, sondern von dem Briten Montgomery Collins. Er kam nach Deutschland und lies sich in Berlin nieder. Doch weil er sich eben für die Uhren interessierte, zog es ihn in den Schwarzwald, wo er dann aber ein Gasthaus eröffnete. Es bekam den Namen The Wild Monkey, und war fortan der Platz, um selbst Gin herzustellen. Leider geriet dieser Gin in Vergessenheit, die Rezeptur wurde aber nach Jahren bei der Renovierung des Gebäudes gefunden. Zwei Einheimische, Christoph Keller und Alexander Stein nahmen sich dieses Fundes an und bringen den Gin seit 2008 erneut auf den Markt.", "https://eng.wikipedia.org/wiki/Fuzz", "Monkey_47_0-5_Liter_4235.jpg"));
		prods.add(new ConcreteProduct("Minttu Peppermint 50, Pfefferminz-Likör - 0,5L 50% vol", Money.of(8.95, EURO), "Spirituosen", "Aus dem hohen Norden kommt der Minttu Peppermint 50. Denn er wird in Finnland hergestellt. Die Brennerei Altia Corporation, Teil der Pernod-Ricard-Konzerns, ist vor allem wegen dieses Pfefferminzlikörs bekannt geworden. Heute exportiert man vor allem nach Schweden und Deutschland. Die Finnen haben ihn ohnehin ins Herz geschlossen, und da er von vielen wegen des erfrischenden Geschmacks gelobt wird, konnte er bald alle Freunde der Minze erobern. 1976 wurde er auf dem finnischen Markt vorgestellt und war damals der erste klare Pfefferminzlikör. Denn die meisten ihrer Art haben eine grüne Farbe, doch Minttu Peppermint 50 ist hell und klar wie ein Wodka. Dies wird durch einen speziellen Zucker erreicht. Die 50 im Namen steht für die Alkoholstärke. Es gibt eine weitere Variante mit 40 % Vol. Der 50er wird vorwiegend exportiert. Der Verzehr ist meist eisgekühlt favorisiert, doch im Winter wird er auch gern mal heiß probiert. Überdies schmeckt er hervorragend in Heißgetränke wie Kaffee oder Kakao und ebenso über Schokoladendesserts oder Eis. Seine frische, kühle und kräftige Art passt einfach zu vielem, und auch ein Mixen mit dem Minttu Peppermint 50 kann viele interessante Kreationen hervorbringen. Ein Highlight ist immer wieder Pfefferminzlikör mit Schokolade in jedweder Art und Weise. So schmeckt er besonders toll in heißer Schokolade oder auch in einem Schokoladeneisbecher. Für alle, die den minzig-kühlen Geschmack lieben, ist er ein Muss in der Hausbar. Sehr gut geeignet ist er beispielsweise für die Zubereitung des Grünen Cocktails. Dabei werden eine Scheibe Kiwi, ein Stängel frischer Minze und 1 cl Minttu Peppermint 50 gemixt und mit Sekt aufgefüllt. Er wird zwar mit dem hellen Pfeffi-Likör nicht gar so grün, schmeckt aber hervorragend.", "https://eng.wikipedia.org/wiki/Fuzz", "Minttu_0-5_Liter_8001.jpg"));
		prods.add(new ConcreteProduct("Plantation Barbados Extra Old 20th Anniversary Rum, Premium - 0,7L 40% vol ", Money.of(38.18, EURO), "Spirituosen", "Sehr alte Barbados Rums werden für den Plantation Barbados Extra Old 20th Anniversary miteinander verehelicht. Diese Rums reifen zunächst über mehrere Jahre in den Tropen in speziellen Bourbon-Fässern, bevor sie anschließend in Eichenfässer umgefüllt werden und in einer kühlen Kellerumgebung ihre letzte Reifung erhalten. Durch diese Verfahrensweise wird der Plantation Barbados Extra Old 20th Anniversary im Geschmack sehr weich und voll. Ein Blickfang ist auch seine mahagoniähnliche Farbe, die eine wunderbare Viskosität beinhaltet. Der Duft ist fruchtig mit Aromen von Aprikose, Banane und Vanille. Der Abgang ist durch die Tropenlagerung mit Nuancen von Kokosnuss versehen und bleibt besonders lange spürbar. Der Plantation Barbados Extra Old 20th Anniversary ist eine der interessantesten und abwechslungsreichsten Sorten auf dem Markt.", "https://eng.wikipedia.org/wiki/Fuzz", "Plantation_20th_Anniversary_0-7_Liter_6588.jpg"));
		prods.add(new ConcreteProduct("Pampero Aniversario Premium Rum - 0,7L 40% vol", Money.of(20.75, EURO), "Spirituosen", "Dieser Blended Rum aus Venezuela ist eine exklusive Jubiläumsedition. Er schmeckt pur und gemixt und sorgt für ein würziges und hochwertiges Trinkerlebnis. Er wird aus Melasse hergestellt, und besticht durch seine dunkle Bernstein-Farbe. Die Würze ist seine Überzeugungskraft. Das Aroma kann Karamell und Honig durchblicken lassen, auch wenn tropische Früchte und Gewürze vorherrschen. Die Zitrusfrucht ist auf alle Fälle dabei, und der bittere Hauch von Lakritze. Dazu kommt, dass er als sehr gut ausbalanciert gilt. 2007 bekam das Erzeugnis bei den San Francisco World Spirits Competition den Titel weltbester Rum. Außerdem beim internationalen Spirituosen-Wettbewerb 2013 Silber. Venezuela geht auch mit diesem Erzeugnis gegen die Konkurrenz aus der Karibik an. Denn mit der Zeit hat es der dortige Rum geschafft, sich einen Namen zu machen. Im Land ist Pampero Aniversario Premium Rum der Marktführer, was seine Qualität unterstreicht. Dass die venezolanischen Rumsorten von so hoher Güte sind, liegt wohl unter anderem daran, dass die Reifelagerung recht hoch über dem Meeresspiegel liegt. Die Destillerie Pampero S.A. ist der Hersteller dieses Rums. Gegründet hat sie 1938 Alejandro Hernándéz. In der Folge entstand eine Produktpalette vorzüglicher Rumsorten. In den 90er Jahren aber wurden fast alle Firmenanteile an die Firma United Distillers verkauft, und damit an Guinness Brewery. Inzwischen gehört das Unternehmen zu Diageo, wobei die Produktion wie gehabt weitergeht. Die Brennerei stellt noch andere Rumsorten her, wobei aber der Pampero Aniversario Premium Rum der berühmteste ist. Der Name hat seinen Ursprung in einem heftigen Wind, der oft über Venezuela hinwegfegt. Er ist für das dortige Klima ausschlaggebend und bringt sowohl Hitze als auch Regen und Trockenheit. Davon ist der Anbau des Zuckerrohrs abhängig, der für die Rumproduktion wichtig ist. Durch die hohen Temperaturen zu mancher Zeit wird außerdem die Reifezeit des Rums verkürzt.", "https://eng.wikipedia.org/wiki/Fuzz", "Ron_Pampero_Aniversario_0-7_Liter_2364.jpg"));
		prods.add(new ConcreteProduct("Canadian Club Whisky - 0,7L 40% vol", Money.of(10.59, EURO), "Spirituosen", "Canadian Club Whiskey ist der weltweit erfolgreichste Whiskey aus Kanada. Dieser Whiskey überzeugt wie viele Erzeugnisse aus dem Nordamerikanischen Staat durch seinen milden Geschmack. Doch trotz dieser Milde sind die vielfältigen Aromen des Canadian Club eine Freude für jeden Kenner. Seit 1858 wird diese Spirituose aus Kanada produziert und weißt damit im Vergleich zu anderen Nordamerikanischen Produkten eine große Tradition auf. Besonders in den USA wird der Canadian Club sehr geschätzt. Diese Beliebtheit hat seinen Ursprung Anfang des 20. Jahrhunderts, als ein generelles Alkoholverbot in den USA galt. Damals sind US-amerikanische Bürger ins Grenzgebiet nach Kanada gefahren, um sich dort illegeal mit Alkohol zu versorgen. Und wie man sich schon denken kann, war eines dieser geschmuggelten Güter der aromatische Canadian Club.", "https://eng.wikipedia.org/wiki/Fuzz", "Canadian_Club_0-7_Liter_2736.jpg"));
		prods.add(new ConcreteProduct("Russian Standard Vodka - 1 Liter 40% vol", Money.of(13.25, EURO), "Spirituosen", "Dieser traditionelle Wodka kommt aus dem schönen St. Petersburg und begeistert die Freunde guter Spirituosen weit über russische Landesgrenzen hinaus. Der Russian Standard Voda mit seinem Alkoholgehalt von 40% vol geht auf Dmitri Mendeleev zurück, der das perfekte Mischverhältnis ausgeklügelt hat, mit dem der beste Wodka gewonnen werden kann. Mit seiner kristallklaren Farbe und dem starken, ausgewogenen Geschmack mit einem Hauch von Getreide, der an gebackenes Brot erinnert, wird dieser ultrareine Wodka, der auch unter dem Namen Russky Standart bekannt ist, auch hierzulande immer beliebter. Perfekt für den puren Genuss, ''on the rocks'' und sogar zum Mixen perfekter Cocktails und Longdrinks.", "https://eng.wikipedia.org/wiki/Fuzz", "Russian_Standard_Vodka-F-3667.jpg"));
		prods.add(new ConcreteProduct("Benedictine DOM Kräuterlikör, Schnaps - 0,7L 40% vol", Money.of(17.31, EURO), "Spirituosen", "Direkt aus der Normandie in Frankreich stammt der traditionelle Benedictine DOM Kräuterlikör. Seine Rezeptur geht dabei auf das Jahr 1510 zurück. Seinen Bekanntheitsgrad erreichte dieser nach Gewürzen und Kräutern schmeckende, mit einer leichten honigartigen Süße versehene Likör aber erst dreihundert Jahre später. Im Jahre 1863 stieß der französische Kaufmann Alexandre Le Grand in ehemals geerbten Unterlagen auf eine geheime Formel dieses Likörs. Ihm ist es schließlich zu verdanken, dass der Benedictine DOM Kräuterlikör zur neuen Blühte erwachte. Der bernsteinfarbene Likör erfreut sich bis heute einer großen Beliebtheit. Das Wort DOM steht übrigens deo optimo maximo, was so viel bedeutet wie, dem größten und besten Gott geweiht.", "https://eng.wikipedia.org/wiki/Fuzz", "Benedictine_DOM_Kraeuterlikoer__Schnaps-1838.jpg"));
		prods.add(new ConcreteProduct("Gordons Dry Gin - 1 Liter 37,5% vol", Money.of(14.04, EURO), "Spirituosen", "Unter den englischen Gin-Marken ist der Gordon's Dry Gin der Klassiker. Im Jahre 1789 wurde die Destillerie von Alexander Gordon gegründet. Die Destillerie befand sich im Norden von London, weil dort die Wasserqualität am höchsten war. Gordon's Dry Gin ist ein reiner, farbloser Gin mit einem Bouquet aus Gewürzen, Früchten und Wacholder. Letzteres schmeckt man auch am Gaumen, außerdem Angelikawurzel, Orangen und Gewürze wie Koriander, Zitrusfrüchte und Zimt. Der Geschmack ist frisch und ausgewogen und lässt erahnen, warum dieser Gin weltweit so bekannt und beliebt ist. Auch im Finish wirken die Gewürze lange nach. Den Gordon's Dry Gin trinkt man am besten auf Eis oder pur, aber auch für Mixgetränke ist er gut geeignet. Legendär ist der ''Gin-Tonic'' mit Tonic Water. Der Gin hat einen Alkoholgehalt von milden 37,5 %.", "https://eng.wikipedia.org/wiki/Fuzz", "Gordons_1_Liter_1494.jpg"));
		prods.add(new ConcreteProduct("London No. 1 Original Blue Gin - 0,7L 47% vol", Money.of(30.81, EURO), "Spirituosen", "Der London Gin No. 1 Original Blue Gin wird mit dem reinen Quellwasser der Clekenwell-Quellen, im Norden Londons, hergestellt. Eine weitere Zutat sind erstklassiges englisches Getreide aus Norfolk und Suffolk. Viel Wert legen die Hersteller auch auf einen gewissenhaften Destillationsprozess. Eine Besonderheit ist die Auswahl der Kräuter. Für gewöhnlich werden rund acht Kräuter für London Gin verwendet. Für den Original Blue Gin wählte man jedoch zwölf Gewürze und Kräuter, die überdies aus verschiedenen Ländern eingeführt werden. Angelikawurzel und Bohnenkraut stammen beispielsweise aus den französischen Alpen, die Bergamotte aus Bergamo und die Mandeln aus Griechenland. Die leicht süßliche Lakritze ist türkischer Natur, die Iriswurzeln, Zitronen- und Orangenschalen werden aus Italien importiert, der Koriander stammt aus Marokko und die schwarzen Johannisbeeren aus dem fernen Hongkong. Die wichtigste Zutat des Gins, wie bei allen Gins, ist der Wacholder. Dieser kommt aus den Bergen Dalmatiens ins hippe London. Die strahlende, aquamarinblaue Farbe erzeugt man mit Gardenien-Extrakt. Kein Wunder, dass der Geschmack des Blue Gins ein besonderer ist. Er schmeckt vollmundig und trocken und hinterlässt ein angenehmes Gefühl auf der Zunge. Dieser Gin hat Kultcharakter und sollte in keiner Hausbar fehlen. Mit seiner attraktiven, puristischen Flaschenform eignet er sich auch bestens als Geschenk für gute Freunde.", "https://eng.wikipedia.org/wiki/Fuzz", "London_No-_1_0-7_Liter_7226.jpg"));
		prods.add(new ConcreteProduct("J & B Whisky Scotch Whisky - 0,7L 40% vol", Money.of(12.41, EURO), "Spirituosen", "J & B Rare Whisky ist ein Blended Scotch Whisky, der aus nicht weniger als 42 verschiedenen Whiskys besteht. Dabei ist gerade die spezielle Zusammensetzung für seinen weichen und außergewöhnlichen Charakter verantwortlich. Würde der Hersteller nur einen der 42 Whiskys weglassen, würde sich das Aroma des J & B Rare Whisky komplett verändern. Das leichte, fruchtige Aroma stammt von den schottischen Speyside Malt Whiskys, die das weiche Herz des J & B Rare formen. Der bernsteinfarbene J & B Rare Whisky wird wahlweise pur, auf Eis oder mit Cola getrunken. Pur entwickelt J & B Rare einen fruchtig milden Geschmack, der weich und leicht süß an Mandeln erinnert. J & B Rare Whisky ist ein Produkt des Whiskyherstellers Justerini & Brooks, der im Jahr 1749 unter dem Namen Johnson & Justerini in London gegründet wurde. Heute gehört das Unternehmen zum Diageo Getränkekonzern.", "https://eng.wikipedia.org/wiki/Fuzz", "J_-_B_0-7_Liter_2898.jpg"));
		prods.add(new ConcreteProduct("Strega Likör Kräuterlikör - 0,7L 40% vol", Money.of(15.49, EURO), "Spirituosen", "Mit einem Strega Liquore erwartet Sie der berühmteste Kräuterlikör Italiens. Nach einem alten Rezept, welches von Generation zu Generation weitergegeben wird, vereint sich hier die Kunst der Likörherstellung. Sein Rezept beinhaltet über 70 verschiedene Kräuter aus aller Herren Länder. Dazu gehören unter anderem Zimt, Pfeffer, Wacholder und Minze. Alle Kräuter werden gemahlen und in einem Getreideaufguss eingelegt. Nach einer langen traditionellen Destillation entsteht durch Zugabe einer Sirups ein Destillat von 40,0 % vol. Zusätzlich wird Safran zugegeben, dadurch erhält der Strega Liquore seine unverwechselbare Farbgebung. Diese Goldfärbung erinnert an die Sonne Italiens und lässt uns Urlaub und Wärme auf der Zunge spüren. Liebhaber seines feinen Aromas trinken den Liquore pur und eisgekühlt oder als Zusatz bei Desserts oder Eisspeisen.", "https://eng.wikipedia.org/wiki/Fuzz", "Strega_0-7_Liter_1824.jpg"));
		prods.add(new ConcreteProduct("Belvedere Premium Vodka - 0,7L 40% vol", Money.of(29.78, EURO), "Spirituosen", "Der Belvedere Vodka darf sich stolz zur Königsklasse bei den Vodkas hinzu zählen, denn er gehört weltweit zu den TOP 10 der Vodkas. Der eher untypische Name des polnischen Vodkas führt auf das Schloss Belvedere in Warschau zurück. Hier war über 100 Jahre lang traditionsgemäß der Sitz des polnischen Präsidenten. Dieser Prachtbau ist auch auf dem Etikett abgebildet. Das aufwendige Herstellungsverfahren, welches bereits seit über 500 Jahren praktiziert wird, macht den Belvedere Vodka so besonders. Die erstklassige Qualität dieses Vodkas, der zu 100 % aus Roggen besteht, kann nur erzielt werden, wenn das strenge Verfahren eingehalten wird. Das sanfte Aroma sowie der feine Geschmack werden durch eine viermalige Destillation ermöglicht.", "https://eng.wikipedia.org/wiki/Fuzz", "Belvedere_Premium_Vodka-F-3740.jpg"));
		prods.add(new ConcreteProduct("Kahlua Kaffeelikör - 0,7L 20% vol ", Money.of(12.52, EURO), "Spirituosen", "Der Kahlua Kaffeelikör stammt aus Mexiko und ist ein beliebter Kaffeelikör. Dieser mexikanische Kaffeelikör wird aus Zuckerrohr, Vanille und Kaffee hergestellt und ist mit 20,0% vol. erhältlich. Mit seinem wunderbaren intensiven Geschmack nach Kaffee ist er vielfältig einsetzbar. So ist er ein Bestandteil des White Russian oder vielen anderen Mixgetränken. Durch seine unendlichen Verwendungsmöglichkeiten ist er aus keiner gut bestückten Cocktailbar wegzudenken. Mittlerweile ist der Kahlua Kaffeelikör mit verschiedenen Geschmacksrichtungen, wie zum Beispiel Haselnuss oder Vanille, zu bekommen. Mit dem Kahlua Kaffeelikör macht das Experimentieren und Zusammenstellen neuer Cocktails Spaß.", "https://eng.wikipedia.org/wiki/Fuzz", "Kahlua_0-7_Liter_1789.jpg"));
		prods.add(new ConcreteProduct("Ratzeputz Ingwer-Spirituose - 0,7L 58% vol", Money.of(19.92, EURO), "Spirituosen", "Der Ratzeputz Schnaps wurde erstmals 1877 von C.W. Baland und Langenbartels hergestellt und stammt aus der Region um Celle. Sein Hauptbestandteil ist die Ingwerwurzel. Der Ingwer gilt seit Hunderten von Jahren als Heilpflanze und dies wurde inzwischen auch in Studien nachgewiesen. Der Schnaps Ratzeputz verfügt über einen frischen zitronigen Geschmack und wird am meisten eisgekühlt genossen. Der Ratzeputz schmeckt zwischendurch auf einer Wanderung oder abends in geselliger Runde.", "https://eng.wikipedia.org/wiki/Fuzz", "Ratzeputz_0-7_Liter_11142.jpg"));
		prods.add(new ConcreteProduct("Dos Mas, Zimtlikör mit Tequila, Spirituose - 0,7L 20% vol", Money.of(9.88, EURO), "Spirituosen", "Der hellgolden leuchtende DOS MAS Tequila Zimtlikör ist eine exquisite Kreation der MBG International Premium Brands GmbH, die im Jahr 1993 gegründet wurde und ihren Sitz im nordrhein-westfälischen Paderborn hat. Der Zimtlikör basiert auf einem Tequila aus Mexiko, der den Saft blauer Agaven enthält, die im mexikanischen Bundesstaat Jalisco gedeihen. Der mexikanische Tequila wird in Paderborn so mit feinem Zimtaroma versetzt, dass der Alkoholanteil im Hintergrund bleibt und nur die Aroma-Noten aus Zimt und Kräutern den harmonisch ausgewogenen Geschmack bestimmen. Der nach Agave duftende Likör lässt sich auch pur angenehm trinken, verfügt aber nicht über zu viel Süße. Mit seiner Leichtigkeit bildet der DOS MAS Tequila Zimtlikör auch eine ideale Grundlage für besondere Cocktails, die über einen Zimt-Beigeschmack verfügen sollen. Auch als mit etwas Saft zubereiteter Longdrink lässt sich der DOS MAS Tequila Zimtlikör genießen.", "https://eng.wikipedia.org/wiki/Fuzz", "4637.jpg"));
		prods.add(new ConcreteProduct("Sauza Tequila Hornitos Anejo - 0,7L 38% vol", Money.of(27.88, EURO), "Spirituosen", "Bei dem Sauza Tequila Hornitos Anejo handelt es sich um eine Tequila-Spezialität. Ein Premium Tequila, der dem hiesigen Markt erst seit 2008 überhaupt zugänglich ist. Er verdient seine Bezeichnung als Premium Tequila, da er mindestens 12 Monate gelagert wird und dies in erlesenen Eichenfässern. So kann er sein edles und ausgesprochen gutes Aroma entwickeln. Dank des eben beschriebenen besonders edlen Aromas ist er beinahe zu schade, um ihn in Mixgetränken zu verwenden. Besser ist er daher geeignet, um ihn pur oder auf Eis zu genießen. Die Herkunft des Sauza Tequila Hornitos Anejo ist Mexiko, genauer gesagt Jalisco, ein mexikanischer Bundesstaat, welcher im Westen des Landes direkt am Pazifik gelegen ist. Innerhalb von Jalisco befindet sich auch die berühmte Stadt Tequila, die also den Namen dieser Spitzenspirituose trägt. Der Hersteller Sauza ist für seine exquisiten und aromareichen Tequilas bekannt. Schon die Farbe dieses Tequilas kann den Liebhaber überzeugen: Sie reicht von einem reifen gold-braun bis hin zu dunkleren Tönen, die auch an Bernstein erinnern. Auch der Körper ist kraftvoll und weich und somit absolut passend zu der dunklen und packenden Farbe. Am Gaumen zu spüren ist das volle Agavenaroma, welches gleichzeitig von leicht süßlichen Noten umspielt wird, die an geröstetes Holz erinnern. Durch diese Kombination kann der Geschmack eine würzige Tiefe entfalten, welche im Nachklang abschließend zu einem rauchig- sanften Aroma führt.", "https://eng.wikipedia.org/wiki/Fuzz", "Sauza_Anejo_0-7_Liter_10502.jpg"));
		prods.add(new ConcreteProduct("English Harbour Gold 3YO brauner Rum - 0,7L 40% vol", Money.of(12.41, EURO), "Spirituosen", "Dieser besondere braune Rum kommt aus Antigua und wird von der Antigua Distillery hergestellt. Er reift für drei Jahre in kleinen Eichenfässern und hat eine goldene Farbe. Die Aromen von getrockneten Früchten und Honig sowie Vanille sind schmackhaft und haben leichten Biss. Im gleichen Alter gibt es aus dieser Destille einen weißen Rum, der ebenfalls zum Mixen von Cocktails sehr gut geeignet ist. Die Destillation in Kupferkesseln bringt dem English Harbour Gold 3YO brauner Rum seine herausragende Qualität und seinen einmaligen Geschmack. Die zur Lagerung verwendeten Eichenfässer waren einstmals Whiskeyfässer und werden angekohlt, um eine spezielle Art des Aromas zu erwirken. Er schmeckt pur, on the Rocks oder auch in allen Cocktails, denen ein besonderer brauner Rum das gewisse Extra gibt. Viele Kenner vermuten England als Herkunft dieses edlen Tropfens und sind erstaunt, dass er aus der Karibik kommt. Die Abfüllung erfolgt in mehreren verschiedenen Destillen, die teilweise eine lange Geschichte haben. Gebackene Äpfel sind noch am ehesten herauszuschmecken, während das Aroma in viele verschiedene Richtungen geht. Auch florale Noten sind enthalten und der Geschmack von Kaffee; der Zimtgeruch ist vor allem beim öffnen der Flasche zu spüren und gibt dem Genuss einen wunderbaren Vorgeschmack.", "https://eng.wikipedia.org/wiki/Fuzz", "english_Harbour_0-7_Liter_7133.jpg"));
		prods.add(new ConcreteProduct("Loimu 2015 Jahrgangs-Glögg - 0,75L 18% vol", Money.of(13.56, EURO), "Spirituosen", "Ein ganz besonderer Glühwein ist der Loimu Glögg aus Finnland. Jedes Jahr aufs Neue gibt die finnische Firma Liqnell & Piispanen einen inzwischen schon weit über Finnland hinaus kultigen Glühwein heraus. Die Auflage des Weihnachts-Glögg ist wie immer streng limitiert. Der Rotwein ist mit handwerklichen Geschick durch schwarze Johannisbeeren verfeinert und mit Zimt und Nelken gewürzt geworden. Verpackt ist der Loimu 2015 in einer hübschen Designer-Flasche, die einem schon beim Anblick Vorfreude auf Weihnachten macht. So ist dieser Loimu Glögg ein ideales Geschenk für liebe Menschen und natürlich zum selbst Genießen ausgesprochen empfehlenswert.", "https://eng.wikipedia.org/wiki/Fuzz", "Loimu-18416.jpg"));

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
