package internetkaufhaus;

import org.salespointframework.EnableSalespoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

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
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
