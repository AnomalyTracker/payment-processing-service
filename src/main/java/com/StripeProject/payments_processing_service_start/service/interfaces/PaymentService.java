package com.StripeProject.payments_processing_service_start.service.interfaces;

import com.StripeProject.payments_processing_service_start.dto.req.InitiatePaymentReqDto;
import com.StripeProject.payments_processing_service_start.dto.resp.InitiatePaymentRespDto;
import org.springframework.stereotype.Component;

@Component
public interface PaymentService {


    public InitiatePaymentRespDto initiatePayment(InitiatePaymentReqDto initiatePaymentReqDto);
}
