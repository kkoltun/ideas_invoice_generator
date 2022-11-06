package org.example.pdf;

import net.sf.jasperreports.engine.JRException;

public class InvoicePdfGenerationException extends RuntimeException {

    public static InvoicePdfGenerationException templateNotFoundException() {
        return new InvoicePdfGenerationException("Invoice template has not been found.");
    }

    public static InvoicePdfGenerationException templateCompilationFailed(Throwable exception) {
        return new InvoicePdfGenerationException("Template compilation failed.", exception);
    }

    public static InvoicePdfGenerationException templateFillingFailed(Throwable exception) {
        return new InvoicePdfGenerationException("Template filling failed.", exception);
    }

    public static InvoicePdfGenerationException pdfGenerationFailed(JRException exception) {
        return new InvoicePdfGenerationException("PDF generation failed.", exception);
    }

    public InvoicePdfGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvoicePdfGenerationException(Throwable cause) {
        super(cause);
    }

    public InvoicePdfGenerationException(String message) {
        super(message);
    }
}
