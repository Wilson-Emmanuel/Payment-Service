package com.anymind.paymentservice.persistence.repositories.custom_repositories;

import java.util.List;
import java.util.Map;

public interface CustomPaymentMethodRepository {
    Map<String,Object> getPaymentMethodByName(String name);
    List<Map<String,Object>> getAllPaymentMethods();
}
