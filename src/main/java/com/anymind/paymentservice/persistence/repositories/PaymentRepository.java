package com.anymind.paymentservice.persistence.repositories;

import com.anymind.paymentservice.persistence.entities.PaymentEntity;
import com.anymind.paymentservice.persistence.repositories.custom_repositories.CustomPaymentRepository;
import com.anymind.paymentservice.persistence.repositories.projections.PaymentProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

/**
 * Created by Wilson
 * On 25-12-2022, 11:55
 */
public interface PaymentRepository extends JpaRepository<PaymentEntity,Long>, CustomPaymentRepository {
    List<PaymentProjection> findAllByDatetimeBetweenOrderByDatetime(Instant fromDate, Instant toDate);
}
