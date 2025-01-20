package com.StripeProject.payments_processing_service_start.service.impl.handler;

import com.StripeProject.payments_processing_service_start.constants.ErrorCodeEnum;
import com.StripeProject.payments_processing_service_start.dao.interfaces.CreateTxnRepo;
import com.StripeProject.payments_processing_service_start.dao.interfaces.TransactionLogRepo;
import com.StripeProject.payments_processing_service_start.dto.req.TransactionDto;
import com.StripeProject.payments_processing_service_start.dto.req.TransactionLogDto;
import com.StripeProject.payments_processing_service_start.entity.TransactionEntity;
import com.StripeProject.payments_processing_service_start.entity.TransactionLogEntity;
import com.StripeProject.payments_processing_service_start.exceptions.ProcessingServiceException;
import com.StripeProject.payments_processing_service_start.service.interfaces.PaymentStatusHandler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CreatedStatusHandler extends PaymentStatusHandler {

    public static final String TXN_FROM_STATUS = "-";

    @Autowired
    private CreateTxnRepo createTxnRepo;

    @Autowired
    private TransactionLogRepo transactionLogRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public TransactionDto  processStatus(TransactionDto transactionDto) {

        System.out.println("CreatedStatusHandler.processStatus() || "+ "transactionDto: "+transactionDto);
        TransactionEntity transactionEntity = modelMapper.map(transactionDto, TransactionEntity.class);
        TransactionEntity savedTxn;
        try{
            savedTxn = createTxnRepo.save(transactionEntity);
        }
        catch (DataIntegrityViolationException exception){
            System.out.println("Unable to save the transaction into the database");
            exception.printStackTrace();
            throw new ProcessingServiceException(ErrorCodeEnum.DUPLICATE_TXN_REF.getErrorCode(), ErrorCodeEnum.DUPLICATE_TXN_REF.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        System.out.println("Transaction is created in Data base with transactionEntity : "+savedTxn);
        System.out.println("Created txnId : "+ savedTxn.getId());
        transactionDto.setId(savedTxn.getId());

        TransactionLogDto transactionLogDto = new TransactionLogDto();
        transactionLogDto.setTxnId(savedTxn.getId());
        transactionLogDto.setTxnFromStatus(TXN_FROM_STATUS);
        transactionLogDto.setTxnToStatus(transactionDto.getTxnStatus());
        TransactionLogEntity transactionLogEntity = modelMapper.map(transactionLogDto, TransactionLogEntity.class);
        TransactionLogEntity savedTxnLog;
        try{
            savedTxnLog = transactionLogRepo.save(transactionLogEntity);
        } catch (Exception e) {
            System.out.println("Unable to save the TransactionLog into the database");
            throw new RuntimeException(e);
        }
        System.out.println("TransactionLog for Creation Status  is created in Data base with transactionLogEntity : "+savedTxnLog);
        System.out.println("Created txnLogId : "+ savedTxnLog.getId());
        return transactionDto;
    }

}
