package org.example.invoice;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

import static org.example.infrastructure.Database.getEntityManagerFactory;

@Component
public class InvoiceService {

    private final EntityManagerFactory entityManagerFactory;

    public InvoiceService() {
        entityManagerFactory = getEntityManagerFactory();
    }

    public Invoice getInvoice(String invoiceNumber) {
        EntityManager manager = entityManagerFactory.createEntityManager();

        List<Invoice> internationalInvoices = manager.createQuery("" +
                        "SELECT invoice " +
                        "FROM Invoice invoice " +
                        "WHERE invoice.invoiceNumber = :invoiceNumber", Invoice.class)
                .setParameter("invoiceNumber", invoiceNumber)
                .getResultList();

        return !internationalInvoices.isEmpty()
                ? internationalInvoices.get(0)
                : null;
    }

    public void addInvoice(Invoice invoice) throws InvoiceNumberAlreadyExistsException {
        EntityManager manager = entityManagerFactory.createEntityManager();

        boolean invoiceNumberExists = manager.createQuery("" +
                        "SELECT invoice " +
                        "FROM Invoice invoice " +
                        "WHERE invoice.invoiceNumber = :invoiceNumber ", Invoice.class)
                .setParameter("invoiceNumber", invoice.getInvoiceNumber())
                .getResultList().size() > 0;
        if (invoiceNumberExists) {
            throw new InvoiceNumberAlreadyExistsException(invoice.getInvoiceNumber());
        }

        // todo check the added invoice

        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        manager.persist(invoice);
        transaction.commit();

        manager.close();
    }

    public void deleteInvoice(Invoice invoice) {
        EntityManager manager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        manager.createQuery("" +
                        "DELETE FROM Invoice invoice " +
                        "WHERE invoice.id = :id")
                .setParameter("id", invoice.getId())
                .executeUpdate();

        transaction.commit();
        manager.close();
    }


}
