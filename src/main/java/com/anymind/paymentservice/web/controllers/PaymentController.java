package com.anymind.paymentservice.web.controllers;

import com.anymind.paymentservice.services.PaymentService;
import com.anymind.paymentservice.web.models.requests.PaymentInput;
import com.anymind.paymentservice.web.models.responses.Payment;
import graphql.schema.DataFetchingEnvironment;
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
import java.util.stream.Collectors;

/**
 * Created by Wilson
 * On 26-12-2022, 10:35
 */
@Controller
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {

    PaymentService paymentService;

    @MutationMapping
    public Payment makePayment(@Argument @Valid PaymentInput newPayment){
        //df.getSelectionSet().getFields().forEach( selectedField -> System.out.println(selectedField.getName()));
        return paymentService.makePayment(newPayment);
    }

    @QueryMapping
    public List<Payment> getPayments(@Argument @Valid @NotBlank String startDateTime, @Argument @Valid @NotBlank String endDateTime){
        return paymentService.getPayments(startDateTime, endDateTime);
    }
}
