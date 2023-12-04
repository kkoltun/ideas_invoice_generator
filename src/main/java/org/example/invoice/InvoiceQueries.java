package org.example.invoice;

import org.hibernate.annotations.processing.HQL;

import java.util.List;

interface InvoiceQueries {

    @HQL("select i from Invoice i where invoiceNumber = :invoiceNumber")
    List<Invoice> findByInvoiceNumber(String invoiceNumber);

    @HQL("select i from Invoice i")
    List<Invoice> findAll();

/*
    TODO This one DOES NOT compile
    @HQL("select count(i) > 0 from Invoice i WHERE i.invoiceNumber = :invoiceNumber")
    boolean existsByInvoiceNumber(String invoiceNumber);
*/
}
