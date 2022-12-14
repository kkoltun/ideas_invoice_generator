package org.example.invoice;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "invoice")
@SequenceGenerator(name = "invoice_id_generator", sequenceName = "invoice_id_seq", allocationSize = 1)
public class Invoice {

    private Integer id;
    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private String invoiceNumber;
    private Integer vendorId;
    private Integer debtorId;
    private String description;
    private Double unitAmount;
    private String unitName;
    private BigDecimal unitNetPrice;
    private BigDecimal invoiceAmount;
    private BigDecimal vatRate;
    private Timestamp created;
    private Timestamp lastupdated;

    public Invoice() {}

    @Id
    @GeneratedValue(generator = "invoice_id_generator", strategy = SEQUENCE)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "invoice_date")
    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoice_date) {
        this.invoiceDate = invoice_date;
    }

    @Column(name = "due_date")
    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate due_date) {
        this.dueDate = due_date;
    }

    @Column(name = "invoice_number")
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    @Column(name = "vendor_id")
    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    @Column(name = "debtor_id")
    public Integer getDebtorId() {
        return debtorId;
    }

    public void setDebtorId(Integer debtorId) {
        this.debtorId = debtorId;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "unit_amount")
    public Double getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(Double amount) {
        this.unitAmount = amount;
    }

    @Column(name = "unit_name")
    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unit) {
        this.unitName = unit;
    }

    @Column(name = "unit_net_price")
    public BigDecimal getUnitNetPrice() {
        return unitNetPrice;
    }

    public void setUnitNetPrice(BigDecimal netPrice) {
        this.unitNetPrice = netPrice;
    }

    @Column(name = "invoice_amount")
    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    @Column(name = "vat_rate")
    public BigDecimal getVatRate() {
        return vatRate;
    }

    public void setVatRate(BigDecimal vatRate) {
        this.vatRate = vatRate;
    }

    @Column(name = "created")
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Column(name = "lastupdated")
    public Timestamp getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(Timestamp lastupdated) {
        this.lastupdated = lastupdated;
    }

}