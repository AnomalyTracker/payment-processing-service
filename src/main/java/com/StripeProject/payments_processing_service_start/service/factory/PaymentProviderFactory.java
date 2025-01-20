package com.StripeProject.payments_processing_service_start.service.factory;

import com.StripeProject.payments_processing_service_start.constants.ProviderEnum;
import com.StripeProject.payments_processing_service_start.service.impl.handler.StripeProviderHandler;
import com.StripeProject.payments_processing_service_start.service.interfaces.ProviderHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentProviderFactory {

    @Autowired
    private StripeProviderHandler stripeProviderHandler;

    public ProviderHandler getProviderHandler(ProviderEnum providerEnum){
        switch (providerEnum){
            case STRIPE -> {
                System.out.println("PaymentProviderFactory.getProviderHandler: "+ stripeProviderHandler);
                return stripeProviderHandler;
            }
            default -> {
                System.out.println("No ProviderEnum is not found");
                return null;
            }
        }
    }
}
