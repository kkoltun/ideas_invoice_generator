package org.example.infrastructure;

import org.example.company.Company;
import org.example.international.invoice.InternationalInvoice;
import org.example.invoice.Invoice;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.jdbc.ReturningWork;

import javax.persistence.EntityManagerFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    public static EntityManagerFactory getEntityManagerFactory() {
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
                .applyClassLoader(Database.class.getClassLoader())
                .build();
        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder(bootstrapServiceRegistry)
                .applySettings(properties)
                .build();

        MetadataSources metadataSources = new MetadataSources(standardServiceRegistry);
        metadataSources.addAnnotatedClass(InternationalInvoice.class);
        metadataSources.addAnnotatedClass(Invoice.class);
        metadataSources.addAnnotatedClass(Company.class);

        Metadata metadata = metadataSources.buildMetadata();
        return metadata.buildSessionFactory();
    }

    public static <T> T doInConnection(ReturningWork<T> work) throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/invoice", "kkoltun", "b1rd@tr33")) {
            return work.execute(connection);
        }
    }
}
