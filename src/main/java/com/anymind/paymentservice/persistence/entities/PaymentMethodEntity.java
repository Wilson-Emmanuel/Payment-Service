package com.anymind.paymentservice.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Created by Wilson
 * On 25-12-2022, 10:53
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "payment_methods")
public class PaymentMethodEntity extends AbstractBaseEntity<Long>{

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "min_price_modifier", columnDefinition = "Decimal(20,10)  default '1.00'")
    @Builder.Default
    private BigDecimal minPriceModifier = BigDecimal.ONE;

    @Column(name = "max_price_modifier", columnDefinition = "Decimal(20,10)  default '1.00'")
    @Builder.Default
    private BigDecimal maxPriceModifier = BigDecimal.ONE;

    @Column(name = "point_modifier",columnDefinition = "Decimal(20,10)  default '0.00'")
    @Builder.Default
    private BigDecimal pointModifier = BigDecimal.ZERO;

    @Column(name = "requires_additional_item")
    @Builder.Default
    private boolean requiresAdditionalItem = false;
}
