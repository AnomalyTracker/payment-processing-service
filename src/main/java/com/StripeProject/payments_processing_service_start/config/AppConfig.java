package com.StripeProject.payments_processing_service_start.config;

import com.StripeProject.payments_processing_service_start.dto.req.TransactionDto;
import com.StripeProject.payments_processing_service_start.entity.TransactionEntity;
import com.StripeProject.payments_processing_service_start.utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.logging.log4j.Logger;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public Gson getGson(){
        return new Gson();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Define converters for custom conversion
        Converter<String, Integer> paymentMethodEnumConverter = new PaymentMethodEnumConverter();
        Converter<String, Integer> providerEnumConverter = new ProviderEnumConverter();
        Converter<String, Integer> txnStatusEnumConverter = new TxnStatusEnumConverter();
        Converter<String, Integer> paymentTypeEnumConverter = new PaymentTypeEnumConverter();
        Converter<Integer, String> paymentMethodEnumConverterIntToSt = new PaymentMethodEnumConverterIntegerToString();
        Converter<Integer, String> providerEnumConverterIntToSt = new ProviderEnumConverterIntegerTOString();
        Converter<Integer, String> txnStatusEnumConverterIntToSt = new TxnStatusEnumConverterIntegerToString();
        Converter<Integer, String> paymentTypeEnumConverterIntToSt = new PaymentTypeEnumConverterIntegerTOString();

        // Custom mapping for TransactionDto to TransactionEntity
        modelMapper.addMappings(new PropertyMap<TransactionDto, TransactionEntity>() {
            @Override
            protected void configure() {
                // Map enum values
                using(paymentMethodEnumConverter).map(source.getPaymentMethod(), destination.getPaymentMethodId());
                using(providerEnumConverter).map(source.getProvider(), destination.getProviderId());
                using(txnStatusEnumConverter).map(source.getTxnStatus(), destination.getTxnStatusId());
                using(paymentTypeEnumConverter).map(source.getPaymentType(), destination.getPaymentTypeId());
            }
        });

        // Custom mapping for TransactionEntity to TransactionDto
        modelMapper.addMappings(new PropertyMap<TransactionEntity, TransactionDto>() {
            @Override
            protected void configure() {
                // Map IDs to their enum values
                using(paymentMethodEnumConverterIntToSt).map(source.getPaymentMethodId(), destination.getPaymentMethod());
                using(providerEnumConverterIntToSt).map(source.getProviderId(), destination.getProvider());
                using(txnStatusEnumConverterIntToSt).map(source.getTxnStatusId(), destination.getTxnStatus());
                using(paymentTypeEnumConverterIntToSt).map(source.getPaymentTypeId(), destination.getPaymentType());
            }
        });

        return modelMapper;
    }

}
