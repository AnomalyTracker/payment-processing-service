package com.StripeProject.payments_processing_service_start.service.impl;

import com.StripeProject.payments_processing_service_start.constants.ProviderEnum;
import com.StripeProject.payments_processing_service_start.dao.interfaces.CreateTxnRepo;
import com.StripeProject.payments_processing_service_start.dto.req.InitiatePaymentReqDto;
import com.StripeProject.payments_processing_service_start.dto.req.TransactionDto;
import com.StripeProject.payments_processing_service_start.dto.resp.InitiatePaymentRespDto;
import com.StripeProject.payments_processing_service_start.entity.TransactionEntity;
import com.StripeProject.payments_processing_service_start.service.factory.PaymentProviderFactory;
import com.StripeProject.payments_processing_service_start.service.interfaces.PaymentService;
import com.StripeProject.payments_processing_service_start.service.interfaces.ProviderHandler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private CreateTxnRepo createTxnRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PaymentProviderFactory providerFactory;

    @Override
    public InitiatePaymentRespDto initiatePayment(InitiatePaymentReqDto initiatePaymentReqDto) {

        System.out.println("PaymentServiceImpl.initiatePayment : "+initiatePaymentReqDto);

        TransactionEntity transactionData = createTxnRepo.findById(initiatePaymentReqDto.getId()).orElseThrow(() -> new RuntimeException("No data found with id: "+initiatePaymentReqDto.getId()));
        System.out.println("transactionData = " + transactionData);

        TransactionDto txnDto =modelMapper.map(transactionData, TransactionDto.class);
        System.out.println("Converted Transaction Entity to TxnDto : "+txnDto);


        ProviderEnum providerEnum = ProviderEnum.getByName(txnDto.getProvider());
        System.out.println("ProviderEnum.providerEnum: "+providerEnum);

        ProviderHandler providerHandler = providerFactory.getProviderHandler(providerEnum);
        InitiatePaymentRespDto providerResponse = providerHandler.processPayment(initiatePaymentReqDto,txnDto);
        return providerResponse;
    }
}
