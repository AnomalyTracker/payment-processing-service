package com.StripeProject.payments_processing_service_start.stripeprovider.resp;

import lombok.Data;

@Data
public class CreatePaymentRes {
    private String id;
    private String url;
}
