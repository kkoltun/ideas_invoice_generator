package org.example.invoice;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.StreamSupport;

@Component
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Iterable<Invoice> getInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice getInvoice(String invoiceNumber) {
        return StreamSupport.stream(invoiceRepository.findByInvoiceNumber(invoiceNumber).spliterator(), false)
                .findAny()
                .orElse(null);
    }

    public void addInvoice(Invoice invoice) throws InvoiceNumberAlreadyExistsException {
        boolean invoiceNumberExists = invoiceRepository.findByInvoiceNumber(invoice.getInvoiceNumber()).iterator().hasNext();
        if (invoiceNumberExists) {
            throw new InvoiceNumberAlreadyExistsException(invoice.getInvoiceNumber());
        }

        BigDecimal vat = invoice.getInvoiceAmount().multiply(BigDecimal.valueOf(23))
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                .setScale(2, RoundingMode.HALF_UP);
        // todo check the added invoice
        invoiceRepository.save(invoice);
    }

    public void deleteInvoice(Invoice invoice) {
        invoiceRepository.delete(invoice);
    }


}
