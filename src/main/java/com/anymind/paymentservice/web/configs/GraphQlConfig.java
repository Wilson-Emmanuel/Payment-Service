package com.anymind.paymentservice.web.configs;

import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

/**
 * Created by Wilson
 * On 28-12-2022, 5:33
 */
@Configuration
public class GraphQlConfig {


    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {

        GraphQLScalarType scalarType = ExtendedScalars.Object;

        return wiringBuilder -> wiringBuilder
                .scalar(scalarType);
    }
}
