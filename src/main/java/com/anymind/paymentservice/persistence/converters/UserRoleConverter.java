package com.anymind.paymentservice.persistence.converters;

import com.anymind.paymentservice.web.models.enums.UserRole;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Created by Wilson
 * On 28-12-2022, 11:40
 */
@Converter
public class UserRoleConverter implements AttributeConverter<UserRole, String> {
    @Override
    public String convertToDatabaseColumn(UserRole userRole) {
        return userRole == null ? null : userRole.name();
    }

    @Override
    public UserRole convertToEntityAttribute(String s) {
        return s == null ? null : UserRole.valueOf(s);
    }
}
