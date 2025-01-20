package com.StripeProject.payments_processing_service_start.utils;

import com.StripeProject.payments_processing_service_start.constants.PaymentMethodEnum;
import org.modelmapper.AbstractConverter;

public class PaymentMethodEnumConverter extends AbstractConverter<String, Integer > {
    @Override
    public Integer convert(String source) {
        return PaymentMethodEnum.getByName(source).getId();
    }
}
