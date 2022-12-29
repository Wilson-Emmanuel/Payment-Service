package com.anymind.paymentservice.persistence.repositories.custom_repositories.impls;

import com.anymind.paymentservice.persistence.repositories.custom_repositories.CustomPaymentMethodRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Wilson
 * On 04-01-2023, 11:55
 */
@Service
public class CustomPaymentMethodRepositoryImpl implements CustomPaymentMethodRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Map<String, Object> getPaymentMethodByName(String name) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getAllPaymentMethods() {
        return null;
    }
}
