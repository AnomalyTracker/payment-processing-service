package com.StripeProject.payments_processing_service_start.pojo.resp;

import lombok.Data;

@Data
public class CreateTransactionRes {
    private int id;
    private String txnStatus;
}
