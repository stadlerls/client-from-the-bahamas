package com.example.restservice.Repositories;

import com.example.restservice.Entities.Client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientRepository extends JpaRepository<Client, Long>{
    
    @Query("select c from client c where c.name = ?1")
    Client findByName(String name);

    default Boolean verifyRegistry(String name){
        Client client = new Client();
        try {
            client = findByName(name);
            if(null != client){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            //TODO: handle exception
            return false;
        }

    }

    default Client insertClient(Client client){
        
        try {
            save(client);
        }
        catch (Exception e) {
            //TODO: handle exception
        }
        return client;
    }

}