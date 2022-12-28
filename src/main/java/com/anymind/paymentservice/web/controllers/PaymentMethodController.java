package com.anymind.paymentservice.web.controllers;

import com.anymind.paymentservice.services.PaymentMethodService;
import com.anymind.paymentservice.web.models.requests.PaymentMethodInput;
import com.anymind.paymentservice.web.models.responses.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by Wilson
 * On 26-12-2022, 10:36
 */
@Controller
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentMethodController {

    PaymentMethodService paymentMethodService;

    @MutationMapping
    public PaymentMethod createPaymentMethod(@Argument @Valid PaymentMethodInput newPaymentMethod){
        return paymentMethodService.createPaymentMethod(newPaymentMethod);
    }

    @MutationMapping
    public PaymentMethod updatePaymentMethod(@Argument @Valid @NotBlank String methodName, @Argument @Valid PaymentMethodInput updatedPaymentMethod){
        return paymentMethodService.updatePaymentMethod(methodName, updatedPaymentMethod);
    }

    @QueryMapping
    public PaymentMethod getPaymentMethod(@Argument @Valid @NotBlank String methodName){
        return paymentMethodService.getPaymentMethod(methodName);
    }

    @QueryMapping
    public List<PaymentMethod> getPaymentMethods(){
        return paymentMethodService.getAllPaymentMethods();
    }
}
