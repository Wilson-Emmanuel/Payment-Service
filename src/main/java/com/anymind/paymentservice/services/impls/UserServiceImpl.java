package com.anymind.paymentservice.services.impls;

import com.anymind.paymentservice.persistence.entities.UserEntity;
import com.anymind.paymentservice.persistence.repositories.UserRepository;
import com.anymind.paymentservice.services.UserService;
import com.anymind.paymentservice.web.exceptions.ResourceAlreadyExistException;
import com.anymind.paymentservice.web.exceptions.ResourceNotFoundException;
import com.anymind.paymentservice.web.models.enums.UserRole;
import com.anymind.paymentservice.web.models.requests.UserInput;
import com.anymind.paymentservice.web.models.responses.PagedData;
import com.anymind.paymentservice.web.models.responses.User;
import graphql.schema.SelectedField;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Wilson
 * On 28-12-2022, 12:09
 */
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    @Override
    public User createUser(UserInput userInput) {
        if(userRepository.hasUserByCustomerIdOrEmail(userInput.getCustomerId(), userInput.getEmail())){
            throw new ResourceAlreadyExistException("User already exists");
        }
        UserEntity userEntity = UserEntity.builder()
                .customerId(userInput.getCustomerId())
                .firstName(userInput.getFirstName())
                .lastName(userInput.getLastName())
                .email(userInput.getEmail())
                .role(UserRole.valueOf(userInput.getRole()))
                .build();
        return fromEntity(userRepository.save(userEntity));
    }

    @Override
    public Map<String, Object> getUserByCustomerId(String customerId, List<SelectedField> selectedFields) {
        return userRepository.getUserCustomerId(customerId, selectedFields);
    }

    @Override
    public User getUserByUserId(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User ID does not exists"));
        return fromEntity(userEntity);
    }

    @Override
    public Map<String, Object> getUserByEmail(String email, List<SelectedField> selectedFields) {
        return userRepository.getUserByEmail(email, selectedFields);
    }

    @Override
    public PagedData getCustomers(int page, int size, List<SelectedField> selectedFields) {
        return userRepository.getCustomers(page, size, selectedFields);
    }

    private User fromEntity(UserEntity entity){
        return User.builder()
                .customerId(entity.getCustomerId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .role(entity.getRole().name())
                .build();
    }
}
