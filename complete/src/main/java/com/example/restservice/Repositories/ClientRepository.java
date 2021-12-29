package com.example.restservice.Repositories;

import com.example.restservice.Entities.Client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientRepository extends JpaRepository<Client, Long>{
    
    @Query("select c from client c where c.name = ?1")
    Client findByName(String name);

}