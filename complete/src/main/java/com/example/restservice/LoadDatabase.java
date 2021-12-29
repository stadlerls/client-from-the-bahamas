package com.example.restservice;

import java.util.Arrays;
import java.util.List;

import com.example.restservice.Entities.Client;
import com.example.restservice.Entities.Invoice;
import com.example.restservice.Repositories.ClientRepository;
import com.example.restservice.Repositories.InvoiceRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
  
    @Bean
    CommandLineRunner initDatabase(ClientRepository ClientRepository, InvoiceRepository InvoiceRepository ) {
      
      Client client1 = new Client(1, "Bilbo Baggins", "bilbo@shire.com");
      Client client2 = new Client(2, "Frodo Baggins", "frodo@shire.com");
      Client client3 = new Client(3, "Legolas", "legolas@elf.com");
      Client client4 = new Client(4, "Aragorn", "aragorn@human.com");
      Client client5 = new Client(5, "Gandalf", "gandalf@shallnotpass.com");
      Client client6 = new Client(6, "Gollum", "gollum@precius.com");

      return args -> {
        //Generate Clients
        log.info("Preloading " + ClientRepository.save(client1));
        log.info("Preloading " + ClientRepository.save(client2));
        log.info("Preloading " + ClientRepository.save(client3));
        log.info("Preloading " + ClientRepository.save(client4));
        log.info("Preloading " + ClientRepository.save(client5));
        log.info("Preloading " + ClientRepository.save(client6));
        
        
        //Generate Invoice
        List<Client> clientsList1 = Arrays.asList(client1, client2, client3);
        List<Client> clientsList2 = Arrays.asList(client3, client5, client4);
        List<Client> clientsList3 = Arrays.asList(client1, client6, client5);
      
        log.info(clientsList1.toString());
        log.info("Preloading " + InvoiceRepository.save(new Invoice(1, 1, clientsList1)));
        log.info("Preloading " + InvoiceRepository.save(new Invoice(2, 2, clientsList2)));
        log.info("Preloading " + InvoiceRepository.save(new Invoice(3, 3, clientsList3)));       
        
      };
    }
}