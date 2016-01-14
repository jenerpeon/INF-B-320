package internetkaufhaus.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import internetkaufhaus.entities.ConcreteProduct;
import internetkaufhaus.entities.Newsletter;

// TODO: Auto-generated Javadoc
/**
 * The Class NewsletterService.
 */
@Service
public class NewsletterService {

	@Autowired
	private JavaMailSender mailSender;

	/** The map. */
	private Map<String, String> map = new HashMap<String, String>();

	/** The old abos. */
	// Map with User and Email
	private Map<String, Map<Date, String>> oldAbos = new HashMap<String, Map<Date, String>>();
	// Map with subject and text of email

	/**
	 * Instantiates a new newsletter service.
	 */
	public NewsletterService() {
		System.out.print("");
	}

	private SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.addTemplateResolver(emailTemplateResolver());
		templateEngine.addTemplateResolver(webTemplateResolver());
		return templateEngine;
	}

	/**
	 * THYMELEAF: Template Resolver for email templates.
	 */
	private TemplateResolver emailTemplateResolver() {
		TemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setOrder(1);
		return templateResolver;
	}

	private TemplateResolver webTemplateResolver() {
		TemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setOrder(2);
		return templateResolver;
	}

	/**
	 * Sets the map.
	 *
	 * @param map
	 *            the map
	 */
	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	/**
	 * Gets the old abos.
	 *
	 * @return the old abos
	 */
	public Map<String, Map<Date, String>> getOldAbos() {
		return oldAbos;
	}

	/**
	 * Sets the old abos.
	 *
	 * @param oldAbos
	 *            the old abos
	 */
	public void setOldAbos(Map<String, Map<Date, String>> oldAbos) {
		this.oldAbos = oldAbos;
	}

	/**
	 * The sendNewsletters method sends the given text as newsletter to all
	 * costumers which have subscribed the newsletter.
	 * 
	 * @param content
	 *            the newsletter content as String
	 *
	 */
	/*
	 * public void sendNewsletter(String subject, String content) { for (String
	 * email : this.getMap().values()) { mailsender.sendMail(email, content,
	 * "zu@googlemail.com", subject); }
	 * 
	 * }
	 */

	/**
	 * Gets the map.
	 *
	 * @return the map
	 */
	public Map<String, String> getMap() {

		return map;
	}

	public void sendNewsletter(Newsletter newsletter, final String recipientName, final String recipientEmail)
			throws MessagingException, IOException {

		List<List<ConcreteProduct>> prods = newsletter.getProductSelection();

		String htmlContent = newsletter.getHtmlContent();

		final MimeMessage mimeMessage = mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		message.setSubject("Newsletter");
		message.setFrom("thymeleaf@example.com");
		message.setTo(recipientEmail);
		message.setText(htmlContent, true);

		MultipartFile logo = getMultipartFile("src/main/resources/static/resources/Bilder", "Logo.png", "Logo.png");
		final InputStreamSource logoImageSource = new ByteArrayResource(logo.getBytes());
		message.addInline(logo.getName(), logoImageSource, logo.getContentType());

		MultipartFile header = getMultipartFile("src/main/resources/static/resources/Bilder", "Newsletter.png",
				"Newsletter.png");
		final InputStreamSource headerImageSource = new ByteArrayResource(header.getBytes());
		message.addInline(header.getName(), headerImageSource, header.getContentType());

		MultipartFile footer = getMultipartFile("src/main/resources/static/resources/Bilder", "SocialMedia.png",
				"SocialMedia.png");
		final InputStreamSource footerImageSource = new ByteArrayResource(footer.getBytes());
		message.addInline(footer.getName(), footerImageSource, footer.getContentType());

		for (ConcreteProduct prod : Stream.concat(prods.get(0).stream(), prods.get(1).stream())
				.collect(Collectors.toList())) {
			MultipartFile prodImage = getMultipartFile("src/main/resources/static/resources/Bilder/Proukte",
					prod.getImagefile(), prod.getImagefile());
			final InputStreamSource prodImageSource = new ByteArrayResource(prodImage.getBytes());
			message.addInline(prodImage.getName(), prodImageSource, prodImage.getContentType());
		}

		this.mailSender.send(mimeMessage);

	}

	public String processTemplate(List<List<ConcreteProduct>> prods, String template, final Locale locale,
			HttpServletRequest request, HttpServletResponse response) {

		final WebContext ctx = new WebContext(request, response, request.getServletContext(), locale);
		ctx.setVariable("prods1", prods.get(0));
		ctx.setVariable("prods2", prods.get(1));

		final String htmlContent = templateEngine().process(template, ctx);
		return htmlContent;
	}

	public String getPreviewHTML(String htmlContent, List<List<ConcreteProduct>> prods) {

		htmlContent = htmlContent.replace("cid:Logo", "/resources/Bilder/Logo.png");
		htmlContent = htmlContent.replace("cid:Newsletter", "/resources/Bilder/Newsletter.png");
		htmlContent = htmlContent.replace("cid:SocialMedia", "/resources/Bilder/SocialMedia.png");

		for (List<ConcreteProduct> prodList : prods) {
			for (ConcreteProduct prod : prodList) {
				htmlContent = htmlContent.replace("cid:" + prod.getImagefile(),
						"/resources/Bilder/Produkte/" + prod.getImagefile());
			}
		}

		return htmlContent;
	}

	private MultipartFile getMultipartFile(String sourcePath, String fileName, String imageName) throws IOException {

		Path path = FileSystems.getDefault().getPath(sourcePath, fileName);

		File file = new File(path.toUri());
		FileInputStream input = new FileInputStream(file);

		MultipartFile img = new MockMultipartFile(imageName.substring(0, imageName.length() - 4), imageName,
				"text/plain", IOUtils.toByteArray(input));

		return img;
	}

}
