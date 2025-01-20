package com.StripeProject.payments_processing_service_start.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Transaction")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false)
    private int userId;
    @Column
    private int paymentMethodId;
    @Column
    private int providerId;
    @Column
    private int paymentTypeId;
    @Column
    private int txnStatusId;
    @Column
    private double amount;
    @Column
    private String currency;
    @Column
    private String merchantTransactionReference;
    @Column(unique = true, nullable = false)
    private String txnReference;
    @Column
    private String providerReference;
    @Column
    private String providerCode;
    @Column
    private String providerMessage;
    @Column
    private int retryCount;
}
