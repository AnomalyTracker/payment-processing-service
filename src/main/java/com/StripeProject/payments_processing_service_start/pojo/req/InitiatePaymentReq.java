package com.StripeProject.payments_processing_service_start.pojo.req;

import lombok.Data;

import java.util.List;

@Data
public class InitiatePaymentReq {

    private List<LineItems> lineItems;
    private String successUrl;
    private String cancelUrl;
}
