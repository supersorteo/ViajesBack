package viajes.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import viajes.demo.config.AdminProperties;
import viajes.demo.config.CloudinaryProperties;

@SpringBootApplication
@EnableConfigurationProperties({ AdminProperties.class, CloudinaryProperties.class })
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
