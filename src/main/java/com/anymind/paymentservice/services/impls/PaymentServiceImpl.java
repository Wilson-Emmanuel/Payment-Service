package com.anymind.paymentservice.services.impls;

import com.anymind.paymentservice.persistence.entities.PaymentEntity;
import com.anymind.paymentservice.persistence.entities.PaymentMethodEntity;
import com.anymind.paymentservice.persistence.entities.UserEntity;
import com.anymind.paymentservice.persistence.repositories.PaymentMethodRepository;
import com.anymind.paymentservice.persistence.repositories.PaymentRepository;
import com.anymind.paymentservice.persistence.repositories.UserRepository;
import com.anymind.paymentservice.services.PaymentService;
import com.anymind.paymentservice.web.exceptions.BusinessLogicException;
import com.anymind.paymentservice.web.exceptions.ResourceNotFoundException;
import com.anymind.paymentservice.web.models.requests.PaymentInput;
import com.anymind.paymentservice.web.models.responses.PagedData;
import com.anymind.paymentservice.web.models.responses.Payment;
import com.anymind.paymentservice.web.models.responses.PaymentMethod;
import com.anymind.paymentservice.web.models.responses.User;
import graphql.schema.SelectedField;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DateTimeException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Wilson
 * On 26-12-2022, 10:59
 */
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentServiceImpl implements PaymentService {

    PaymentRepository paymentRepository;
    PaymentMethodRepository paymentMethodRepository;
    UserRepository userRepository;
    @Override
    public Map<String, Object> makePayment(PaymentInput input, List<SelectedField> selectedFields) {
        //Ensure customer exist
        UserEntity customer = userRepository.findByCustomerId(input.getCustomerId())
                .orElseThrow(()->new ResourceNotFoundException("Customer does not exist"));

        //Ensure payment method exist
        PaymentMethodEntity paymentMethod = paymentMethodRepository.findByName(input.getPaymentMethod())
                .orElseThrow(() -> new ResourceNotFoundException("Payment method does not exists"));

        //Ensure the price modifier is within accepted range for the payment method
        BigDecimal priceModifier = input.getPriceModifier();
        BigDecimal pointModifier = paymentMethod.getPointModifier();
        BigDecimal price = new BigDecimal(input.getPrice());

        BigDecimal minPriceModifier = paymentMethod.getMinPriceModifier();
        BigDecimal maxPriceModifier = paymentMethod.getMaxPriceModifier();

        if(minPriceModifier.compareTo(priceModifier) > 0 ||
            maxPriceModifier.compareTo(priceModifier) < 0)
        {
            throw new BusinessLogicException("Invalid price modifier. Please re-adjust and try again");
        }

        //Ensure payment date is valid
        Instant paymentDate = null;
        try{
            paymentDate = Instant.parse(input.getDatetime());
        }catch (DateTimeException exception){
            throw new BusinessLogicException("Invalid payment date");
        }

        //Ensure additional Item is valid if available
        boolean requiresAdditionalItem = paymentMethod.isRequiresAdditionalItem();
        if(requiresAdditionalItem && (input.getAdditionalItem() == null)){
            throw new BusinessLogicException("Payment method requires additional item");
        }

        //compute final price and points
        BigDecimal finalPrice = price.multiply(priceModifier).setScale(2, RoundingMode.HALF_EVEN);
        int points = price.multiply(pointModifier).intValue();

        PaymentEntity paymentEntity = PaymentEntity.builder()
                .paymentMethod(paymentMethod)
                .customer(customer)
                .price(input.getPrice())
                .finalPrice(finalPrice)
                .priceModifier(input.getPriceModifier())
                .pointModifier(paymentMethod.getPointModifier())
                .points(points)
                .additionalItem(input.getAdditionalItem())
                .datetime(paymentDate)
                .build();
        paymentRepository.save(paymentEntity);
        return paymentRepository.getPaymentById(paymentEntity.getId(), selectedFields);
    }

    /**
     * Implemented using JPA projection to fetch data
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<Payment> getPayments(String startDate, String endDate) {
        Instant startDateTime = null, endDateTime = null;
        try{
            startDateTime = Instant.parse(startDate);
            endDateTime = Instant.parse(endDate);
        }catch (Exception ignored){}

        if(ObjectUtils.isEmpty(startDateTime) || ObjectUtils.isEmpty(endDateTime) ||
                endDateTime.isBefore(startDateTime)){
            throw new BusinessLogicException("End date cannot be earlier than start date");
        }

        return paymentRepository.findAllByDatetimeBetweenOrderByDatetime(startDateTime, endDateTime)
                .stream()
                .map(paymentProjection -> Payment.builder()
                        .finalPrice(paymentProjection.getFinalPrice())
                        .price(paymentProjection.getPrice())
                        .pointModifier(paymentProjection.getPointModifier())
                        .points(paymentProjection.getPoints())
                        .paymentMethod(
                                PaymentMethod.builder()
                                        .name(paymentProjection.getPaymentMethod().getName())
                                        .build()

                        )
                        .datetime(paymentProjection.getDatetime().toString())
                        .priceModifier(paymentProjection.getPriceModifier())
                        .additionalItem(paymentProjection.getAdditionalItem())
                        .customer(
                                User.builder()
                                        .customerId(paymentProjection.getCustomer().getCustomerId())
                                        .firstName(paymentProjection.getCustomer().getFirstName())
                                        .lastName(paymentProjection.getCustomer().getLastName())
                                        .email(paymentProjection.getCustomer().getEmail())
                                        .build()
                        ).build())
                .collect(Collectors.toList());
    }

    @Override
    public List<Map<String,Object>> getPayments(String startDate, String endDate, List<SelectedField> selectedFields) {
        Instant startDateTime = null, endDateTime = null;
        try{
            startDateTime = Instant.parse(startDate);
            endDateTime = Instant.parse(endDate);
        }catch (Exception ignored){}

        if(ObjectUtils.isEmpty(startDateTime) || ObjectUtils.isEmpty(endDateTime) ||
                endDateTime.isBefore(startDateTime)){
            throw new BusinessLogicException("End date cannot be earlier than start date");
        }
        return paymentRepository.getPaymentsNewImpl(startDateTime,endDateTime, selectedFields);
    }

    @Override
    public PagedData getPagedPayments(int page, int size, List<SelectedField> selectedFields) {
        return paymentRepository.getPagedPayments(page, size, selectedFields);
    }

}
