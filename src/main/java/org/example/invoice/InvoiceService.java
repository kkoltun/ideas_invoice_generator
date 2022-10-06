package org.example.invoice;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Component
public class InvoiceService {

    private final EntityManagerFactory entityManagerFactory;

    public InvoiceService() {
        entityManagerFactory = getEntityManagerFactory();
    }

    // todo infrastructure should be extracted out
    public EntityManagerFactory getEntityManagerFactory() {
        Properties properties = new Properties();

        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.use_sql_comments", "true");

        properties.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        properties.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/invoice");
        properties.setProperty("hibernate.connection.username", "kkoltun");
        properties.setProperty("hibernate.connection.password", "b1rd@tr33");

        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        BootstrapServiceRegistry bootstrapServiceRegistry = new BootstrapServiceRegistryBuilder()
                .applyClassLoader(InvoiceService.class.getClassLoader())
                .build();
        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder(bootstrapServiceRegistry)
                .applySettings(properties)
                .build();

        MetadataSources metadataSources = new MetadataSources(standardServiceRegistry);
        metadataSources.addAnnotatedClass(Invoice.class);
        metadataSources.addAnnotatedClass(InvoiceActor.class);

        Metadata metadata = metadataSources.buildMetadata();
        return metadata.buildSessionFactory();
    }

    public InvoiceActor getInvoiceActor(String actorRegistrationNumber) {
        EntityManager manager = entityManagerFactory.createEntityManager();

        List<InvoiceActor> actors = manager.createQuery("" +
                        "SELECT actor " +
                        "FROM InvoiceActor actor " +
                        "WHERE actor.registrationNumber = :registrationNumber", InvoiceActor.class)
                .setParameter("registrationNumber", actorRegistrationNumber)
                .getResultList();

        return !actors.isEmpty()
                ? actors.get(0)
                : null;
    }

    public void addActor(InvoiceActor invoiceActor) throws InvoiceActorAlreadyExistsException {
        EntityManager manager = entityManagerFactory.createEntityManager();

        boolean invoiceActorExists = manager.createQuery("" +
                        "SELECT actor " +
                        "FROM InvoiceActor actor " +
                        "WHERE actor.registrationNumber = :registrationNumber ", InvoiceActor.class)
                .setParameter("registrationNumber", invoiceActor.getRegistrationNumber())
                .getResultList().size() > 0;
        if (invoiceActorExists) {
            throw new InvoiceActorAlreadyExistsException(invoiceActor.getRegistrationNumber());
        }

        // todo check the added actor

        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        manager.persist(invoiceActor);
        transaction.commit();

        manager.close();
    }

    public Invoice getInvoice(String invoiceNumber) {
        EntityManager manager = entityManagerFactory.createEntityManager();

        List<Invoice> invoices = manager.createQuery("" +
                        "SELECT invoice " +
                        "FROM Invoice invoice " +
                        "WHERE invoice.invoiceNumber = :invoiceNumber", Invoice.class)
                .setParameter("invoiceNumber", invoiceNumber)
                .getResultList();

        return !invoices.isEmpty()
                ? invoices.get(0)
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

    public void deleteInvoiceActor(InvoiceActor invoiceActor) {
        EntityManager manager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        manager.createQuery("" +
                        "DELETE FROM InvoiceActor actor " +
                        "WHERE actor.id = :id")
                .setParameter("id", invoiceActor.getId())
                .executeUpdate();

        transaction.commit();
        manager.close();
    }
}
