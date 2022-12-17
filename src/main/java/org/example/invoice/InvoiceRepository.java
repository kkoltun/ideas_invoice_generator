package org.example.invoice;

import org.springframework.data.repository.CrudRepository;

interface InvoiceRepository extends CrudRepository<Invoice, Integer> {
    Iterable<Invoice> findByInvoiceNumber(String invoiceNumber);
}
