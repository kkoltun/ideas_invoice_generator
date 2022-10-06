package org.example.invoice;

import net.sf.jasperreports.engine.util.JRStyledText;

public class InvoiceActorAlreadyExistsException extends RuntimeException {
    private final String invoiceActorRegistrationNumber;

    public InvoiceActorAlreadyExistsException(String invoiceActorRegistrationNumber) {
        this.invoiceActorRegistrationNumber = invoiceActorRegistrationNumber;
    }

    public String getInvoiceActorRegistrationNumber() {
        return invoiceActorRegistrationNumber;
    }
}
