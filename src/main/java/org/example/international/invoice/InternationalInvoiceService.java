package org.example.international.invoice;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import static org.example.international.invoice.InternationalInvoiceQueries_.findByInvoiceNumber;

@Component
public class InternationalInvoiceService {

    private final SessionFactory sessionFactory;

    public InternationalInvoiceService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public InternationalInvoice getInvoice(String invoiceNumber) {
        return sessionFactory.fromSession(session -> findByInvoiceNumber(session, invoiceNumber))
                .stream()
                .findAny()
                .orElse(null);
    }

    public void addInvoice(InternationalInvoice invoice) throws InvoiceNumberAlreadyExistsException {
        sessionFactory.inSession(session -> {
            boolean invoiceNumberExists = findByInvoiceNumber(session, invoice.getInvoiceNumber()).size() > 0;
            if (invoiceNumberExists) {
                throw new org.example.invoice.InvoiceNumberAlreadyExistsException(invoice.getInvoiceNumber());
            }

            // todo check the added invoice
            session.persist(invoice);
        });
    }

    public void deleteInvoice(InternationalInvoice invoice) {
        sessionFactory.inSession(session -> session.remove(invoice));
    }
}
