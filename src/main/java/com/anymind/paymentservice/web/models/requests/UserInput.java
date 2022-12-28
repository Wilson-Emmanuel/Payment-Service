package com.anymind.paymentservice.web.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Wilson
 * On 28-12-2022, 11:58
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserInput {

    @NotBlank(message = "customer id cannot be blank")
    private String userId;

    @NotBlank(message = "customer first name cannot be blank")
    private String firstName;

    @NotBlank(message = "customer last name cannot be blank")
    private String lastName;

    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Invalid email format")
    @NotBlank(message = "email cannot be blank")
    private String email;

    @Pattern(regexp = "^CUSTOMER|ADMIN$", message = "Only CUSTOMER or ADMIN are allowed")
    @NotBlank(message = "role cannot be blank")
    private String role;
}
