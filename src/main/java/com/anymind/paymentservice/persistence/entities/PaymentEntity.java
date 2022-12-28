package com.anymind.paymentservice.persistence.entities;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wilson
 * On 25-12-2022, 11:31
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "payments")
public class PaymentEntity extends AbstractBaseEntity<Long>{

    @OneToOne(fetch = FetchType.LAZY)
    private UserEntity customer;

    @Column(name = "final_price", scale = 2, nullable = false, columnDefinition = "Decimal(20,2)")
    private BigDecimal finalPrice;

    @Column(name = "price", scale = 2, nullable = false)
    private String price;

    @Column(name = "price_modifier", nullable = false, columnDefinition = "Decimal(20,10)")
    private BigDecimal priceModifier;

    @Column(name = "points")
    @Builder.Default
    private int points = 0;

    @Column(name = "point_modifier", columnDefinition = "Decimal(20,10)")
    private BigDecimal pointModifier;

    @Column(name = "payment_date", nullable = false)
    private Instant paymentDate;

    @OneToOne(fetch = FetchType.EAGER)
    private PaymentMethodEntity paymentMethod;

    @Type(JsonBinaryType.class)
    @Column(name = "additional_item", columnDefinition = "json")
    private Map<String, String> additionalItem = new HashMap<>();
}
