package com.anymind.paymentservice.services.impls;

import com.anymind.paymentservice.persistence.entities.UserEntity;
import com.anymind.paymentservice.persistence.repositories.UserRepository;
import com.anymind.paymentservice.services.UserService;
import com.anymind.paymentservice.web.exceptions.ResourceAlreadyExistException;
import com.anymind.paymentservice.web.exceptions.ResourceNotFoundException;
import com.anymind.paymentservice.web.models.enums.UserRole;
import com.anymind.paymentservice.web.models.requests.UserInput;
import com.anymind.paymentservice.web.models.responses.User;
import com.anymind.paymentservice.web.models.responses.UsersPage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

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
        if(userRepository.hasUserByExternalIdOrEmail(userInput.getUserId(), userInput.getEmail())){
            throw new ResourceAlreadyExistException("User already exists");
        }
        UserEntity userEntity = UserEntity.builder()
                .externalId(userInput.getUserId())
                .firstName(userInput.getFirstName())
                .lastName(userInput.getLastName())
                .email(userInput.getEmail())
                .role(UserRole.valueOf(userInput.getRole()))
                .build();
        return fromEntity(userRepository.save(userEntity));
    }

    @Override
    public User getUserByExternalId(String externalId) {
        UserEntity userEntity = userRepository.findByExternalId(externalId).orElseThrow(()-> new ResourceNotFoundException("User does not exists"));
        return fromEntity(userEntity);
    }

    @Override
    public User getUserByUserId(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User ID does not exists"));
        return fromEntity(userEntity);
    }

    @Override
    public User getUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User email does not exists"));
        return fromEntity(userEntity);
    }

    @Override
    public UsersPage getCustomers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
        Page<UserEntity> userEntities = userRepository.findAllByRole(UserRole.CUSTOMER, pageable);
        return new UsersPage(
                page,
                userEntities.getSize(),
                userEntities.getTotalPages(),
                userEntities.stream()
                        .map(this::fromEntity)
                        .collect(Collectors.toList())
                );
    }

    private User fromEntity(UserEntity entity){
        return User.builder()
                .userId(entity.getExternalId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .role(entity.getRole().name())
                .build();
    }
}
