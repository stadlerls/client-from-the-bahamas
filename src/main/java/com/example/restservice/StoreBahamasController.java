package com.example.restservice;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;

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
	private RestService restService;

	StoreBahamasController(
		ClientRepository Clientrepository,
		InvoiceRepository InvoiceRepository, 
		InvoiceDetailsRepository InvoiceDetailsrepository, 
		RestService RestService) {
			
		this.clientrepository = Clientrepository;
		this.invoicerepository = InvoiceRepository;
		this.invoiceDetailsrepository = InvoiceDetailsrepository;
		this.restService = RestService;
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

	@PostMapping("/store-client/")
	@ResponseBody
	public void storeClient( 
		@RequestParam(name = "name") String name,
		@RequestParam(name = "email") String email) throws IOException, ServletException{

			try {
				Client client = new Client();
				client.setName(name);
				client.setEmail(email);
				clientrepository.save(client);

			} catch (Exception e) {
				//TODO: handle exception
			}

	}

	//Invoice
	@GetMapping("/invoices")
	List<Invoice> allInvoices() {
		return invoicerepository.findAll();
	}

	@GetMapping("/invoicesDetails")
	List<InvoiceDetails> allInvoicesDetails() {
		return invoiceDetailsrepository.findAll();
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

			client.setName(name);
			client.setEmail(email);

			if (invoicerepository.verifyRegistry(invoice_id)) {
				if(clientrepository.verifyRegistry(name)){

					invoice = invoicerepository.getById(invoice_id);
					Iterator<Client> clientsList = invoice.getClients().iterator();
					boolean clientInList = false;
					while(clientsList.hasNext()) {
						if(clientsList.next().getEmail().equals(email)){
							clientInList = true;
						}
					}
					if(clientInList){
						return invoicerepository.findById(invoice_id).orElseThrow(() -> new InvoiceNotFoundException(invoice_id));
					}else{
						Client ExistingClient = clientrepository.findByName(name);
						invoice = invoicerepository.getById(invoice_id);
						invoice.getClients().add(ExistingClient);
						return invoicerepository.save(new Invoice(invoice_id, Long.parseLong(fiscal_id), invoice.getClients()));
					}
				}else{
					try {
						Client newClient = clientrepository.save(client);
						invoice = invoicerepository.getById(invoice_id);
						invoice.getClients().add(newClient);
						return invoicerepository.save(new Invoice(invoice_id, Long.parseLong(fiscal_id), invoice.getClients()));
					} catch (Exception e) {
						//TODO: handle exception
					}
				}
			}else{
				if(!clientrepository.verifyRegistry(name)){
					try {
						Client newClient = clientrepository.save(client);
						Client clientRegistered = restService.bahamasRegister(invoice_id, Long.parseLong(fiscal_id), name, email);
						List<Client> clientsList = Arrays.asList(newClient);	
						return invoicerepository.save(new Invoice(invoice_id, Long.parseLong(fiscal_id), clientsList));
					} catch (Exception e) {
						//TODO: handle exception
					}
					
				}else{

					try {
						Client ExistingClient = clientrepository.findByName(name);
						List<Client> clientsList = Arrays.asList(ExistingClient);	
						return invoicerepository.save(new Invoice(invoice_id, Long.parseLong(fiscal_id), clientsList));
					} catch (Exception e) {
						//TODO: handle exception
					}

				}
				return invoicerepository.save(invoice);
			}
			return invoicerepository.findById(invoice_id).orElseThrow(() -> new InvoiceNotFoundException(invoice_id));		
	}

	@GetMapping("/retrieve-bahamas-client/{invoice_id}")
	Invoice findInvoiceById(@PathVariable Long invoice_id) {
		
		return invoicerepository.findById(invoice_id).orElseThrow(() -> new InvoiceNotFoundException(invoice_id));
	}
}
