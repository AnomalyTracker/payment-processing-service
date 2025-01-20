package com.StripeProject.payments_processing_service_start.dao.interfaces;

import com.StripeProject.payments_processing_service_start.entity.TransactionLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionLogRepo extends JpaRepository<TransactionLogEntity, Integer> {
}
