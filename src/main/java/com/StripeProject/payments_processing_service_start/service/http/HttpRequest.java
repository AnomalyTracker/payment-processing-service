package com.StripeProject.payments_processing_service_start.service.http;

import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@Data
public class HttpRequest {

    private HttpMethod method;
    private String url;
    private Object requestBody;
    private HttpHeaders headers;
}
