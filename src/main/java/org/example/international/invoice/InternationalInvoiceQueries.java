package org.example.international.invoice;

import org.hibernate.annotations.processing.Find;

import java.util.List;

interface InternationalInvoiceQueries {
    @Find
    List<InternationalInvoice> findByInvoiceNumber(String invoiceNumber);
}
