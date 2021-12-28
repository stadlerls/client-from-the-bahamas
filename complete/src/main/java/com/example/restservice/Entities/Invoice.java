package com.example.restservice.Entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name="invoice")
@Table(name="invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
    private long fiscal_id;
    
    @OneToMany(mappedBy = "clientId", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinTable(name = "CLIENT" , joinColumns = @JoinColumn(name = "clientId"))
    private List<Client> clients = new ArrayList<Client>();
    
    public Invoice(long id, long fiscal_id, List<Client> clients){

        this.id = id;
        this.fiscal_id = fiscal_id;
        this.clients = clients;
        
    }

    public Invoice(){

    }

    public long getinvoiceId() {
		return id;
	}

    public long getFiscalId() {
		return fiscal_id;
	}

    public List<Client> getClients(){
        return clients;
    }

}