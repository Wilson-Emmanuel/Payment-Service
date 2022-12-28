package com.anymind.paymentservice.persistence.entities;

import com.anymind.paymentservice.web.models.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

/**
 * Created by Wilson
 * On 28-12-2022, 11:33
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "users")
public class UserEntity extends AbstractBaseEntity<Long>{
    @Column(name = "external_id", unique = true, nullable = false)
    private String externalId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserRole role = UserRole.CUSTOMER;
}
