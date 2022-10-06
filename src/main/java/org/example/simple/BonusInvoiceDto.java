package org.example.simple;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BonusInvoiceDto {
    private LocalDate invoiceDate;
    private String invoiceNumber;
    private BigDecimal invoiceAmount;
    private String description;
    private String descriptionPl;
    private String vendorRegistrationNumber;
    private String debtorRegistrationNumber;

    public BonusInvoiceDto() {
    }

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

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionPl() {
        return descriptionPl;
    }

    public void setDescriptionPl(String descriptionPl) {
        this.descriptionPl = descriptionPl;
    }

    public String getVendorRegistrationNumber() {
        return vendorRegistrationNumber;
    }

    public void setVendorRegistrationNumber(String vendorRegistrationNumber) {
        this.vendorRegistrationNumber = vendorRegistrationNumber;
    }

    public String getDebtorRegistrationNumber() {
        return debtorRegistrationNumber;
    }

    public void setDebtorRegistrationNumber(String debtorRegistrationNumber) {
        this.debtorRegistrationNumber = debtorRegistrationNumber;
    }
}
