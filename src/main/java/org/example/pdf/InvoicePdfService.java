package org.example.pdf;

import io.vavr.control.Option;
import io.vavr.control.Try;
import net.sf.jasperreports.engine.*;
import org.example.international.invoice.InternationalInvoice;
import org.example.invoice.Invoice;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@Service
public class InvoicePdfService {

    private final SessionFactory sessionFactory;

    public InvoicePdfService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private static JasperPrint fillReport(JasperReport report, Map<String, Object> parameters, Connection connection) {
        try {
            return JasperFillManager.fillReport(report, parameters, connection);
        } catch (Exception exception) {
            throw InvoicePdfGenerationException.templateFillingFailed(exception);
        }
    }

    public byte[] generate(Invoice invoice) {
        return generate(invoice.getId(), Template.INVOICE);
    }

    public byte[] generate(InternationalInvoice invoice) {
        return generate(invoice.getId(), Template.INTERNATIONAL_INVOICE);
    }

    private byte[] generate(Integer id, Template template) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("invoice_id", id);

        return Try.of(() -> loadReportTemplate(template))
                .mapTry(this::compile)
                .mapTry(report -> fill(report, parameters))
                .mapTry(this::generatePdf)
                .get();
    }

    private InputStream loadReportTemplate(Template template) {
        return Option.of(InvoicePdfService.class.getResourceAsStream(template.getPath()))
                .getOrElseThrow(InvoicePdfGenerationException::templateNotFoundException);
    }

    private JasperReport compile(InputStream inputStream) {
        try {
            return JasperCompileManager.compileReport(inputStream);
        } catch (JRException exception) {
            throw InvoicePdfGenerationException.templateCompilationFailed(exception);
        }
    }

    private JasperPrint fill(JasperReport report, Map<String, Object> parameters) {
        return sessionFactory.fromSession(session -> session.doReturningWork(connection -> fillReport(report, parameters, connection)));
    }

    private byte[] generatePdf(JasperPrint jasperPrint) {
        try {
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException exception) {
            throw InvoicePdfGenerationException.pdfGenerationFailed(exception);
        }
    }

    private enum Template {
        INVOICE("/jasper/invoice.jrxml"),
        INTERNATIONAL_INVOICE("/jasper/international_invoice.jrxml");

        private final String pathToTemplate;

        Template(String pathToTemplate) {
            this.pathToTemplate = pathToTemplate;
        }

        String getPath() {
            return pathToTemplate;
        }
    }
}
