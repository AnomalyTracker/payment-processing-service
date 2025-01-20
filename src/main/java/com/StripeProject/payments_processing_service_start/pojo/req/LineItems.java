package com.StripeProject.payments_processing_service_start.pojo.req;

import lombok.Data;

@Data
public class LineItems {

    private int quantity;
    private String currency;
    private String productName;
    private double unitAmount;
}
