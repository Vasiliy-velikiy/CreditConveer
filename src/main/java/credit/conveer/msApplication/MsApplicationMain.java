package credit.conveer.msApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableFeignClients("credit.conveer.msApplication.client")
@EnableDiscoveryClient
public class MsApplicationMain {
    public static void main(String[] args) {
        SpringApplication.run(MsApplicationMain.class, args);
        System.out.println("ok");
    }
}
