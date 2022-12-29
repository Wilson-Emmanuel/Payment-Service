package com.anymind.paymentservice.persistence.repositories.custom_repositories;

import com.anymind.paymentservice.web.models.responses.PagedData;
import graphql.schema.SelectedField;

import java.time.Instant;
import java.util.List;
import java.util.Map;


public interface CustomPaymentRepository {
    List<Map<String,Object>> getPaymentsNewImpl(Instant startDate, Instant endDate, List<SelectedField> selectedFields);
    Map<String,Object> getPaymentById(Long id, List<SelectedField> selectedFields);
    PagedData getPagedPayments(int pageNumber, int pageSize, List<SelectedField> selectedFields);

}
