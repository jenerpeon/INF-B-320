package internetkaufhaus;
import org.salespointframework.EnableSalespoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@EnableSalespoint
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

