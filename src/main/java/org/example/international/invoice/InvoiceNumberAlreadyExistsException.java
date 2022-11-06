package org.example.international.invoice;

public class InvoiceNumberAlreadyExistsException extends RuntimeException{
    private final String invoiceNumber;

    public InvoiceNumberAlreadyExistsException(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }
}
