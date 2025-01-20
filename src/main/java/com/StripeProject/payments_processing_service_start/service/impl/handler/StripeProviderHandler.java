package com.StripeProject.payments_processing_service_start.service.impl.handler;

import com.StripeProject.payments_processing_service_start.constants.ErrorCodeEnum;
import com.StripeProject.payments_processing_service_start.constants.TransactionStatusEnum;
import com.StripeProject.payments_processing_service_start.dto.req.InitiatePaymentReqDto;
import com.StripeProject.payments_processing_service_start.dto.req.TransactionDto;
import com.StripeProject.payments_processing_service_start.dto.resp.CreatePaymentResDto;
import com.StripeProject.payments_processing_service_start.dto.resp.InitiatePaymentRespDto;
import com.StripeProject.payments_processing_service_start.exceptions.ProcessingServiceException;
import com.StripeProject.payments_processing_service_start.service.http.HttpServiceEngine;
import com.StripeProject.payments_processing_service_start.service.http.HttpRequest;
import com.StripeProject.payments_processing_service_start.service.interfaces.PaymentStatusService;
import com.StripeProject.payments_processing_service_start.service.interfaces.ProviderHandler;
import com.StripeProject.payments_processing_service_start.stripeprovider.req.CreatePaymentReq;
import com.StripeProject.payments_processing_service_start.stripeprovider.resp.CreatePaymentRes;
import com.StripeProject.payments_processing_service_start.stripeprovider.resp.ErrorResponse;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

@Component
public class StripeProviderHandler implements ProviderHandler {

    @Autowired
    private HttpServiceEngine httpServiceEngine;

    @Autowired
    private PaymentStatusService statusService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Gson gson;

    @Value("${stripeprovider.payments.createpayment}")
    private String stripeCreatePaymentUrl;

    @Override
    public InitiatePaymentRespDto processPayment(InitiatePaymentReqDto initiatePaymentReqDto, TransactionDto transactionDto) {

        System.out.println("StripeProviderHandler.processPayment initiatePaymentReqDto:"+initiatePaymentReqDto+" transactionDto: "+transactionDto);

        HttpRequest httpRequest = getHttpRequest(initiatePaymentReqDto, transactionDto);

        transactionDto.setTxnStatus(TransactionStatusEnum.INITIATED.getName());
        statusService.processPaymentStatus(transactionDto);

        ResponseEntity<String> httpResponse = httpServiceEngine.makeHttpRequest(httpRequest);
        System.out.println("httpResponse = " + httpResponse);
        return getInitiatePaymentRespDto(transactionDto, httpResponse);
    }

    private InitiatePaymentRespDto getInitiatePaymentRespDto(TransactionDto transactionDto, ResponseEntity<String> httpResponse) {
        if (httpResponse.getStatusCode() == HttpStatus.CREATED) {
            CreatePaymentRes createPaymentRes = gson.fromJson(httpResponse.getBody(), CreatePaymentRes.class);

            System.out.println("createPaymentRes = " + createPaymentRes);
            InitiatePaymentRespDto initiatePaymentRespDto = modelMapper.map(createPaymentRes, InitiatePaymentRespDto.class);
            initiatePaymentRespDto.setTxnReference(transactionDto.getTxnReference());
            System.out.println("Success: " + httpResponse.getBody());
            transactionDto.setTxnStatus(TransactionStatusEnum.PENDING.getName());
            statusService.processPaymentStatus(transactionDto);

            return initiatePaymentRespDto;
        }
        else if (httpResponse.getStatusCode().is4xxClientError() || httpResponse.getStatusCode().is5xxServerError()){

            ErrorResponse errorResponseFromStripe = gson.fromJson(httpResponse.getBody(), ErrorResponse.class);
            throw new ProcessingServiceException(errorResponseFromStripe.getErrorCode(), errorResponseFromStripe.getErrorMessage(), HttpStatus.valueOf(httpResponse.getStatusCode().value()));
        }
        else
            throw new ProcessingServiceException(ErrorCodeEnum.GENERIC_ERROR.getErrorCode(),ErrorCodeEnum.GENERIC_ERROR.getErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private HttpRequest getHttpRequest(InitiatePaymentReqDto initiatePaymentReqDto, TransactionDto transactionDto) {
        CreatePaymentReq createPaymentReq = modelMapper.map(initiatePaymentReqDto, CreatePaymentReq.class);
        createPaymentReq.setTxnRef(transactionDto.getTxnReference());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setRequestBody(createPaymentReq);
        httpRequest.setUrl(stripeCreatePaymentUrl);
        httpRequest.setMethod(HttpMethod.POST);
        httpRequest.setHeaders(headers);
        return httpRequest;
    }
}
