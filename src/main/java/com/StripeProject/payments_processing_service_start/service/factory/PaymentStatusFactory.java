package com.StripeProject.payments_processing_service_start.service.factory;

import com.StripeProject.payments_processing_service_start.constants.TransactionStatusEnum;
import com.StripeProject.payments_processing_service_start.service.impl.handler.*;
import com.StripeProject.payments_processing_service_start.service.interfaces.PaymentStatusHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentStatusFactory {
    @Autowired
    private CreatedStatusHandler createdStatusHandler;

    @Autowired
    private InitiateStatusHandler initiateStatusHandler;

    @Autowired
    private PendingStatusHandler pendingStatusHandler;

    @Autowired
    private FailledStatusHandler failledStatusHandler;

    @Autowired
    private SuccessStatusHandler successStatusHandler;

    public PaymentStatusHandler getStatusHandler(TransactionStatusEnum statusEnum){
        switch (statusEnum){
            case CREATED -> {System.out.println("statusEnum ||"+statusEnum);
            System.out.println("createdStatusHandler ||"+createdStatusHandler);
            return createdStatusHandler;
        }
            case INITIATED -> {
                System.out.println("statusEnum = " + statusEnum);
                System.out.println("initiateStatusHandler || "+initiateStatusHandler);
                return initiateStatusHandler;
            }
            case PENDING -> {
                System.out.println("statusEnum = " + statusEnum);
                System.out.println("pendingStatusHandler || "+pendingStatusHandler);
                return pendingStatusHandler;
            }
            case FAILED -> {
                System.out.println("statusEnum = " + statusEnum);
                System.out.println("failledStatusHandler || "+failledStatusHandler);
                return failledStatusHandler;
            }
            case SUCCESS -> {
                System.out.println("statusEnum = " + statusEnum);
                System.out.println("successStatusHandler || "+successStatusHandler);
                return successStatusHandler;
            }
            default -> {
                System.out.println("No statusEnum is not found");
                return null;
            }
        }
    }
}
