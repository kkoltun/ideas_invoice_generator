package org.example.international.invoice;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

import static org.example.infrastructure.Database.getEntityManagerFactory;

@Component
public class InternationalInvoiceService {

    private final EntityManagerFactory entityManagerFactory;

    public InternationalInvoiceService() {
        entityManagerFactory = getEntityManagerFactory();
    }

    public InternationalInvoice getInvoice(String invoiceNumber) {
        EntityManager manager = entityManagerFactory.createEntityManager();

        List<InternationalInvoice> internationalInvoices = manager.createQuery("" +
                        "SELECT invoice " +
                        "FROM InternationalInvoice invoice " +
                        "WHERE invoice.invoiceNumber = :invoiceNumber", InternationalInvoice.class)
                .setParameter("invoiceNumber", invoiceNumber)
                .getResultList();

        return !internationalInvoices.isEmpty()
                ? internationalInvoices.get(0)
                : null;
    }

    public void addInvoice(InternationalInvoice internationalInvoice) throws InvoiceNumberAlreadyExistsException {
        EntityManager manager = entityManagerFactory.createEntityManager();

        boolean invoiceNumberExists = manager.createQuery("" +
                        "SELECT invoice " +
                        "FROM InternationalInvoice invoice " +
                        "WHERE invoice.invoiceNumber = :invoiceNumber ", InternationalInvoice.class)
                .setParameter("invoiceNumber", internationalInvoice.getInvoiceNumber())
                .getResultList().size() > 0;
        if (invoiceNumberExists) {
            throw new InvoiceNumberAlreadyExistsException(internationalInvoice.getInvoiceNumber());
        }

        // todo check the added invoice

        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        manager.persist(internationalInvoice);
        transaction.commit();

        manager.close();
    }

    public void deleteInvoice(InternationalInvoice internationalInvoice) {
        EntityManager manager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        manager.createQuery("" +
                        "DELETE FROM InternationalInvoice invoice " +
                        "WHERE invoice.id = :id")
                .setParameter("id", internationalInvoice.getId())
                .executeUpdate();

        transaction.commit();
        manager.close();
    }


}
