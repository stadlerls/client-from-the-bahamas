package com.example.restservice.Entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="invoice")
@Table(name="invoice")
public class Invoice {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long invoice_id;
    private long fiscal_id;
    
    //@OneToMany(mappedBy = "invoice_id", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinTable(name = "CLIENT" , joinColumns = @JoinColumn(name = "clientId"))
    //private List<Client> clients = new ArrayList<Client>();
    
    public Invoice(long invoice_id, long fiscal_id){

        this.invoice_id = invoice_id;
        this.fiscal_id = fiscal_id;
        
    }

    public Invoice(){

    }

    public long getinvoiceId() {
		return invoice_id;
	}

    public long getFiscalId() {
		return fiscal_id;
	}

    public void setinvoiceId(long invoiceId){
        this.invoice_id = invoiceId;
    }

    public void setfiscalId(long fiscalId){
        this.fiscal_id = fiscalId;
    }

}