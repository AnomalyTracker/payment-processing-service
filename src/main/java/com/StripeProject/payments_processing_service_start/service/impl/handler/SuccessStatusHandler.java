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
public class SuccessStatusHandler extends PaymentStatusHandler {

    @Autowired
    private CreateTxnRepo createTxnRepo;

    @Autowired
    private TransactionLogRepo transactionLogRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public TransactionDto processStatus(TransactionDto transactionDto) {

        System.out.println("InitiateStatusHandler.processStatus() || "+ "transactionDto: "+transactionDto);
        TransactionEntity txnData = createTxnRepo.findById(transactionDto.getId()).orElseThrow(() -> new RuntimeException("txnData not found with given Data"));
        TransactionDto transactionDto1 = modelMapper.map(txnData,TransactionDto.class);
        String previousTxnStatus = transactionDto1.getTxnStatus();
        TransactionEntity transactionEntity = modelMapper.map(transactionDto, TransactionEntity.class);
        System.out.println("Initiated status handelr: transaction entity to update: "+transactionEntity);
        TransactionEntity savedTxn;
        try{
            savedTxn = createTxnRepo.save(transactionEntity);
        }
        catch (DataIntegrityViolationException exception){
            System.out.println("Unable to save the transaction into the database");
            exception.printStackTrace();
            throw new ProcessingServiceException(ErrorCodeEnum.DUPLICATE_TXN_REF.getErrorCode(), ErrorCodeEnum.DUPLICATE_TXN_REF.getErrorMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("updated transaction data : "+savedTxn);
        System.out.println("updatedf txnId : "+ savedTxn.getId());

        TransactionLogDto transactionLogDto = new TransactionLogDto();
        transactionLogDto.setTxnId(savedTxn.getId());
        transactionLogDto.setTxnFromStatus(previousTxnStatus);
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
