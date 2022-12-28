package com.anymind.paymentservice.web.error_resolver;

import graphql.ErrorClassification;

public enum ErrorTypes implements ErrorClassification {
    BAD_REQUEST,
    UNAUTHORIZED,
    FORBIDDEN,
    NOT_FOUND ,
    INTERNAL_ERROR
}
