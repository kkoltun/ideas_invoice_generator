package org.example.international.invoice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
public class InternationalInvoice {

    @Id
    private Integer id;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "vendor_id")
    private Integer vendorId;

    @Column(name = "debtor_id")
    private Integer debtorId;

    private String description;

    @Column(name = "description_pl")
    private String descriptionPl;

    @Column(name = "unit_amount")
    private Double unitAmount;

    @Column(name = "unit_name")
    private String unitName;

    @Column(name = "unit_net_price")
    private BigDecimal unitNetPrice;

    @Column(name = "invoice_amount")
    private BigDecimal invoiceAmount;

    @Column(name = "usd_pln_rate")
    private BigDecimal usdPlnRate;

    @Column(name = "vat_rate")
    private BigDecimal vatRate;

    @Column(name = "nbp_table_number")
    private String nbpTableNumber;

    @Column(name = "nbp_table_date")
    private LocalDate nbpTableDate;

    private Timestamp created;

    private Timestamp lastupdated;

    public InternationalInvoice() {
    }

    @Id
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

    public String getDescriptionPl() {
        return descriptionPl;
    }

    public void setDescriptionPl(String descriptionPl) {
        this.descriptionPl = descriptionPl;
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

    public BigDecimal getUsdPlnRate() {
        return usdPlnRate;
    }

    public void setUsdPlnRate(BigDecimal usdPlnRate) {
        this.usdPlnRate = usdPlnRate;
    }

    public BigDecimal getVatRate() {
        return vatRate;
    }

    public void setVatRate(BigDecimal vatRate) {
        this.vatRate = vatRate;
    }

    public String getNbpTableNumber() {
        return nbpTableNumber;
    }

    public void setNbpTableNumber(String nbpTableNumber) {
        this.nbpTableNumber = nbpTableNumber;
    }

    public LocalDate getNbpTableDate() {
        return nbpTableDate;
    }

    public void setNbpTableDate(LocalDate nbpTableDate) {
        this.nbpTableDate = nbpTableDate;
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

}
