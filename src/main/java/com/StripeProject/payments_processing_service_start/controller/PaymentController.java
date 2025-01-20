package com.StripeProject.payments_processing_service_start.controller;

import com.StripeProject.payments_processing_service_start.constants.EndPoints;
import com.StripeProject.payments_processing_service_start.dto.req.InitiatePaymentReqDto;
import com.StripeProject.payments_processing_service_start.dto.req.TransactionDto;
import com.StripeProject.payments_processing_service_start.dto.resp.InitiatePaymentRespDto;
import com.StripeProject.payments_processing_service_start.pojo.req.InitiatePaymentReq;
import com.StripeProject.payments_processing_service_start.pojo.resp.CreateTransactionRes;
import com.StripeProject.payments_processing_service_start.pojo.req.Transaction;
import com.StripeProject.payments_processing_service_start.pojo.resp.InitiatePaymentResp;
import com.StripeProject.payments_processing_service_start.service.interfaces.PaymentService;
import com.StripeProject.payments_processing_service_start.service.interfaces.PaymentStatusService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndPoints.v1_payments)
public class PaymentController {

    @Autowired
    private PaymentStatusService statusService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(EndPoints.create)
    public ResponseEntity<CreateTransactionRes> CreatePayment(@RequestBody Transaction transaction){
        System.out.println("**** Starting payment processing");
        TransactionDto transactionDto = modelMapper.map(transaction, TransactionDto.class);
        TransactionDto ResponseFromService = statusService.processPaymentStatus(transactionDto);

        System.out.println("Transaction created with id: "+ResponseFromService.getId());
        System.out.println("PaymentController.CreatePayment() ||"+" transaction :"+transaction+" ResponseFromService:"+ResponseFromService);
        Transaction transaction1 = modelMapper.map(ResponseFromService, Transaction.class);
        CreateTransactionRes createTransactionRes = new CreateTransactionRes();
        createTransactionRes.setId(ResponseFromService.getId());
        createTransactionRes.setTxnStatus(ResponseFromService.getTxnStatus());
        return new ResponseEntity<>(createTransactionRes, HttpStatus.CREATED);
    }


    @PostMapping(EndPoints.INITIATE)
    public ResponseEntity<InitiatePaymentResp> initiatePayment(@PathVariable int id, @RequestBody InitiatePaymentReq initiatePaymentReq) throws JsonProcessingException {

        InitiatePaymentReqDto initiatePaymentReqDto = modelMapper.map(initiatePaymentReq, InitiatePaymentReqDto.class);
        initiatePaymentReqDto.setId(id);
        System.out.println("initiatePaymentReqDto: "+initiatePaymentReqDto);
        InitiatePaymentRespDto initiatePaymentRespDto = paymentService.initiatePayment(initiatePaymentReqDto);
        InitiatePaymentResp initiatePaymentResp = modelMapper.map(initiatePaymentRespDto, InitiatePaymentResp.class);
        System.out.println("paymentService.initiatePayment.response: "+initiatePaymentRespDto + "  initiatePaymentResp: "+initiatePaymentResp);
        return new ResponseEntity<>(initiatePaymentResp, HttpStatus.OK);
    }
}
