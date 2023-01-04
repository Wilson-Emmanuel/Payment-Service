package com.anymind.paymentservice.web.controllers;

import com.anymind.paymentservice.services.PaymentService;
import com.anymind.paymentservice.web.models.requests.PaymentInput;
import com.anymind.paymentservice.web.models.responses.PagedData;
import graphql.schema.DataFetchingFieldSelectionSet;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

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
    public Map<String, Object> makePayment(@Argument @Valid PaymentInput newPayment, DataFetchingFieldSelectionSet selectionSet){
        return paymentService.makePayment(newPayment, selectionSet.getImmediateFields());
    }

    @QueryMapping
    public List<Map<String,Object>> getPayments(@Argument @Valid @NotBlank String startDateTime, @Argument @Valid @NotBlank String endDateTime, DataFetchingFieldSelectionSet set){
        return paymentService.getPayments(startDateTime, endDateTime,set.getImmediateFields());
    }
    @QueryMapping
    public PagedData getPagedPayments(@Argument @Valid @Min(value = 0) int page, @Argument @Valid @Min(value = 1) int pageSize, DataFetchingFieldSelectionSet selectedSet){
        return paymentService.getPagedPayments(page, pageSize, selectedSet.getFields("data").get(0).getSelectionSet().getImmediateFields());
    }


}
