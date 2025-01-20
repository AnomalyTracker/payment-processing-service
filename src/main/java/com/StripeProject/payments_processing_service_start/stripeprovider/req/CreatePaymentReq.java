package com.StripeProject.payments_processing_service_start.stripeprovider.req;


import lombok.Data;

import java.util.List;

@Data
public class CreatePaymentReq {
    private String txnRef;
    private List<LineItems> lineItems;
    private String successUrl;
    private String cancelUrl;
}
