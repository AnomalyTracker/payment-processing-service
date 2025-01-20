package com.StripeProject.payments_processing_service_start.utils;

import com.StripeProject.payments_processing_service_start.constants.PaymentMethodEnum;
import org.modelmapper.AbstractConverter;

public class PaymentMethodEnumConverterIntegerToString extends AbstractConverter<Integer, String> {
    @Override
    protected String convert(Integer source) {
        return PaymentMethodEnum.getById(source).getName();
    }
}
