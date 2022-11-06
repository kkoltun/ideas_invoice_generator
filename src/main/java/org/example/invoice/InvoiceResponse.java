package org.example.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.example.company.Company;

import java.math.BigDecimal;
import java.time.LocalDate;

public class InvoiceResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate invoiceDate;
    private String invoiceNumber;
    private double unitAmount;
    private BigDecimal unitNetPrice;
    private BigDecimal invoiceAmount;
    private String debtorRegistrationNumber;
    private String vendorRegistrationNumber;

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public double getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(double unitAmount) {
        this.unitAmount = unitAmount;
    }

    public BigDecimal getUnitNetPrice() {
        return unitNetPrice;
    }

    public void setUnitNetPrice(BigDecimal unitNetPrice) {
        this.unitNetPrice = unitNetPrice;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getDebtorRegistrationNumber() {
        return debtorRegistrationNumber;
    }

    public void setDebtorRegistrationNumber(String debtorRegistrationNumber) {
        this.debtorRegistrationNumber = debtorRegistrationNumber;
    }

    public String getVendorRegistrationNumber() {
        return vendorRegistrationNumber;
    }

    public void setVendorRegistrationNumber(String vendorRegistrationNumber) {
        this.vendorRegistrationNumber = vendorRegistrationNumber;
    }

    public static InvoiceResponse of(Invoice invoice, Company debtor, Company vendor) {
        InvoiceResponse response = new InvoiceResponse();
        response.setInvoiceNumber(invoice.getInvoiceNumber());
        response.setInvoiceAmount(invoice.getInvoiceAmount());
        response.setInvoiceDate(invoice.getInvoiceDate());
        response.setUnitAmount(invoice.getUnitAmount());
        response.setUnitNetPrice(invoice.getUnitNetPrice());
        response.setDebtorRegistrationNumber(debtor.getRegistrationNumber());
        response.setVendorRegistrationNumber(vendor.getRegistrationNumber());
        return response;
    }
}
