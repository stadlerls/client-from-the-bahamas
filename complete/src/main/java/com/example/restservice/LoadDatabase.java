package com.example.restservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
  
    @Bean
    CommandLineRunner initDatabase(StoreBahamasRepository repository) {
  
      return args -> {
        log.info("Preloading " + repository.save(new Client(1234, 999999999, "Bilbo Baggins", "bilbo@shire.com")));
        log.info("Preloading " + repository.save(new Client(1235, 999999988, "Frodo Baggins", "frodo@shire.com")));
      };
    }
}