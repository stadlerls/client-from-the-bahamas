package com.example.restservice.Repositories;
import com.example.restservice.Entities.InvoiceDetails;

import org.springframework.data.jpa.repository.JpaRepository;
public interface InvoiceDetailsRepository extends JpaRepository<InvoiceDetails, Long> {

}