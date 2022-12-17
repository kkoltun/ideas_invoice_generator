package org.example.international.invoice;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@Table("international_invoice")
public class InternationalInvoice {
    private Integer id;
    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private String invoiceNumber;
    private Integer vendorId;
    private Integer debtorId;
    private String description;
    private String descriptionPl;
    private Double unitAmount;
    private String unitName;
    private BigDecimal unitNetPrice;
    private BigDecimal invoiceAmount;
    private BigDecimal usdPlnRate;
    private BigDecimal vatRate;
    private String nbpTableNumber;
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

    @Column("invoice_date")
    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoice_date) {
        this.invoiceDate = invoice_date;
    }

    @Column("due_date")
    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate due_date) {
        this.dueDate = due_date;
    }

    @Column("invoice_number")
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    @Column("vendor_id")
    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    @Column("debtor_id")
    public Integer getDebtorId() {
        return debtorId;
    }

    public void setDebtorId(Integer debtorId) {
        this.debtorId = debtorId;
    }

    @Column("description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column("description_pl")
    public String getDescriptionPl() {
        return descriptionPl;
    }

    public void setDescriptionPl(String descriptionPl) {
        this.descriptionPl = descriptionPl;
    }

    @Column("unit_amount")
    public Double getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(Double amount) {
        this.unitAmount = amount;
    }

    @Column("unit_name")
    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unit) {
        this.unitName = unit;
    }

    @Column("unit_net_price")
    public BigDecimal getUnitNetPrice() {
        return unitNetPrice;
    }

    public void setUnitNetPrice(BigDecimal netPrice) {
        this.unitNetPrice = netPrice;
    }

    @Column("invoice_amount")
    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    @Column("usd_pln_rate")
    public BigDecimal getUsdPlnRate() {
        return usdPlnRate;
    }

    public void setUsdPlnRate(BigDecimal usdPlnRate) {
        this.usdPlnRate = usdPlnRate;
    }

    @Column("vat_rate")
    public BigDecimal getVatRate() {
        return vatRate;
    }

    public void setVatRate(BigDecimal vatRate) {
        this.vatRate = vatRate;
    }

    @Column("nbp_table_number")
    public String getNbpTableNumber() {
        return nbpTableNumber;
    }

    public void setNbpTableNumber(String nbpTableNumber) {
        this.nbpTableNumber = nbpTableNumber;
    }

    @Column("nbp_table_date")
    public LocalDate getNbpTableDate() {
        return nbpTableDate;
    }

    public void setNbpTableDate(LocalDate nbpTableDate) {
        this.nbpTableDate = nbpTableDate;
    }

    @Column("created")
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Column("lastupdated")
    public Timestamp getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(Timestamp lastupdated) {
        this.lastupdated = lastupdated;
    }

}