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
	@Column(name = "clientId", updatable = false)
	private long clientId;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "email", nullable = false)
	private String email;
	
	public Client(long clientId, String name, String email) {
		
		this.clientId = clientId;
		this.name = name;
		this.email = email;
	}
	
	public Client() {
	}

	public long getClientId() {
		return clientId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

}
