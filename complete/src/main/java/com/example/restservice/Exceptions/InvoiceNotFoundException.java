package com.example.restservice.Exceptions;

public class InvoiceNotFoundException extends RuntimeException{
    InvoiceNotFoundException(Long invoice_id) {
        super("Could not find invoice id: " + invoice_id);
      }

}
