package com.pragma.payment;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pragma.payment.application.PaymentService;
import com.pragma.payment.domain.dto.PaymentRequest;
import com.pragma.payment.domain.dto.PaymentResponse;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootApplication
public class PaymentApplication {
    private static final Logger logger = LoggerFactory.getLogger(PaymentApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class, args);
    }

    @Bean
    @Profile("dev")
    public CommandLineRunner demo(PaymentService paymentService) {
        return args -> {
            logger.debug("Iniciando demostración del servicio de pagos...");

            PaymentRequest request = new PaymentRequest(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                new BigDecimal("100.00"),
                "USD",
                "Compra de prueba"
            );

            try {
                PaymentResponse response = paymentService.processPayment(request);
                logger.debug("Pago procesado exitosamente: {}", response);
            } catch (Exception e) {
                logger.error("Error al procesar el pago de demostración: {}", e.getMessage(), e);
            }
        };
    }
}