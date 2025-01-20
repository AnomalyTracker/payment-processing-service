package com.StripeProject.payments_processing_service_start.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionLogDto {

    private int txnId;
    private String txnFromStatus;
    private String txnToStatus;
}
