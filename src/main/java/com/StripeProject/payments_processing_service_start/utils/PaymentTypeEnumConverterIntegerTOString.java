package com.StripeProject.payments_processing_service_start.utils;

import com.StripeProject.payments_processing_service_start.constants.PaymentTypeEnum;
import org.modelmapper.AbstractConverter;

public class PaymentTypeEnumConverterIntegerTOString extends AbstractConverter<Integer, String > {
    @Override
    public String convert(Integer source) {
        return PaymentTypeEnum.getById(source).getName();
    }
}
