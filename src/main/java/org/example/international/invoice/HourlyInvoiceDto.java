package org.example.international.invoice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

public class HourlyInvoiceDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fromDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate toDate;
    private String invoiceNumber;
    private double unitAmount;
    private BigDecimal unitNetPrice;
    private BigDecimal invoiceAmount;
    private String debtorRegistrationNumber;
    private String vendorRegistrationNumber;

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
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
}
