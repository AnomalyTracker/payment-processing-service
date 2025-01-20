package com.StripeProject.payments_processing_service_start.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
public class ProcessingServiceException extends RuntimeException{

    private String errorCode;
    private String errorMessage;
    private HttpStatus httpStatus;
    
}
