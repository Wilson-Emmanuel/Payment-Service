package com.anymind.paymentservice;

import com.anymind.paymentservice.services.PaymentMethodService;
import com.anymind.paymentservice.services.UserService;
import com.anymind.paymentservice.web.models.requests.PaymentMethodInput;
import com.anymind.paymentservice.web.models.requests.UserInput;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class PaymentServiceApplication {

	@Autowired
	private PaymentMethodService paymentMethodService;

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		
		SpringApplication.run(PaymentServiceApplication.class, args);
	}

	@PostConstruct
	public void autoloader(){
		List<PaymentMethodInput> paymentMethodInputs = List.of(
				new PaymentMethodInput("CASH",new BigDecimal("0.9"),new BigDecimal("1"),new BigDecimal("0.05"), false),
				new PaymentMethodInput("CASH_ON_DELIVERY",new BigDecimal("1"),new BigDecimal("1.2"),new BigDecimal("0.05"), true),
				new PaymentMethodInput("VISA",new BigDecimal("0.95"),new BigDecimal("1"),new BigDecimal("0.03"), true),
				new PaymentMethodInput("MASTERCARD",new BigDecimal("0.95"),new BigDecimal("1"),new BigDecimal("0.03"), true),
				new PaymentMethodInput("AMEX",new BigDecimal("0.98"),new BigDecimal("1.01"),new BigDecimal("0.02"), true),
				new PaymentMethodInput("JCB",new BigDecimal("0.95"),new BigDecimal("1"),new BigDecimal("0.05"), true),
				new PaymentMethodInput("LINE PAY",new BigDecimal("1"),new BigDecimal("1"),new BigDecimal("0.01"), false),
				new PaymentMethodInput("PAYPAY",new BigDecimal("1"),new BigDecimal("1"),new BigDecimal("0.01"), false),
				new PaymentMethodInput("POINTS",new BigDecimal("1"),new BigDecimal("1"),new BigDecimal("0"), false),
				new PaymentMethodInput("GRAB PAY",new BigDecimal("1"),new BigDecimal("1"),new BigDecimal("0.01"), false),
				new PaymentMethodInput("BANK TRANSFER",new BigDecimal("1"),new BigDecimal("1"),new BigDecimal("0"), true),
				new PaymentMethodInput("CHEQUE",new BigDecimal("0.9"),new BigDecimal("1"),new BigDecimal("0"), true)
		);

		paymentMethodInputs.forEach((methodInput)->paymentMethodService.createPaymentMethod(methodInput));


		List<UserInput> users = List.of(
				new UserInput("12345","Wilson","Emmanuel","wilson@gmail.com","CUSTOMER"),
				new UserInput("12346","Anyx","Admin","admin@anyx.com","ADMIN")
		);
		users.forEach(user -> userService.createUser(user));
	}


}
