package com.example.restservice;
import java.util.List;

import com.example.restservice.Entities.Client;
import com.example.restservice.Entities.Invoice;
import com.example.restservice.Entities.InvoiceDetails;
import com.example.restservice.Exceptions.ClientNotFoundException;
import com.example.restservice.Exceptions.InvoiceNotFoundException;
import com.example.restservice.Repositories.ClientRepository;
import com.example.restservice.Repositories.InvoiceDetailsRepository;
import com.example.restservice.Repositories.InvoiceRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoreBahamasController {
	
	private final ClientRepository clientrepository;
	private final InvoiceRepository invoicerepository;
	private final InvoiceDetailsRepository invoiceDetailsrepository;

	StoreBahamasController(ClientRepository Clientrepository, InvoiceRepository InvoiceRepository, InvoiceDetailsRepository InvoiceDetailsrepository) {
		this.clientrepository = Clientrepository;
		this.invoicerepository = InvoiceRepository;
		this.invoiceDetailsrepository = InvoiceDetailsrepository;
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

	@GetMapping("/findClientByName/{clientName}")
	Client findClientByName(@PathVariable String clientName) {
		
		try {
			return clientrepository.findByName(clientName);
		} catch (Exception e) {
			new Exception("Client not found");
		}
		return null;

	}

	//Invoice
	@GetMapping("/invoices")
	List<Invoice> allInvoices() {
		return invoicerepository.findAll();
	}
	
	@PostMapping("/store-bahamas-client/{invoice_id}")
	@ResponseBody
	Invoice storeBahamasClient(
		@PathVariable Long invoice_id,
		@RequestParam(name = "fiscal_id") String fiscal_id, 
		@RequestParam(name = "name") String name,
		@RequestParam(name = "email") String email) throws Exception {
			
			Invoice invoice = new Invoice();
			Client client = new Client();
			
			invoice.setinvoiceId(invoice_id);
			invoice.setfiscalId(Long.parseLong(fiscal_id));

			client.setName(name);
			client.setEmail(email);

			if (invoicerepository.findById(invoice_id).isPresent()) {
				
				Client clientExists = new Client();
				try {
					clientExists = findClientByName(name);
				} catch (Exception e) {
					//TODO: handle exception
				}
				if(null != clientExists){
					return invoicerepository.findById(invoice_id).orElseThrow(() -> new InvoiceNotFoundException(invoice_id));
				}else{
					Client clientSuccess = new Client();
					try {
						clientSuccess = addClient(client);
					}
					catch (Exception e) {
						//TODO: handle exception
					}
					if(null != clientSuccess){
						InvoiceDetails invoiceDetailsSucess = new InvoiceDetails();
						//Inverter método para retornar detalhes ao invés do Invoice
						try {
							invoiceDetailsSucess = addInvoiceDetails(invoice, clientSuccess);
						} catch (Exception e) {
							//TODO: handle exception
						}
						if(null != invoiceDetailsSucess){
							return invoicerepository.save(invoice);
						}
					}
				}
			}else{
				Client clientExists = new Client();
				try {
					clientExists = findClientByName(name);
				} catch (Exception e) {
					//TODO: handle exception
				}
				if(null == clientExists){
					try {
						Client clientSuccess = new Client();
						clientSuccess = addClient(client);
						if(null != clientSuccess){
							//Insert Details da Invoice
							InvoiceDetails invoiceDetailsSucess = new InvoiceDetails();
							//Inverter método para retornar detalhes ao invés do Invoice
							try {
								invoiceDetailsSucess = addInvoiceDetails(invoice, clientSuccess);
							} catch (Exception e) {
								//TODO: handle exception
							}
							if(null != invoiceDetailsSucess){
								return invoicerepository.save(invoice);
							}
						}
					} catch (Exception e) {
						//TODO: handle exception
					}
				}
				return invoicerepository.save(invoice);
			}
			return invoicerepository.findById(invoice_id).orElseThrow(() -> new InvoiceNotFoundException(invoice_id));		
	}

	Client addClient(Client client)throws Exception {

		return clientrepository.save(client);

	}

	InvoiceDetails addInvoiceDetails(Invoice invoice, Client client)throws Exception {

		InvoiceDetails invoiceDetails = new InvoiceDetails();

		return invoiceDetailsrepository.save(invoiceDetails);

	}

	@GetMapping("/retrieve-bahamas-client/{invoice_id}")
	Invoice findInvoiceById(@PathVariable Long invoice_id) {
		
		return invoicerepository.findById(invoice_id).orElseThrow(() -> new InvoiceNotFoundException(invoice_id));
	}


}
