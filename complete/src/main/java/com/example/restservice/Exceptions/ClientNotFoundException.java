package com.example.restservice.Exceptions;

public class ClientNotFoundException extends RuntimeException{
    public ClientNotFoundException(Long invoice_id) {
        super("Could not find client id: " + invoice_id);
      }

}
