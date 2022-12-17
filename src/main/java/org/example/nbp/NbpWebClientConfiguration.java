package org.example.nbp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class NbpWebClientConfiguration {
    private static final String URI = "http://api.nbp.pl/api/exchangerates/rates/A/USD/";

    @Bean
    WebClient nbpWebClient() {
        return WebClient.create(URI);
    }
}
