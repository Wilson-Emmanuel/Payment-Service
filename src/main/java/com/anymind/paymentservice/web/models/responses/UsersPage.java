package com.anymind.paymentservice.web.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Wilson
 * On 28-12-2022, 14:21
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UsersPage {
    private int currentPage;
    private int pageSize;
    private int totalPages;
    private List<User> users;
}
