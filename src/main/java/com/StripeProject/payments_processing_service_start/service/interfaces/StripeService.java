package com.StripeProject.payments_processing_service_start.service.interfaces;

import com.StripeProject.payments_processing_service_start.pojo.stripe.StripeEvent;

public interface StripeService {


    void processStripeEvent(StripeEvent event);
}
