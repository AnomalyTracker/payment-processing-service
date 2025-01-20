package com.StripeProject.payments_processing_service_start.pojo.error;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private String Path;
    private String errorcode;
    private String errorMessage;

}
