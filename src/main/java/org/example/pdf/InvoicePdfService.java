package org.example.pdf;

import io.vavr.control.Option;
import io.vavr.control.Try;
import net.sf.jasperreports.engine.*;
import org.example.international.invoice.InternationalInvoice;
import org.example.invoice.Invoice;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.example.infrastructure.Database.doInConnection;

@Service
public class InvoicePdfService {

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
                .mapTry(InvoicePdfService::compile)
                .mapTry(report -> fill(report, parameters))
                .mapTry(InvoicePdfService::generatePdf)
                .get();
    }

    private static InputStream loadReportTemplate(Template template) {
        return Option.of(InvoicePdfService.class.getResourceAsStream(template.getPath()))
                .getOrElseThrow(InvoicePdfGenerationException::templateNotFoundException);
    }

    private static JasperReport compile(InputStream inputStream) {
        try {
            return JasperCompileManager.compileReport(inputStream);
        } catch (JRException exception) {
            throw InvoicePdfGenerationException.templateCompilationFailed(exception);
        }
    }

    private static JasperPrint fill(JasperReport report, Map<String, Object> parameters) {
        try {
            return doInConnection(connection -> {
                try {
                    return JasperFillManager.fillReport(report, parameters, connection);
                } catch (JRException e) {
                    throw InvoicePdfGenerationException.templateFillingFailed(e);
                }
            });
        } catch (SQLException exception) {
            throw InvoicePdfGenerationException.templateFillingFailed(exception);
        }
    }

    private static byte[] generatePdf(JasperPrint jasperPrint) {
        try {
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException exception) {
            throw InvoicePdfGenerationException.pdfGenerationFailed(exception);
        }
    }
}
