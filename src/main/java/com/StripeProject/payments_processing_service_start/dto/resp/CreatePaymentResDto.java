package com.StripeProject.payments_processing_service_start.dto.resp;

import lombok.Data;

@Data
public class CreatePaymentResDto {
    private String txnRef;
    private String id;
    private String url;
}
