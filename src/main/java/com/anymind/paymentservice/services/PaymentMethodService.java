package com.anymind.paymentservice.services;

import com.anymind.paymentservice.web.models.requests.PaymentMethodInput;
import com.anymind.paymentservice.web.models.responses.PaymentMethod;

import java.util.List;
/**
 * Created by Wilson
 * On 25-12-2022, 18:12
 */
public interface PaymentMethodService {
    PaymentMethod createPaymentMethod(PaymentMethodInput paymentMethodInput);
    PaymentMethod updatePaymentMethod(String methodName, PaymentMethodInput paymentMethodInput);
    List<PaymentMethod> getAllPaymentMethods();
    PaymentMethod getPaymentMethod(String methodName);
}
