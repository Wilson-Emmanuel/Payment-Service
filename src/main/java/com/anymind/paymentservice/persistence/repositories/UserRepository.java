package com.anymind.paymentservice.persistence.repositories;

import com.anymind.paymentservice.persistence.entities.UserEntity;
import com.anymind.paymentservice.persistence.repositories.custom_repositories.CustomUserRepository;
import com.anymind.paymentservice.web.models.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Wilson
 * On 28-12-2022, 12:10
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, QuerydslPredicateExecutor<UserEntity>, CustomUserRepository {
    Optional<UserEntity> findByCustomerId(String externalId);
    Page<UserEntity> findAllByRole(UserRole role, Pageable pageable);

    @Query("""
       select 
       case when count(user)> 0 then true else false end 
       from UserEntity user 
       where user.customerId like :customerId or user.email like :email
       """)
    boolean hasUserByCustomerIdOrEmail(@Param("customerId") String customerId, @Param("email") String email);
}
