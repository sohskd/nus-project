package com.nus.team3.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.nus.team3.dto.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class HttpSender {

    public final String ROOT_URL = "http://localhost:8080";
    public final String SAVE_TXN_URL = "/transaction/saveTxn";

    public HttpSender() {
    }

    public void sendOrder(Order o){
        String url = ROOT_URL + SAVE_TXN_URL;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity httpEntity = new HttpEntity<>(o.toString(), httpHeaders);
        ResponseEntity result = restTemplate.exchange(url, HttpMethod.POST, httpEntity, JsonNode.class);
    }
}
