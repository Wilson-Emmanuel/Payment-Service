package com.anymind.paymentservice.services;

import com.anymind.paymentservice.web.models.requests.UserInput;
import com.anymind.paymentservice.web.models.responses.PagedData;
import com.anymind.paymentservice.web.models.responses.User;
import graphql.schema.SelectedField;

import java.util.List;
import java.util.Map;

public interface UserService {
    User createUser(UserInput userInput);
    Map<String,Object> getUserByCustomerId(String customerId, List<SelectedField> selectedFields);
    User getUserByUserId(Long userId);
    Map<String,Object> getUserByEmail(String email, List<SelectedField> selectedFields);

    PagedData getCustomers(int page, int size, List<SelectedField> selectedFields);
}
