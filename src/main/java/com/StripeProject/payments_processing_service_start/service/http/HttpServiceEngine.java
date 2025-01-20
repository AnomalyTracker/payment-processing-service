package com.StripeProject.payments_processing_service_start.service.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.*;

@Component
public class HttpServiceEngine {

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<String> makeHttpRequest(HttpRequest httpRequest) {

        try{
            HttpHeaders headers = httpRequest.getHeaders();
            if (headers == null) {
                headers = new HttpHeaders();
            }
            HttpEntity<Object> requestEntity = new HttpEntity<>(httpRequest.getRequestBody(), headers);
            ResponseEntity<String> httpResponse =restTemplate.exchange(httpRequest.getUrl(), httpRequest.getMethod(), requestEntity, String.class);
            // Make the HTTP request
            return httpResponse;
        }catch (HttpClientErrorException | HttpServerErrorException e) {
            // Log the exception or handle it as needed
            System.out.println("Got Client/Server error while making httpcall: " + e.getMessage());
            e.printStackTrace();
            // Return a ResponseEntity indicating failure
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }catch (ResourceAccessException e) {
            System.out.println("Got Timeout while making http call: "+ e);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(e.getMessage());
        }catch (Exception e) {
            System.out.println("Exception making http call: "+ e);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    public void handleResponse(ResponseEntity<String> response) {
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Success: " + response.getBody());
        } else {
            System.out.println("Failure: Status Code " + response.getStatusCode() + ", Body: " + response.getBody());
        }
    }
}

