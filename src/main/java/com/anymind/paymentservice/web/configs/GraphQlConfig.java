package com.anymind.paymentservice.web.configs;

import com.anymind.paymentservice.persistence.repositories.UserRepository;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import graphql.schema.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.util.Map;

/**
 * Created by Wilson
 * On 28-12-2022, 5:33
 */
@Configuration
public class GraphQlConfig {


    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer(UserRepository userRepository) {

        //SCALAR TYPE
        GraphQLScalarType scalarType = ExtendedScalars.Object;

        //QUERYDSL: disabled in favor or custom implementation of Dynamic query
       // DataFetcher<UserEntity> dataFetcher = QuerydslDataFetcher.builder(userRepository).single();

        //UNION TYPE RESOLVER
        TypeResolver pagedData = env -> {
            Object javaObject = env.getObject();//the PagedData (union type) java object that needs to be resolved
            if ("data".equals(env.getField().getName()) && javaObject instanceof Map<?,?> value) {
                if("Payment".equals(String.valueOf(value.get("interfaceType")))){
                    return env.getSchema().getObjectType("Payment");
                }
                //implement more check when extra Union is added to the PagedData
                //user is the default
                return env.getSchema().getObjectType("User");
            }
            //error
            return env.getSchema().getObjectType("User");
        };

        return wiringBuilder -> wiringBuilder
                .scalar(scalarType)
                .type("PagedData", (typewiring)->typewiring.typeResolver(pagedData));
               // .type("Query", builder -> builder.dataFetcher("getCustomerById", dataFetcher));

    }


}
