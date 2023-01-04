package com.anymind.paymentservice.persistence.repositories.custom_repositories;

import com.anymind.paymentservice.web.models.responses.PagedData;
import graphql.schema.SelectedField;

import java.util.List;
import java.util.Map;

public interface CustomUserRepository {
    Map<String,Object> getUserCustomerId(String customerId, List<SelectedField> selectedFields);
    Map<String, Object> getUserByEmail(String email, List<SelectedField> selectedFields);
    Map<String, Object> getUserByUserId(Long id, List<SelectedField> selectedFields);
    PagedData getCustomers(int pageNumber, int pageSize, List<SelectedField> selectedFields);
}
