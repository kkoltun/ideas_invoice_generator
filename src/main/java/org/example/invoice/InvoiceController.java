package org.example.invoice;

import org.example.company.Company;
import org.example.company.CompanyService;
import org.example.international.invoice.HourlyInvoiceDto;
import org.example.pdf.InvoicePdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.springframework.http.MediaType.APPLICATION_PDF;

@RestController
@RequestMapping("/invoice")
class InvoiceController {

    private final InvoiceFacade invoiceFacade;
    private final InvoicePdfService pdfService;
    private final CompanyService companyService;

    public InvoiceController(InvoiceFacade invoiceFacade, InvoicePdfService pdfService, CompanyService companyService) {
        this.invoiceFacade = invoiceFacade;
        this.pdfService = pdfService;
        this.companyService = companyService;
    }

    @GetMapping
    public Iterable<InvoiceResponse> getInvoices() {
        Iterable<Invoice> invoices = invoiceFacade.getInvoices();
        Set<Integer> debtors = stream(invoices)
                .map(Invoice::getDebtorId)
                .collect(Collectors.toSet());
        Set<Integer> vendors = stream(invoices)
                .map(Invoice::getVendorId)
                .collect(Collectors.toSet());

        Map<Integer, Company> companies = companyService.getCompaniesByIds(sum(debtors, vendors))
                .stream()
                .collect(Collectors.toMap(Company::getId, Function.identity()));

        return stream(invoices)
                .map(invoice -> InvoiceResponse.builder(invoice)
                        .setDebtor(companies.get(invoice.getDebtorId()))
                        .setVendor(companies.get(invoice.getVendorId())))
                .map(InvoiceResponse.Builder::build)
                .toList();
    }

    @GetMapping(produces = "application/pdf")
    public ResponseEntity<byte[]> getPdf(@RequestParam("invoice_number") String invoiceNumber) {
        Invoice invoice = invoiceFacade.getInvoice(invoiceNumber);
        if (invoice == null) {
            // todo
            throw new RuntimeException(String.format("Invoice not found %s.", invoiceNumber));
        }

        byte[] bytes = pdfService.generate(invoice);

        String filename = invoiceNumber.replaceAll("/", ".") + ".pdf";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_PDF);
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public InvoiceResponse getJson(@RequestParam("invoice_number") String invoiceNumber) {
        Invoice invoice = invoiceFacade.getInvoice(invoiceNumber);
        if (invoice == null) {
            // todo
            throw new RuntimeException(String.format("Invoice not found %s.", invoiceNumber));
        }

        Company debtor = companyService.getCompanyById(invoice.getDebtorId());
        Company vendor = companyService.getCompanyById(invoice.getVendorId());

        return InvoiceResponse.of(invoice, debtor, vendor);
    }

    @PostMapping("/hourly")
    public void addHourlyInvoice(@RequestBody HourlyInvoiceDto dto) {
        invoiceFacade.addInvoice(dto);
    }

    @DeleteMapping()
    public void deleteInvoice(@RequestParam("invoice_number") String invoiceNumber) {
        invoiceFacade.deleteInvoice(invoiceNumber);
    }

    private static Set<Integer> sum(Collection<Integer> first, Collection<Integer> second) {
        Set<Integer> sum = new HashSet<>();
        sum.addAll(first);
        sum.addAll(second);
        return sum;
    }

    private static <T> Stream<T> stream(Iterable<T> invoices) {
        return StreamSupport.stream(invoices.spliterator(), false);
    }
}
