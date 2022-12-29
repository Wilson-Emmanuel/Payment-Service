package com.anymind.paymentservice.web.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by Wilson
 * On 25-12-2022, 18:12
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Payment {
    private User customer;
    private String finalPrice;
    private BigDecimal priceModifier;
    private String price;
    private int points;
    private BigDecimal pointModifier;
    private String paymentMethod;
    private String datetime;
    private Object additionalItem; //meta data

}
