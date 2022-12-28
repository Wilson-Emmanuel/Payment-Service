package com.anymind.paymentservice.web.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class PaymentMethod {
    private String name;
    private BigDecimal minPriceModifier;
    private BigDecimal maxPriceModifier;
    private BigDecimal pointModifier;
    private boolean requiresAdditionalItem;

}
