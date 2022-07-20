package credit.conveer.msDeal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"credit.conveer"})
@EnableFeignClients("credit.conveer.msDeal.сlient")
@EnableDiscoveryClient
@EnableJpaRepositories
public class MsDealApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsDealApplication.class, args);
        System.out.println("ok");
    }
}
