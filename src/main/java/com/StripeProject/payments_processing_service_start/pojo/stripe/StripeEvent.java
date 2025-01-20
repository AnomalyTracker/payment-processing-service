package com.StripeProject.payments_processing_service_start.pojo.stripe;


import lombok.Data;

@Data
public class StripeEvent {

    private String id;

    private String type;

    private StripeData data;
}
