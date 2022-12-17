package org.example.pdf;

import io.vavr.control.Option;
import io.vavr.control.Try;
import net.sf.jasperreports.engine.*;
import org.example.international.invoice.InternationalInvoice;
import org.example.invoice.Invoice;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
public class InvoicePdfService {

    private final DataSource dataSource;

    public InvoicePdfService(DataSource dataSource) {
        this.dataSource = dataSource;
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
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            return JasperFillManager.fillReport(report, parameters, connection);
        } catch (Exception exception) {
            throw InvoicePdfGenerationException.templateFillingFailed(exception);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private byte[] generatePdf(JasperPrint jasperPrint) {
        try {
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException exception) {
            throw InvoicePdfGenerationException.pdfGenerationFailed(exception);
        }
    }
}
