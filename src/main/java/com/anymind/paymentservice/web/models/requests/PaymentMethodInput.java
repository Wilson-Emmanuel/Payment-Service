package com.anymind.paymentservice.web.models.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Created by Wilson
 * On 25-12-2022, 18:18
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentMethodInput {
    @NotBlank(message = "payment method name cannot be blank")
    private String name;

    @Min(value = 0, message = "min price modifier must be positive")
    private BigDecimal minPriceModifier;

    @Min(value = 0, message = "max price modifier must be positive")
    private BigDecimal maxPriceModifier;

    @Min(value = 0, message = "point modifier must be positive")
    private BigDecimal pointModifier;

    private boolean requiresAdditionalItem;
}
