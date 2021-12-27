package com.example.restservice;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoreBahamasController {
	
	private final StoreBahamasRepository repository;

	StoreBahamasController(StoreBahamasRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/store-bahamas-client/{invoice_id}")
	Client one(@PathVariable Long invoice_id) {
		
		return repository.findById(invoice_id).orElseThrow(() -> new BahamasClientNotFoundException(invoice_id));
	}

	@GetMapping("/clients")
	List<Client> all() {
		return repository.findAll();
	}

}
