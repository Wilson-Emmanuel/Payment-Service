package com.anymind.paymentservice.services;

import com.anymind.paymentservice.web.models.requests.PaymentInput;
import com.anymind.paymentservice.web.models.responses.Payment;

import java.util.List;

/**
 * Created by Wilson
 * On 26-12-2022, 10:41
 */
public interface PaymentService {
    Payment makePayment(PaymentInput paymentInput);
    List<Payment> getPayments(String startDate, String endDate);
}
