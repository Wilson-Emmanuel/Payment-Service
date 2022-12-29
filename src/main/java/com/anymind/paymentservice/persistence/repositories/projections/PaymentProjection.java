package com.anymind.paymentservice.persistence.repositories.projections;

import java.math.BigDecimal;
import java.time.Instant;

public interface PaymentProjection
{
    String getFinalPrice();
    String getPrice();
    BigDecimal getPriceModifier();
    int getPoints();
    BigDecimal getPointModifier();
    Object getAdditionalItem();
    Instant getDatetime();

    CustomerProjection getCustomer();
    PaymentMethodProjection getPaymentMethod();

    public interface CustomerProjection{
        String getCustomerId();
        String getFirstName();
        String getLastName();
        String getEmail();
    }

    public interface PaymentMethodProjection{
        String getName();
    }
}
