package com.anymind.paymentservice.persistence.repositories;

import com.anymind.paymentservice.persistence.entities.PaymentMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Wilson
 * On 25-12-2022, 11:52
 */
public interface PaymentMethodRepository extends JpaRepository<PaymentMethodEntity,Long> {
    Optional<PaymentMethodEntity> findByName(String methodName);
    boolean existsByName(String methodName);
}
