package org.example.international.invoice;

import org.example.pdf.InvoicePdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_PDF;

@RestController
@RequestMapping("/invoice/international")
class InternationalInvoiceController {

    private final InternationalInvoiceFacade invoiceFacade;
    private final InvoicePdfService invoicePdfService;

    public InternationalInvoiceController(InternationalInvoiceFacade invoiceFacade, InvoicePdfService invoicePdfService) {
        this.invoiceFacade = invoiceFacade;
        this.invoicePdfService = invoicePdfService;
    }

    @GetMapping("/{invoiceNumber}/pdf")
    public ResponseEntity<byte[]> generate(@PathVariable String invoiceNumber) {
        InternationalInvoice invoice = invoiceFacade.getInvoice(invoiceNumber);
        if (invoice == null) {
            // todo this should be a 404 HTTP Code
            throw new RuntimeException(String.format("Invoice not found %s.", invoiceNumber));
        }

        byte[] bytes = invoicePdfService.generate(invoice);

        String filename = invoiceNumber + ".pdf";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_PDF);
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    @PostMapping("/hourly")
    public void addHourlyInvoice(@RequestBody HourlyInvoiceDto dto) {
        invoiceFacade.addInvoice(dto);
    }

    @PostMapping("/bonus")
    public void addBonusInvoice(@RequestBody BonusInvoiceDto dto) {
        invoiceFacade.addInvoice(dto);
    }


    @DeleteMapping("/invoice/{invoiceNumber}")
    public void deleteInvoice(@PathVariable String invoiceNumber) {
        invoiceFacade.deleteInvoice(invoiceNumber);
    }
}
