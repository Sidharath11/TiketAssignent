package com.tiketbakend.tiket.util;

import com.tiketbakend.tiket.model.mongodb.Stock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public class ApiCall {
    public void makePostApiCall(String url, Stock data){
        RestTemplate restTemplate = new RestTemplate();
       final String baseURL="http://localhost:8080/api/v1/stock";
        URI uri = null;
        try {
            uri = new URI(baseURL+url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        ResponseEntity<String> result = restTemplate.postForEntity(uri, data, String.class);

        //Verify request succeed
        org.junit.Assert.assertEquals (201, result.getStatusCodeValue());
    }
    public void makePutApiCall(String url, Stock data){
        RestTemplate restTemplate = new RestTemplate();
        final String baseURL="http://localhost:8080/api/v1/stock";
        URI uri = null;
        try {
            uri = new URI(baseURL+url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        HttpEntity<Stock> requestUpdate = new HttpEntity<>(data);
        System.out.println(restTemplate.exchange(uri, HttpMethod.PUT, requestUpdate, Stock.class));
    }
}
