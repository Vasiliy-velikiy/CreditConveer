package credit.conveer.msDeal.MockConfig;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class WireMockConfig {

    @Autowired
    private WireMockServer wireMockServer;


    @Bean(initMethod = "start", destroyMethod = "stop")
    public static WireMockServer mockService() {
        return new WireMockServer(8008);
    }

}
