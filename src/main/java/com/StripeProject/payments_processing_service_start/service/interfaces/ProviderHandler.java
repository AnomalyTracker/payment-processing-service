package com.StripeProject.payments_processing_service_start.service.interfaces;

import com.StripeProject.payments_processing_service_start.dto.req.InitiatePaymentReqDto;
import com.StripeProject.payments_processing_service_start.dto.req.TransactionDto;
import com.StripeProject.payments_processing_service_start.dto.resp.InitiatePaymentRespDto;

public interface ProviderHandler {

    public InitiatePaymentRespDto processPayment(InitiatePaymentReqDto initiatePaymentReqDto, TransactionDto transactionDto);
}
