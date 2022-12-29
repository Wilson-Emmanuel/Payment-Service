package com.anymind.paymentservice.web.models.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Created by Wilson
 * On 26-12-2022, 10:28
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentInput {
    @NotBlank(message = "customerId cannot be blank")
    private String customerId;

    @NotBlank(message = "price cannot be blank")
    @Pattern(regexp = "^(?!0*[.,]0*$|[.,]0*$|0*$)\\d+[,.]?\\d{0,2}$", message = "invalid price format")
    private String price;

    @Min(value = 0, message = "price modifier must be positive")
    private BigDecimal priceModifier;

    @NotBlank(message = "payment method must be blank")
    private String paymentMethod;

    @NotBlank(message = "datetime cannot be empty")
    private String datetime;

    private Object additionalItem;
}
