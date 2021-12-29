package com.example.restservice.Exceptions;

public class InvoiceNotFoundException extends RuntimeException{
    public InvoiceNotFoundException(Long invoice_id) {
        super("Could not find invoice id: " + invoice_id);
      }

}
