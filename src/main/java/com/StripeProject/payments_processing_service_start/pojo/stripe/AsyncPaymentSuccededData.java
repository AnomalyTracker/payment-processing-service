package com.StripeProject.payments_processing_service_start.pojo.stripe;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class AsyncPaymentSuccededData extends StripeDataObject {
    private String id;
    private String status;
    private String payment_status;
}
