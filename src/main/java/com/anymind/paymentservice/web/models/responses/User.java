package com.anymind.paymentservice.web.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Wilson
 * On 28-12-2022, 11:28
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    private String customerId;

    private String firstName;
    private String lastName;
    private String email;
    private String role;
}
