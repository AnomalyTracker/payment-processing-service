package com.StripeProject.payments_processing_service_start.utils;

import com.StripeProject.payments_processing_service_start.constants.TransactionStatusEnum;
import org.modelmapper.AbstractConverter;

public class TxnStatusEnumConverter extends AbstractConverter<String, Integer> {
    @Override
    public Integer convert(String source) {
        return TransactionStatusEnum.getByName(source).getId();
    }
}
