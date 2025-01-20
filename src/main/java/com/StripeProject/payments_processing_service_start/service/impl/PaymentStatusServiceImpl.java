package com.StripeProject.payments_processing_service_start.service.impl;

import com.StripeProject.payments_processing_service_start.constants.ErrorCodeEnum;
import com.StripeProject.payments_processing_service_start.constants.TransactionStatusEnum;
import com.StripeProject.payments_processing_service_start.dto.req.TransactionDto;
import com.StripeProject.payments_processing_service_start.exceptions.ProcessingServiceException;
import com.StripeProject.payments_processing_service_start.service.factory.PaymentStatusFactory;
import com.StripeProject.payments_processing_service_start.service.interfaces.PaymentStatusHandler;
import com.StripeProject.payments_processing_service_start.service.interfaces.PaymentStatusService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class PaymentStatusServiceImpl implements PaymentStatusService {
    @Autowired
    private PaymentStatusFactory paymentStatusFactory;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public TransactionDto processPaymentStatus(TransactionDto transactionDto) {
        System.out.println("PaymentStatusServiceImpl.processPaymentStatus() ||transactionDto: "+transactionDto);

        TransactionStatusEnum statusEnum=TransactionStatusEnum.getByName(transactionDto.getTxnStatus());
        System.out.println("status: "+statusEnum);

        if (statusEnum == null){
            System.out.println("NULL statusEnum for the txnStatus: "+transactionDto.getTxnStatus());
            throw new ProcessingServiceException(ErrorCodeEnum.INVALID_Txn_STATUS.getErrorCode(),ErrorCodeEnum.INVALID_Txn_STATUS.getErrorMessage(), HttpStatus.BAD_REQUEST );
        }


        PaymentStatusHandler statusHanlder = paymentStatusFactory.getStatusHandler(statusEnum);

        if (statusHanlder == null){
            System.out.println("NULL statusEnum for the txnStatus: "+paymentStatusFactory.getStatusHandler(statusEnum));
            throw new ProcessingServiceException(ErrorCodeEnum.UNCONFIGURED_STATUS_HANDELR.getErrorCode(), ErrorCodeEnum.UNCONFIGURED_STATUS_HANDELR.getErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        System.out.println("statusHanlder = " + statusHanlder);

        return statusHanlder.processStatus(transactionDto);
    }
}
