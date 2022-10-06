package org.example.pdf;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_PDF;

@RestController
public class InvoicePdfController {

    private final InvoicePdfService invoicePdfService;

    public InvoicePdfController(InvoicePdfService invoicePdfService) {
        this.invoicePdfService = invoicePdfService;
    }

    @GetMapping("/pdf/{invoiceNumber}")
    public ResponseEntity<byte[]> generate(@PathVariable String invoiceNumber) {
        String filename = invoiceNumber + ".pdf";
        byte[] bytes = invoicePdfService.generate(invoiceNumber);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_PDF);
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
}
