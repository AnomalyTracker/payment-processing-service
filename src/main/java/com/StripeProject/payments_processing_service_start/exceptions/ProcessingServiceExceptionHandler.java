package com.StripeProject.payments_processing_service_start.exceptions;

import com.StripeProject.payments_processing_service_start.pojo.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class ProcessingServiceExceptionHandler {

    @ExceptionHandler(ProcessingServiceException.class)
    public ResponseEntity<ErrorResponse> handleProcessingServiceException(ProcessingServiceException exception, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(request.getDescription(false),exception.getErrorCode(), exception.getErrorMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
