package com.anymind.paymentservice.services;

import com.anymind.paymentservice.web.models.requests.UserInput;
import com.anymind.paymentservice.web.models.responses.User;
import com.anymind.paymentservice.web.models.responses.UsersPage;

public interface UserService {
    User createUser(UserInput userInput);
    User getUserByExternalId(String externalId);
    User getUserByUserId(Long userId);
    User getUserByEmail(String email);

    UsersPage getCustomers(int page, int size);
}
