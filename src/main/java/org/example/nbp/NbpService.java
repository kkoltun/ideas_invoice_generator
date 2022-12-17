package org.example.nbp;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class NbpService {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final WebClient nbpWebClient;

    public NbpService(WebClient nbpWebClient) {
        this.nbpWebClient = nbpWebClient;
    }

    public Mono<NbpRateDto> getUsdRate(LocalDate date) {
        return nbpWebClient
                .get()
                .uri(builder -> builder.pathSegment(date.format(DATE_FORMAT)).build())
                .retrieve()
                .bodyToMono(RatesTableDto.class)
                .flatMapIterable(RatesTableDto::getRates)
                .last()
                .map(NbpService::map);
    }

    private static NbpRateDto map(RateDto rate) {
        return new NbpRateDto(LocalDate.parse(rate.getEffectiveDate(), DATE_FORMAT),
                rate.getNo(),
                rate.getMid());
    }
}
