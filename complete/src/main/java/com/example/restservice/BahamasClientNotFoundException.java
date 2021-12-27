package com.example.restservice;

public class BahamasClientNotFoundException extends RuntimeException{
    BahamasClientNotFoundException(Long invoice_id) {
        super("Could not find client " + invoice_id);
      }

}
