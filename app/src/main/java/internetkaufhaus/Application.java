package internetkaufhaus;
import org.salespointframework.EnableSalespoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import internetkaufhaus.interceptors.GeneralInterceptor;
import internetkaufhaus.model.Search;

@EnableSalespoint
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	
	@Configuration
	static class InterceptorConfiguration extends WebMvcConfigurerAdapter{
		@Autowired
		private Search search;
		
		@Override
		public void addInterceptors(InterceptorRegistry registry) {
		    registry.addInterceptor(new GeneralInterceptor(search));
		} 
	}
}
