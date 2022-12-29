package com.anymind.paymentservice;

import com.anymind.paymentservice.web.models.responses.PaymentMethod;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Wilson
 * On 28-12-2022, 21:00
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PaymentMethodTest {

    @Autowired
    private  WebApplicationContext applicationContext;

    private HttpGraphQlTester tester;

    private PaymentMethod expectedPaymentMethod;

    @BeforeAll
    public  void setup(){

        this.expectedPaymentMethod = new PaymentMethod(
                "UnionPay",
                new BigDecimal("0.95"),
                new BigDecimal("1.0"),
                new BigDecimal("0.05"),
                true
        );

        WebTestClient client =
                MockMvcWebTestClient.bindToApplicationContext(applicationContext)
                        .configureClient()
                        .baseUrl("/graphql")
                        .build();

        tester = HttpGraphQlTester.create(client);
    }

    @Test
    void onStartup_load_12_paymentMethods_to_DB_then_successful(){
        assertTrue(getPaymentMethodCount() >= 12);
    }

    @Test
    void add_new_paymentMethod_then_successful(){

        int beforeAdding = getPaymentMethodCount();

        PaymentMethod addedPaymentMethod = this.tester.documentName("addNewPaymentMethod")
                .execute()
                .path("createPaymentMethod")
                .entity(PaymentMethod.class)
                .get();

        int afterAdding = getPaymentMethodCount();

        assertEquals(beforeAdding+1, afterAdding);
        assertEquals(expectedPaymentMethod, addedPaymentMethod);
    }
    private int getPaymentMethodCount(){
       return this.tester.documentName("allPaymentMethods")
                .execute()
                .path("getPaymentMethods")
                .entityList(Object.class)
                .get().size();
    }
}
