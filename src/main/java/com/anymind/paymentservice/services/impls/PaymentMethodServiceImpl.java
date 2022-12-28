package com.anymind.paymentservice.services.impls;

import com.anymind.paymentservice.persistence.entities.PaymentMethodEntity;
import com.anymind.paymentservice.persistence.repositories.PaymentMethodRepository;
import com.anymind.paymentservice.services.PaymentMethodService;
import com.anymind.paymentservice.web.exceptions.BusinessLogicException;
import com.anymind.paymentservice.web.exceptions.ResourceAlreadyExistException;
import com.anymind.paymentservice.web.models.requests.PaymentMethodInput;
import com.anymind.paymentservice.web.models.responses.PaymentMethod;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Wilson
 * On 26-12-2022, 10:57
 */
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentMethodServiceImpl implements PaymentMethodService {

    PaymentMethodRepository paymentMethodRepository;

    @Override
    public PaymentMethod createPaymentMethod(PaymentMethodInput paymentMethodInput) {
        if(paymentMethodRepository.existsByName(paymentMethodInput.getName())){
            throw new ResourceAlreadyExistException("Payment method already exists");
        }

        BigDecimal min = paymentMethodInput.getMinPriceModifier();
        BigDecimal max = paymentMethodInput.getMaxPriceModifier();

        //check whether the range is valid
        if(min.compareTo(max) > 0){
            throw  new BusinessLogicException("Invalid price modifier range.");
        }

        PaymentMethodEntity paymentMethodEntity = PaymentMethodEntity.builder()
                .name(paymentMethodInput.getName())
                .maxPriceModifier(paymentMethodInput.getMaxPriceModifier())
                .minPriceModifier(paymentMethodInput.getMinPriceModifier())
                .pointModifier(paymentMethodInput.getPointModifier())
                .requiresAdditionalItem(paymentMethodInput.isRequiresAdditionalItem())
                .build();
        return fromEntity(paymentMethodRepository.save(paymentMethodEntity));
    }

    @Override
    public PaymentMethod updatePaymentMethod(String methodName, PaymentMethodInput paymentMethodInput) {

        BigDecimal min = paymentMethodInput.getMinPriceModifier();
        BigDecimal max = paymentMethodInput.getMaxPriceModifier();

        //check whether the range is valid
        if(min.compareTo(max) > 0){
            throw  new BusinessLogicException("Invalid price modifier range.");
        }


        PaymentMethodEntity paymentMethodEntity = paymentMethodRepository.findByName(methodName)
                .orElseThrow(()-> new ResourceAlreadyExistException("Payment method does not exist"));

        paymentMethodEntity.setMaxPriceModifier(max);
        paymentMethodEntity.setMinPriceModifier(min);
        paymentMethodEntity.setPointModifier(paymentMethodInput.getPointModifier());
        paymentMethodEntity.setRequiresAdditionalItem(paymentMethodInput.isRequiresAdditionalItem());

        return fromEntity(paymentMethodRepository.save(paymentMethodEntity));
    }

    @Override
    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodRepository.findAll()
                .stream().map(this::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentMethod getPaymentMethod(String methodName) {
        PaymentMethodEntity paymentMethodEntity = paymentMethodRepository.findByName(methodName)
                .orElseThrow(()-> new ResourceAlreadyExistException("Payment method does not exist"));
        return this.fromEntity(paymentMethodEntity);
    }

    private PaymentMethod fromEntity(PaymentMethodEntity entity){
        return PaymentMethod.builder()
                .name(entity.getName())
                .maxPriceModifier(entity.getMaxPriceModifier())
                .minPriceModifier(entity.getMinPriceModifier())
                .pointModifier(entity.getPointModifier())
                .requiresAdditionalItem(entity.isRequiresAdditionalItem())
                .build();
    }
}
