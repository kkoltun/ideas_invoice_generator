package org.example;

import org.example.company.Company;
import org.example.international.invoice.InternationalInvoice;
import org.example.invoice.Invoice;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import static java.lang.Boolean.TRUE;
import static org.hibernate.cfg.JdbcSettings.*;

@Configuration
class HibernateConfiguration {

    @Bean
    public SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration()
                .addAnnotatedClass(Invoice.class)
                .setProperty(JAKARTA_JDBC_USER, "kkoltun")
                .setProperty(JAKARTA_JDBC_PASSWORD, "b1rd@tr33")
                .setProperty(JAKARTA_JDBC_URL, "jdbc:postgresql://localhost:5432/invoices")
                .setProperty(CONNECTION_PROVIDER, "org.hibernate.hikaricp.internal.HikariCPConnectionProvider")
                .setProperty(SHOW_SQL, TRUE.toString())
                .setProperty(FORMAT_SQL, TRUE.toString())
                .setProperty(HIGHLIGHT_SQL, TRUE.toString())
                .addAnnotatedClass(Invoice.class)
                .addAnnotatedClass(Company.class)
                .addAnnotatedClass(InternationalInvoice.class)
                .buildSessionFactory();
    }
}
