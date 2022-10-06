package org.example.pdf;

public class InvoicePdfGenerationException extends RuntimeException {
    public InvoicePdfGenerationException(Throwable cause) {
        super(cause);
    }

    public InvoicePdfGenerationException(String message) {
        super(message);
    }
}
