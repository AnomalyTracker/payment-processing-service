package com.StripeProject.payments_processing_service_start.utils;

import com.StripeProject.payments_processing_service_start.constants.ProviderEnum;
import org.modelmapper.AbstractConverter;

public class ProviderEnumConverterIntegerTOString extends AbstractConverter<Integer, String> {
    @Override
    protected String convert(Integer source) {
        return ProviderEnum.getById(source).getName();
    }
}
