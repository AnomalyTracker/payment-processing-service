package com.StripeProject.payments_processing_service_start.dto.req;

import lombok.Data;

@Data
public class LineItemsDto {

    private int quantity;
    private String currency;
    private String productName;
    private double unitAmount;
}
