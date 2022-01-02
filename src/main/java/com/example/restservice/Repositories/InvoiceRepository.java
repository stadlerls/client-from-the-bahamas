package com.example.restservice.Repositories;

import com.example.restservice.Entities.Invoice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    default Boolean verifyRegistry(Long invoice_id){
        
        if (findById(invoice_id).isPresent()) {
            return true;
        }else{
            return false;
        }

    }
}