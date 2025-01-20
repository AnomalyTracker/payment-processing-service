package com.StripeProject.payments_processing_service_start.service.interfaces;

import com.StripeProject.payments_processing_service_start.dto.req.TransactionDto;
import org.springframework.stereotype.Component;

@Component
public abstract class PaymentStatusHandler {
    public abstract TransactionDto processStatus(TransactionDto transaction);
}
