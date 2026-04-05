package viajes.demo.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("production")
public class CloudinaryConfig {

    @Bean
    Cloudinary cloudinary(CloudinaryProperties props) {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", props.cloudName(),
                "api_key", props.apiKey(),
                "api_secret", props.apiSecret(),
                "secure", true
        ));
    }
}
