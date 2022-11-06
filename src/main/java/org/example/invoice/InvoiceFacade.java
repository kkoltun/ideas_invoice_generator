package org.example.invoice;

import org.example.company.CompanyService;
import org.example.international.invoice.HourlyInvoiceDto;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class InvoiceFacade {

    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final InvoiceService invoiceService;
    private final CompanyService companyService;

    public InvoiceFacade(InvoiceService invoiceService, CompanyService companyService) {
        this.invoiceService = invoiceService;
        this.companyService = companyService;
    }

    public Invoice getInvoice(String invoiceNumber) {
        return invoiceService.getInvoice(invoiceNumber);
    }

    public void addInvoice(HourlyInvoiceDto hourlyInvoiceDto) {
        invoiceService.addInvoice(mapHourlyInvoice(hourlyInvoiceDto));
    }

    public void deleteInvoice(String invoiceNumber) {
        Invoice invoice = invoiceService.getInvoice(invoiceNumber);
        if (invoice == null) {
            return;
        }

        invoiceService.deleteInvoice(invoice);
    }

    private Invoice mapHourlyInvoice(HourlyInvoiceDto hourlyInvoiceDto) {
        LocalDate invoiceDate = hourlyInvoiceDto.getToDate();
        LocalDate dueDate = invoiceDate.plusDays(14);

        String description = String.format("Realizacja zadań związanych z oprogramowaniem w dniach %s - %s zgodnie z umową z dnia 03.10.2022",
                hourlyInvoiceDto.getFromDate().format(DATE_FORMAT),
                hourlyInvoiceDto.getToDate().format(DATE_FORMAT));

        Invoice invoice = new Invoice();
        invoice.setInvoiceDate(invoiceDate);
        invoice.setDueDate(dueDate);
        invoice.setInvoiceNumber(hourlyInvoiceDto.getInvoiceNumber());
        invoice.setDebtorId(companyService.getCompanyId(hourlyInvoiceDto.getDebtorRegistrationNumber()));
        invoice.setVendorId(companyService.getCompanyId(hourlyInvoiceDto.getVendorRegistrationNumber()));
        invoice.setDescription(description);
        invoice.setUnitAmount(hourlyInvoiceDto.getUnitAmount());
        invoice.setUnitName("h");
        invoice.setUnitNetPrice(hourlyInvoiceDto.getUnitNetPrice());
        invoice.setInvoiceAmount(hourlyInvoiceDto.getInvoiceAmount());
        invoice.setVatRate(null);
        Timestamp timestamp = Timestamp.from(Instant.now());
        invoice.setCreated(timestamp);
        invoice.setLastupdated(timestamp);

        return invoice;
    }
}
