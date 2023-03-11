package org.example.international.invoice;

import org.springframework.stereotype.Component;

import java.util.stream.StreamSupport;

@Component
public class InternationalInvoiceService {

    private final InternationalInvoiceRepository invoiceRepository;

    public InternationalInvoiceService(InternationalInvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public InternationalInvoice getInvoice(String invoiceNumber) {
        return StreamSupport.stream(invoiceRepository.findByInvoiceNumber(invoiceNumber).spliterator(), false)
                .findAny()
                .orElse(null);
    }

    public void addInvoice(InternationalInvoice invoice) throws InvoiceNumberAlreadyExistsException {
        boolean invoiceNumberExists = invoiceRepository.findByInvoiceNumber(invoice.getInvoiceNumber()).iterator().hasNext();
        if (invoiceNumberExists) {
            throw new org.example.invoice.InvoiceNumberAlreadyExistsException(invoice.getInvoiceNumber());
        }

        // todo check the added invoice
        invoiceRepository.save(invoice);
    }

    public void deleteInvoice(InternationalInvoice invoice) {
        invoiceRepository.delete(invoice);
    }
}
