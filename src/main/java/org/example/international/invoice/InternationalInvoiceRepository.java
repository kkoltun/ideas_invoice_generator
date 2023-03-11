package org.example.international.invoice;

import org.springframework.data.repository.CrudRepository;

interface InternationalInvoiceRepository extends CrudRepository<InternationalInvoice, Integer> {

    Iterable<InternationalInvoice> findByInvoiceNumber(String invoiceNumber);
}
