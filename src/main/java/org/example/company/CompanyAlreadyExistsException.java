package org.example.company;

public class CompanyAlreadyExistsException extends RuntimeException {
    private final String invoiceActorRegistrationNumber;

    public CompanyAlreadyExistsException(String invoiceActorRegistrationNumber) {
        this.invoiceActorRegistrationNumber = invoiceActorRegistrationNumber;
    }

    public String getInvoiceActorRegistrationNumber() {
        return invoiceActorRegistrationNumber;
    }

    @Override
    public String getMessage() {
        return String.format("Company with registration number [%s] already exists", invoiceActorRegistrationNumber);
    }
}
