package com.StripeProject.payments_processing_service_start.constants;


import lombok.Getter;

public enum ErrorCodeEnum {
    GENERIC_ERROR("2000","Try again later"),
    INVALID_Txn_STATUS("2001","Invalid Request, Incorrect 'txnStatus' recevied"),
    DUPLICATE_TXN_REF("2002", "Invalid request, Duplicate Entry of txnRef"),
    UNCONFIGURED_STATUS_HANDELR("2003","For the given status Handler is not configured");
    @Getter
    private String errorCode;

    @Getter
    private String errorMessage;

    ErrorCodeEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
