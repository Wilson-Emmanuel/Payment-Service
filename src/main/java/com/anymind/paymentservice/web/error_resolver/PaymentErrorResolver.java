package com.anymind.paymentservice.web.error_resolver;

import com.anymind.paymentservice.web.exceptions.BusinessLogicException;
import com.anymind.paymentservice.web.exceptions.ResourceAlreadyExistException;
import com.anymind.paymentservice.web.exceptions.ResourceNotFoundException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import java.util.stream.Collectors;

/**
 * Created by Wilson
 * On 28-12-2022, 8:40
 */
@Component
public class PaymentErrorResolver extends DataFetcherExceptionResolverAdapter {

    private static final Logger log = LoggerFactory.getLogger(PaymentErrorResolver.class);

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        Throwable t = NestedExceptionUtils.getMostSpecificCause(ex);

        if (t instanceof BusinessLogicException || t instanceof ResourceAlreadyExistException || t instanceof ResourceNotFoundException exception) {
            return GraphqlErrorBuilder.newError(env)
                    .errorType(ErrorTypes.BAD_REQUEST)
                    .message(t.getMessage())
                    .build();
        }
        if( t instanceof ConstraintViolationException constraintViolationException){
            return validationError(constraintViolationException, env);
        }

        if (ex instanceof BindException bindException) {
            return GraphqlErrorBuilder.newError(env)
                    .message("Could not parse data")
                    .errorType(ErrorTypes.BAD_REQUEST)
                    .build();
        }

        t.printStackTrace();
        return GraphqlErrorBuilder.newError(env)
                .message("Error occurred: Ensure request is valid ")
                .errorType(ErrorTypes.BAD_REQUEST)
                .build();
    }

    private GraphQLError validationError(ConstraintViolationException exception, DataFetchingEnvironment env){
        String invalidFields = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("\n"));

        return GraphqlErrorBuilder.newError(env)
                .errorType(ErrorTypes.BAD_REQUEST)
                .message(invalidFields)
                .build();
    }
}
