package org.example.invoice;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(name ="invoice_date")
    private LocalDate invoiceDate;

    @Column(name ="due_date")
    private LocalDate dueDate;

    @Column(name ="invoice_number")
    private String invoiceNumber;

    @Column(name ="vendor_id")
    private Integer vendorId;

    @Column(name ="debtor_id")
    private Integer debtorId;

    private String description;

    @Column(name ="unit_amount")
    private Double unitAmount;

    @Column(name ="unit_name")
    private String unitName;

    @Column(name ="unit_net_price")
    private BigDecimal unitNetPrice;

    @Column(name ="invoice_amount")
    private BigDecimal invoiceAmount;

    @Column(name ="vat_rate")
    private BigDecimal vatRate;

    private Timestamp created;

    private Timestamp lastupdated;

    public Invoice() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoice_date) {
        this.invoiceDate = invoice_date;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate due_date) {
        this.dueDate = due_date;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Integer getDebtorId() {
        return debtorId;
    }

    public void setDebtorId(Integer debtorId) {
        this.debtorId = debtorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(Double amount) {
        this.unitAmount = amount;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unit) {
        this.unitName = unit;
    }

    public BigDecimal getUnitNetPrice() {
        return unitNetPrice;
    }

    public void setUnitNetPrice(BigDecimal netPrice) {
        this.unitNetPrice = netPrice;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public BigDecimal getVatRate() {
        return vatRate;
    }

    public void setVatRate(BigDecimal vatRate) {
        this.vatRate = vatRate;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(Timestamp lastupdated) {
        this.lastupdated = lastupdated;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", invoiceDate=" + invoiceDate +
                ", dueDate=" + dueDate +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", vendorId=" + vendorId +
                ", debtorId=" + debtorId +
                ", description='" + description + '\'' +
                ", unitAmount=" + unitAmount +
                ", unitName='" + unitName + '\'' +
                ", unitNetPrice=" + unitNetPrice +
                ", invoiceAmount=" + invoiceAmount +
                ", vatRate=" + vatRate +
                ", created=" + created +
                ", lastupdated=" + lastupdated +
                '}';
    }
}
