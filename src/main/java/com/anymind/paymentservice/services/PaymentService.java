package com.anymind.paymentservice.services;

import com.anymind.paymentservice.web.models.requests.PaymentInput;
import com.anymind.paymentservice.web.models.responses.PagedData;
import com.anymind.paymentservice.web.models.responses.Payment;
import graphql.schema.SelectedField;

import java.util.List;
import java.util.Map;

/**
 * Created by Wilson
 * On 26-12-2022, 10:41
 */
public interface PaymentService {
    Map<String,Object> makePayment(PaymentInput paymentInput, List<SelectedField> selectedFields);
    List<Payment> getPayments(String startDate, String endDate);
    List<Map<String,Object>> getPayments(String startDate, String endDate, List<SelectedField> selectedFields);
    PagedData getPagedPayments(int page, int size, List<SelectedField> selectedFields);

}
