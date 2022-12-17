package org.example.international.invoice;

import org.springframework.stereotype.Component;

@Component
public class InternationalInvoiceService {

    // todo use the new InternationalInvoiceRepository

    public InternationalInvoiceService() {
    }

    public InternationalInvoice getInvoice(String invoiceNumber) {
        return null;
    }

    public void addInvoice(InternationalInvoice internationalInvoice) throws InvoiceNumberAlreadyExistsException {
    }

    public void deleteInvoice(InternationalInvoice internationalInvoice) {
    }
}
