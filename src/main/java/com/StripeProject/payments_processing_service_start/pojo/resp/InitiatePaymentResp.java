package com.StripeProject.payments_processing_service_start.pojo.resp;

import lombok.Data;

@Data
public class InitiatePaymentResp {

    private String txnReference;
    private String url;
}
