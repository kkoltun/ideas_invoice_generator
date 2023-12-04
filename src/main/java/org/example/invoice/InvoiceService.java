package org.example.invoice;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import static org.example.invoice.InvoiceQueries_.findByInvoiceNumber;

@Component
public class InvoiceService {

    private final SessionFactory sessionFactory;

    public InvoiceService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Iterable<Invoice> getInvoices() {
        return sessionFactory.fromSession(InvoiceQueries_::findAll);
    }

    public Invoice getInvoice(String invoiceNumber) {
        return sessionFactory.fromSession(session -> findByInvoiceNumber(session, invoiceNumber))
                .stream()
                .findAny()
                .orElse(null);
    }

    public void addInvoice(Invoice invoice) throws InvoiceNumberAlreadyExistsException {
        sessionFactory.inTransaction(session -> {
            // todo use existsByInvoiceNumber instead
            boolean invoiceNumberExists = findByInvoiceNumber(session, invoice.getInvoiceNumber()).size() > 0;
            if (invoiceNumberExists) {
                throw new InvoiceNumberAlreadyExistsException(invoice.getInvoiceNumber());
            }

            session.persist(invoice);
        });
    }

    public void deleteInvoice(Invoice invoice) {
        sessionFactory.inTransaction(session -> session.remove(invoice));
    }


}
