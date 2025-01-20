package com.StripeProject.payments_processing_service_start.dao.interfaces;

import com.StripeProject.payments_processing_service_start.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreateTxnRepo extends JpaRepository<TransactionEntity, Integer>{

    TransactionEntity findByProviderReference(String id);
}
