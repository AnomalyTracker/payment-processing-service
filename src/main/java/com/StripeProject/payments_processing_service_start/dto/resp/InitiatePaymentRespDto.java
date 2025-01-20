package com.StripeProject.payments_processing_service_start.dto.resp;

import lombok.Data;

@Data
public class InitiatePaymentRespDto {

    private String txnReference;
    private String id;
    private String url;
}
