package com.anymind.paymentservice.persistence.repositories.custom_repositories.impls;

import com.anymind.paymentservice.persistence.dynamic_field_selectors.DPredicate;
import com.anymind.paymentservice.persistence.dynamic_field_selectors.DQueryProcessor;
import com.anymind.paymentservice.persistence.entities.PaymentEntity;
import com.anymind.paymentservice.persistence.repositories.custom_repositories.CustomPaymentRepository;
import com.anymind.paymentservice.web.models.responses.PagedData;
import graphql.schema.SelectedField;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Created by Wilson
 * On 29-12-2022, 17:57
 */
@Service
public class CustomPaymentRepositoryImpl implements CustomPaymentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Map<String, Object>> getPaymentsNewImpl(Instant startDate, Instant endDate, List<SelectedField> selectedFields) {

        DQueryProcessor<PaymentEntity> processor = new DQueryProcessor<>(selectedFields, entityManager, PaymentEntity.class);
        DPredicate<PaymentEntity> predicate = DPredicate.getBuilder(PaymentEntity.class)
                .between("datetime", startDate, endDate)
                .build();
        processor.setPredicate(predicate);
        return processor.fetchAll();
    }

    @Override
    public Map<String, Object> getPaymentById(Long id, List<SelectedField> selectedFields) {
        return fetchSingleBySingleParameter("id",id,selectedFields);
    }

    @Override
    public PagedData getPagedPayments(int pageNumber, int pageSize, List<SelectedField> selectedFields) {
        return new DQueryProcessor<>(selectedFields, entityManager, PaymentEntity.class)
                .fetchPage(pageNumber, pageSize,"Payment");
    }

    private Map<String, Object> fetchSingleBySingleParameter(String parameter, Object parameterValue, List<SelectedField> selectedFields){
        DQueryProcessor<PaymentEntity> processor = new DQueryProcessor<>(selectedFields, entityManager,PaymentEntity.class);
        DPredicate<PaymentEntity> predicate = DPredicate.getBuilder(PaymentEntity.class)
                .equal(parameter,parameterValue)
                .build();
        processor.setPredicate(predicate);
        return processor.fetchOne();
    }
}
