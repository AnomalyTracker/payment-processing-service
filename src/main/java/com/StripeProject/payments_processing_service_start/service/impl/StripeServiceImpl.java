package com.StripeProject.payments_processing_service_start.service.impl;

import com.StripeProject.payments_processing_service_start.constants.TransactionStatusEnum;
import com.StripeProject.payments_processing_service_start.dao.interfaces.CreateTxnRepo;
import com.StripeProject.payments_processing_service_start.dto.req.TransactionDto;
import com.StripeProject.payments_processing_service_start.entity.TransactionEntity;
import com.StripeProject.payments_processing_service_start.pojo.stripe.AsyncPaymentFailed;
import com.StripeProject.payments_processing_service_start.pojo.stripe.AsyncPaymentSuccededData;
import com.StripeProject.payments_processing_service_start.pojo.stripe.SessionCompletedData;
import com.StripeProject.payments_processing_service_start.pojo.stripe.StripeEvent;
import com.StripeProject.payments_processing_service_start.service.interfaces.PaymentStatusService;
import com.StripeProject.payments_processing_service_start.service.interfaces.StripeService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StripeServiceImpl implements StripeService {

    private static final String PAYMENT_STATUS_PAID = "paid";
    private static final String STATUS_COMPLETE = "complete";
    private static final String CHECKOUT_SESSION_COMPLETED = "checkout.session.completed";
    private static final String CHECKOUT_SESSION_ASYNC_PAYMENT_SUCCEEDED = "checkout.session.async_payment_succeeded";
    private static final String CHECKOUT_SESSION_ASYNC_PAYMENT_FAILED = "checkout.session.async_payment_failed";

    @Autowired
    private Gson gson;

    @Autowired
    private CreateTxnRepo createTxnRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PaymentStatusService paymentStatusService;

    @Override
    public void processStripeEvent(StripeEvent event) {
        System.out.println("event = " + event);

        if (CHECKOUT_SESSION_ASYNC_PAYMENT_FAILED.equals(event.getType())){
            System.out.println("Updating failed processing");

            AsyncPaymentFailed eventData = gson.fromJson(event.getData().getObject(), AsyncPaymentFailed.class);

            System.out.println(CHECKOUT_SESSION_ASYNC_PAYMENT_FAILED+" received");

            System.out.println("eventData = " + eventData);

            TransactionEntity txnData = createTxnRepo.findByProviderReference(eventData.getId());

            TransactionDto txnDtoData = modelMapper.map(txnData, TransactionDto.class);

            txnDtoData.setTxnStatus(TransactionStatusEnum.FAILED.getName());
            paymentStatusService.processPaymentStatus(txnDtoData);
            System.out.println("Updated txn as FAILED");
            return;
        }

        if (CHECKOUT_SESSION_ASYNC_PAYMENT_SUCCEEDED.equals(event.getType())){
            System.out.println("Updating Success Processing");

            AsyncPaymentSuccededData eventData = gson.fromJson(event.getData().getObject(), AsyncPaymentSuccededData.class);
            System.out.println(CHECKOUT_SESSION_ASYNC_PAYMENT_SUCCEEDED+" received");

            System.out.println("eventData = " + eventData);

            TransactionEntity txnData = createTxnRepo.findByProviderReference(eventData.getId());

            TransactionDto txnDtoData = modelMapper.map(txnData, TransactionDto.class);

            txnDtoData.setTxnStatus(TransactionStatusEnum.SUCCESS.getName());
            paymentStatusService.processPaymentStatus(txnDtoData);
            System.out.println("Updated txn as SUCCESS");
            return;
        }

        if (CHECKOUT_SESSION_COMPLETED.equals(event.getType())){
            SessionCompletedData eventData = gson.fromJson(
                    event.getData().getObject(), SessionCompletedData.class);

            System.out.println("checkout.session.completed received");
            System.out.println("stripeDataObj:" + eventData);

            if (eventData.getStatus().equals(STATUS_COMPLETE) && eventData.getPayment_status().equals(PAYMENT_STATUS_PAID)){

                System.out.println(" --- SUCCESSFULLY PROCESSED------");

                System.out.println("eventData = " + eventData);

                TransactionEntity txnData = createTxnRepo.findByProviderReference(eventData.getId());

                TransactionDto txnDtoData = modelMapper.map(txnData, TransactionDto.class);

                txnDtoData.setTxnStatus(TransactionStatusEnum.SUCCESS.getName());
                paymentStatusService.processPaymentStatus(txnDtoData);
                System.out.println("Updated txn as SUCCESS");
            }
            return;
        }

        System.out.println("Received eventType:" + event.getType());
    }
}
