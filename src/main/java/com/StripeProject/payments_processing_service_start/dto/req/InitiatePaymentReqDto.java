package com.StripeProject.payments_processing_service_start.dto.req;

import com.StripeProject.payments_processing_service_start.pojo.req.LineItems;
import lombok.Data;

import java.util.List;

@Data
public class InitiatePaymentReqDto {
    private int id;
    private List<LineItemsDto> lineItems;
    private String successUrl;
    private String cancelUrl;
}
