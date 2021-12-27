package com.example.restservice;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Client {
	
	@Id
	private long invoice_id;
	private long fiscal_id;
	private String name;
	private String email;

	
	public Client(long invoice_id, long fiscal_id, String name, String email) {
		
		this.invoice_id = invoice_id;
		this.fiscal_id = fiscal_id;
		this.name = name;
		this.email = email;
		
	}
	
	public Client() {
	}

	public long getInvoiceId() {
		return invoice_id;
	}

	public long getFiscalId() {
		return fiscal_id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}
}
