package com.example.restservice.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name="invoiceDetails")
@Table(name="invoiceDetails")
public class InvoiceDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoiceDetails_id", updatable = false)
	private long invoiceDetails_id;
    
    @JoinColumn(name = "invoice_id", referencedColumnName = "invoice_id", insertable = false, updatable = false)
    @ManyToOne
    Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "client_id")
    Client client;
    
    public long getInvoiceDetailsId() {
		return invoiceDetails_id;
	}

    public Invoice getInvoice() {
		return invoice;
	}

    public Client getClient() {
		return client;
	}

    public void setInvoiceDetailsId(long invoiceDetailsId){
        this.invoiceDetails_id = invoiceDetailsId;
    }

    public void setInvoice(Invoice invoice){
        this.invoice = invoice;
    }

    public void setClient(Client client){
        this.client = client;
    }

}
