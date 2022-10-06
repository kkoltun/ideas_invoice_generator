package org.example.pdf;

import net.sf.jasperreports.engine.*;
import org.example.invoice.Invoice;
import org.example.invoice.InvoiceService;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

@Service
public class InvoicePdfService {

    private final InvoiceService invoiceService;

    public InvoicePdfService(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    public byte[] generate(String invoiceNumber) {
        Invoice invoice = invoiceService.getInvoice(invoiceNumber);

        if (invoice == null) {
            throw new InvoicePdfGenerationException(String.format("Invoice with number [%s] does not exist.", invoiceNumber));
        }

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("invoice_id", invoice.getId());

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(this.getClass().getResourceAsStream("/jasper/invoice.jrxml"));

            // todo infrastructure should be extracted out
            Class.forName("org.postgresql.Driver");

            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/invoice", "kkoltun", "b1rd@tr33");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
            connection.close();

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new InvoicePdfGenerationException(e);
        }
    }
}
