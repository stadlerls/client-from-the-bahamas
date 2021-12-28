package com.example.restservice.Repositories;

import com.example.restservice.Entities.Client;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

}