package com.anymind.paymentservice;

import com.anymind.paymentservice.web.models.responses.Payment;
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
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Wilson
 * On 28-12-2022, 21:00
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PaymentTest {

    @Autowired
    private  WebApplicationContext applicationContext;

    private HttpGraphQlTester tester;


    @BeforeAll
    public  void setup(){
        WebTestClient client =
                MockMvcWebTestClient.bindToApplicationContext(applicationContext)
                        .configureClient()
                        .baseUrl("/graphql")
                        .build();

        tester = HttpGraphQlTester.create(client);
    }

    @Test
    void when_makePayment_then_successful(){

        int sizeBeforeMakingPayment = this.tester.documentName("queryPayments")
                .execute()
                .path("getPayments")
                .entityList(Object.class)
                .get().size();

        //then make a payment with master card
        Payment paymentResponse = this.tester.documentName("makePaymentWithMastercard")
                .execute()
                .path("makePayment")
                .entity(Payment.class)
                .get();

        //now get the new count after the test
        int sizeAfterMakingPayment = this.tester.documentName("queryPayments")
                .execute()
                .path("getPayments")
                .entityList(Object.class)
                .get().size();

        //get master card payment details
        PaymentMethod masterCard = this.tester.documentName("getMastercardPaymentMethod")
                .execute()
                        .path("getPaymentMethod")
                                .entity(PaymentMethod.class)
                                        .get();

        //Confirm that size increased by 1
        //All confirm that the returned payment is indeed what was sent
        assertEquals(sizeAfterMakingPayment, sizeBeforeMakingPayment+1);
        assertEquals(masterCard.getName(), paymentResponse.getPaymentMethod().getName());


        //confirm the final price and points are well calculated
        BigDecimal requestPrice = new BigDecimal("800.00");
        BigDecimal requestPriceModifier = new BigDecimal("0.95");
        BigDecimal responseFinalPrice = new BigDecimal(paymentResponse.getFinalPrice());

        assertEquals(responseFinalPrice, requestPriceModifier.multiply(requestPrice).setScale(2,RoundingMode.HALF_EVEN));
        assertEquals(paymentResponse.getPoints(), requestPrice.multiply(masterCard.getPointModifier()).intValue());

    }
}
