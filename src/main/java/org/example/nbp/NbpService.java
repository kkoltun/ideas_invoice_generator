package org.example.nbp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class NbpService {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public NbpRateDto getUsdRate(LocalDate date) {
        String textUri = "http://api.nbp.pl/api/exchangerates/rates/A/USD/" + date.format(DATE_FORMAT);
        URI uri = URI.create(textUri);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(uri);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);

                ObjectMapper objectMapper = new ObjectMapper();
                RatesTableDto ratesTableDto = objectMapper.readValue(result, RatesTableDto.class);
                RateDto rateDto = ratesTableDto.getRates().get(0);

                return new NbpRateDto(LocalDate.parse(rateDto.getEffectiveDate(), DATE_FORMAT),
                        rateDto.getNo(),
                        rateDto.getMid());
            }
        } catch (Exception e) {
            throw new NbpServiceException(e);
        }
    }
}
