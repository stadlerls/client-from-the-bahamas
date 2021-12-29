package com.example.restservice.Entities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="client")
@Table(name="client")
public class Client {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "client_id", updatable = false)
	private long client_id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "email", nullable = false)
	private String email;
	
	public Client(long client_id, String name, String email) {
		
		this.client_id = client_id;
		this.name = name;
		this.email = email;
	}
	
	public Client() {
	}

	public long getClient_id() {
		return client_id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public void setClient_id(Long client_id) {
		 this.client_id = client_id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
