package internetkaufhaus;

import org.salespointframework.EnableSalespoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

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
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/*
	 * @Configuration static class InterceptorConfiguration extends
	 * WebMvcConfigurerAdapter {
	 * 
	 * @Autowired private Search search;
	 * 
	 * @Override public void addInterceptors(InterceptorRegistry registry) {
	 * registry.addInterceptor(new GeneralInterceptor(search)); } }
	 */
}
