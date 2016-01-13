package internetkaufhaus;

import java.io.IOException;
import java.util.Properties;

import org.salespointframework.EnableSalespoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

// TODO: Auto-generated Javadoc
/**
 * This is the main class. Who would've thought!
 * 
 * @author max
 *
 */
@EnableSalespoint
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

	/**
	 * This is the main function. Who would've thought!
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public JavaMailSender mailSender() throws IOException {
		Properties properties = configProperties();
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(properties.getProperty("mail.server.host"));
		mailSender.setPort(Integer.parseInt(properties.getProperty("mail.server.port")));
		mailSender.setProtocol(properties.getProperty("mail.server.protocol"));
		mailSender.setUsername(properties.getProperty("mail.server.username"));
		mailSender.setPassword(properties.getProperty("mail.server.password"));
		mailSender.setJavaMailProperties(javaMailProperties());
		return mailSender;
	}
	
	private Properties configProperties() throws IOException {
		Properties properties = new Properties();
		properties.load(new ClassPathResource("configuration.properties").getInputStream());
		return properties;
	}

	private Properties javaMailProperties() throws IOException {
		Properties properties = new Properties();
		properties.load(new ClassPathResource("javamail.properties").getInputStream());
		return properties;
	}

}
