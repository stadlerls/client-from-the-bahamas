package com.example.restservice;

import com.example.restservice.Entities.Client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {

    private final RestTemplate restTemplate;

    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Client bahamasRegister(Long invoice, Long fiscal_id, String name, String email) {
        String url = "https://bahamas.gov/register?invoice="
        +invoice+"&fiscal_id="+fiscal_id+"&name="+name+"&email="+email;
        //return this.restTemplate.getForObject(url, String.class);
        Client clientRegistered = new Client();
        return clientRegistered;
    }
}