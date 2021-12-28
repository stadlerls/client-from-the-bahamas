package com.example.restservice;
import java.util.Arrays;
import java.util.List;

import com.example.restservice.Entities.Client;
import com.example.restservice.Entities.Invoice;
import com.example.restservice.Exceptions.ClientNotFoundException;
import com.example.restservice.Repositories.ClientRepository;
import com.example.restservice.Repositories.InvoiceRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoreBahamasController {
	
	private final ClientRepository clientrepository;
	private final InvoiceRepository invoicerepository;

	StoreBahamasController(ClientRepository Clientrepository, InvoiceRepository InvoiceRepository) {
		this.clientrepository = Clientrepository;
		this.invoicerepository = InvoiceRepository;
	}
	
	//Client
	@GetMapping("/clients")
	List<Client> allClients() {
		return clientrepository.findAll();
	}

	@GetMapping("/findClientById/{clientId}")
	Client findClientById(@PathVariable Long clientId) {
		
		return clientrepository.findById(clientId).orElseThrow(() -> new ClientNotFoundException(clientId));
	}

	//Invoice
	@GetMapping("/invoices")
	List<Invoice> allInvoices() {
		return invoicerepository.findAll();
	}

	@GetMapping("/findInvoiceById/{invoice_id}")
	Invoice findInvoiceById(@PathVariable Long invoice_id) {
		
		return invoicerepository.findById(invoice_id).orElseThrow(() -> new ClientNotFoundException(invoice_id));
	}


}
