package com.StripeProject.payments_processing_service_start.stripeprovider.resp;

import lombok.Data;

@Data
public class ErrorResponse {
    private String errorCode;
    private String errorMessage;
}
