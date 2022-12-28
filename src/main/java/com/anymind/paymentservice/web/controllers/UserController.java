package com.anymind.paymentservice.web.controllers;

import com.anymind.paymentservice.services.UserService;
import com.anymind.paymentservice.web.models.requests.UserInput;
import com.anymind.paymentservice.web.models.responses.User;
import com.anymind.paymentservice.web.models.responses.UsersPage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

/**
 * Created by Wilson
 * On 28-12-2022, 14:23
 */
@Controller
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @MutationMapping
    public User createUser(@Argument @Valid UserInput newUser){
        return userService.createUser(newUser);
    }

    @QueryMapping
    public User  getCustomerById(@Argument @Valid @NotBlank String customerId){
        return userService.getUserByExternalId(customerId);
    }

    @QueryMapping
    public UsersPage getCustomers(@Argument @Valid @Min(value = 0) int page, @Argument @Valid @Min(value = 1) int pageSize){
        return userService.getCustomers(page, pageSize);
    }
}
