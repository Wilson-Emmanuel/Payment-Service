package com.anymind.paymentservice.persistence.repositories.custom_repositories.impls;

import com.anymind.paymentservice.persistence.dynamic_field_selectors.DPredicate;
import com.anymind.paymentservice.persistence.dynamic_field_selectors.DQueryProcessor;
import com.anymind.paymentservice.persistence.entities.UserEntity;
import com.anymind.paymentservice.persistence.repositories.custom_repositories.CustomUserRepository;
import com.anymind.paymentservice.web.models.responses.PagedData;
import graphql.schema.SelectedField;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Wilson
 * On 04-01-2023, 11:52
 */
@Service
public class CustomUserRepositoryImpl implements CustomUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Map<String, Object> getUserCustomerId(String customerId, List<SelectedField> selectedFields) {
        return fetchSingleBySingleParameter("customerId",customerId,selectedFields);
    }

    @Override
    public Map<String, Object> getUserByEmail(String email, List<SelectedField> selectedFields) {
        return fetchSingleBySingleParameter("email",email,selectedFields);
    }

    @Override
    public Map<String, Object> getUserByUserId(Long id, List<SelectedField> selectedFields) {
        return fetchSingleBySingleParameter("id",id,selectedFields);
    }

    @Override
    public PagedData getCustomers(int pageNumber, int pageSize, List<SelectedField> selectedFields) {
        return new DQueryProcessor<>(selectedFields, entityManager,UserEntity.class)
                .fetchPage(pageNumber, pageSize,"User");
    }

    private Map<String, Object> fetchSingleBySingleParameter(String parameter, Object parameterValue, List<SelectedField> selectedFields){
        DQueryProcessor<UserEntity> processor = new DQueryProcessor<>(selectedFields, entityManager,UserEntity.class);
        DPredicate<UserEntity> predicate = DPredicate.getBuilder(UserEntity.class)
                .equal(parameter,parameterValue)
                .build();
        processor.setPredicate(predicate);
        return processor.fetchOne();
    }
}
