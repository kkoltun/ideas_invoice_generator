package org.example.nbp;

import org.example.company.Company;
import org.example.invoice.Invoice;
import org.example.invoice.InvoiceResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/nbp")
public class NbpController {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final NbpService nbpService;

    public NbpController(NbpService nbpService) {
        this.nbpService = nbpService;
    }

    @GetMapping()
    public NbpRateDto get(@RequestParam("date") String date) {
        LocalDate parsedDate = LocalDate.parse(date, DATE_FORMAT);

        return nbpService.getUsdRate(parsedDate).block();
    }
}
