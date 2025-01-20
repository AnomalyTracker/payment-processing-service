package com.StripeProject.payments_processing_service_start.utils;

import com.StripeProject.payments_processing_service_start.constants.ProviderEnum;
import org.modelmapper.AbstractConverter;

public class ProviderEnumConverter extends AbstractConverter<String, Integer> {
    @Override
    protected Integer convert(String source) {
        System.out.println("Converting provider name: " + source);
        ProviderEnum providerEnum = ProviderEnum.getByName(source);
        if (providerEnum == null) {
            System.out.println("ProviderEnum not found for: " + source);
            return null;
        }
        System.out.println("ProviderEnum found: " + providerEnum + " with ID: " + providerEnum.getId());
        return providerEnum.getId();
    }
}
