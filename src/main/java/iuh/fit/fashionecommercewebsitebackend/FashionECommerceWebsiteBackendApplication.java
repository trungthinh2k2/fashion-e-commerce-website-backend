package iuh.fit.fashionecommercewebsitebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FashionECommerceWebsiteBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FashionECommerceWebsiteBackendApplication.class, args);
    }

}
