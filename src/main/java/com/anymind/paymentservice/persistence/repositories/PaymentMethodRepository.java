package com.anymind.paymentservice.persistence.repositories;

import com.anymind.paymentservice.persistence.entities.PaymentMethodEntity;
import com.anymind.paymentservice.persistence.repositories.custom_repositories.CustomPaymentMethodRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Created by Wilson
 * On 25-12-2022, 11:52
 */
public interface PaymentMethodRepository extends JpaRepository<PaymentMethodEntity,Long> , CustomPaymentMethodRepository {
    Optional<PaymentMethodEntity> findByName(String methodName);
    boolean existsByName(String methodName);
    @Query("SELECT p.name FROM PaymentMethodEntity p WHERE p.id=?1")
    Optional<String> getPaymentMethodName(Long id);
}
