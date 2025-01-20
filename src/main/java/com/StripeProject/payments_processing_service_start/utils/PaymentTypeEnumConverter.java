package com.StripeProject.payments_processing_service_start.utils;

import com.StripeProject.payments_processing_service_start.constants.PaymentTypeEnum;
import org.modelmapper.AbstractConverter;

public class PaymentTypeEnumConverter extends AbstractConverter<String, Integer > {
    @Override
    public Integer convert(String source) {
        return PaymentTypeEnum.getByName(source).getId();
    }
}
