package com.StripeProject.payments_processing_service_start.utils;

import com.StripeProject.payments_processing_service_start.constants.TransactionStatusEnum;
import org.modelmapper.AbstractConverter;

public class TxnStatusEnumConverterIntegerToString extends AbstractConverter<Integer, String> {

    @Override
    protected String convert(Integer source) {
        return TransactionStatusEnum.getById(source).getName();
    }
}
