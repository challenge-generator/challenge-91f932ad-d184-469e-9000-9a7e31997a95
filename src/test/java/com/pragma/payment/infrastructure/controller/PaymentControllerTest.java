package com.pragma.payment.infrastructure.controller;


import com.pragma.payment.domain.PaymentStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.payment.application.PaymentService;
import com.pragma.payment.domain.Payment;
import com.pragma.payment.domain.exception.CreditOriginatorException;
import com.pragma.payment.domain.exception.FraudDetectionException;
import com.pragma.payment.domain.exception.PaymentGatewayException;
import com.pragma.payment.infrastructure.config.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
@Import(GlobalExceptionHandler.class)
@DisplayName("Tests de integración para PaymentController - Superficie de práctica")
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentService paymentService;

    private Payment payment;
    private String paymentJson;

    @BeforeEach
    void setUp() throws Exception {
        payment = new Payment(
            UUID.randomUUID(),
            "TXN-100",
            new BigDecimal("1500.00"),
            "USD",
            Payment.PaymentStatus.COMPLETED,
            "web"
        );
        payment.setCreatedAt(LocalDateTime.now());
        payment.setUpdatedAt(LocalDateTime.now());

        paymentJson = objectMapper.writeValueAsString(payment);
    }

    @Test
    @DisplayName("Debe retornar 201 Created cuando el pago se procesa exitosamente")
    void shouldReturn201WhenPaymentProcessedSuccessfully() throws Exception {
        // Given: el servicio procesa el pago exitosamente
        when(paymentService.processPayment(any(Payment.class))).thenReturn(payment);

        // When & Then: se verifica el código de estado 201
        mockMvc.perform(post("/api/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(paymentJson))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(payment.getId().toString()))
            .andExpect(jsonPath("$.transactionId").value(payment.getTransactionId()));

        verify(paymentService, times(1)).processPayment(any(Payment.class));
    }

    @Test
    @DisplayName("Debe retornar 400 Bad Request cuando hay error de validación")
    void shouldReturn400WhenValidationError() throws Exception {
        // Given: payload inválido (falta transactionId)
        String invalidJson = "{\"amount\": 1000.00, \"currency\": \"USD\"}";

        // When & Then: se retorna 400 por validación
        mockMvc.perform(post("/api/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
            .andExpect(status().isBadRequest());

        verify(paymentService, never()).processPayment(any(Payment.class));
    }

    @Test
    @DisplayName("Debe retornar 502 Bad Gateway cuando CreditOriginatorException ocurre")
    void shouldReturn502WhenCreditOriginatorFails() throws Exception {
        // Given: el originador de crédito falla
        when(paymentService.processPayment(any(Payment.class)))
            .thenThrow(new CreditOriginatorException(
                "Credit originator connection failed",
                "TXN-100",
                "ORIGINATOR_ERROR"
            ));

        // When & Then: se retorna 502 con detalles del error
        mockMvc.perform(post("/api/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(paymentJson))
            .andExpect(status().isBadGateway())
            .andExpect(jsonPath("$.error").exists())
            .andExpect(jsonPath("$.transactionId").value("TXN-100"));
    }

    @Test
    @DisplayName("Debe retornar 422 Unprocessable Entity cuando FraudDetectionException ocurre")
    void shouldReturn422WhenFraudDetected() throws Exception {
        // Given: el motor antifraude detecta fraude
        when(paymentService.processPayment(any(Payment.class)))
            .thenThrow(new FraudDetectionException(
                "High risk transaction detected",
                "HIGH_RISK_SCORE"
            ));

        // When & Then: se retorna 422 con mensaje de fraude
        mockMvc.perform(post("/api/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(paymentJson))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.error").exists());
    }

    @Test
    @DisplayName("Debe retornar 503 Service Unavailable cuando PaymentGatewayException ocurre")
    void shouldReturn503WhenPaymentGatewayFails() throws Exception {
        // Given: el gateway de pagos falla
        when(paymentService.processPayment(any(Payment.class)))
            .thenThrow(new PaymentGatewayException(
                "Gateway timeout",
                "TIMEOUT"
            ));

        // When & Then: se retorna 503
        mockMvc.perform(post("/api/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(paymentJson))
            .andExpect(status().isServiceUnavailable())
            .andExpect(jsonPath("$.error").exists());
    }

    @Test
    @DisplayName("Debe retornar 200 OK cuando se obtiene pago por ID")
    void shouldReturn200WhenGetPaymentById() throws Exception {
        // Given: el servicio retorna el pago
        UUID paymentId = UUID.randomUUID();
        payment.setId(paymentId);
        when(paymentService.getPaymentById(paymentId)).thenReturn(payment);

        // When & Then: se retorna el pago con estado 200
        mockMvc.perform(get("/api/payments/{id}", paymentId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(paymentId.toString()));
    }

    @Test
    @DisplayName("Debe retornar 404 Not Found cuando el pago no existe")
    void shouldReturn404WhenPaymentNotFound() throws Exception {
        // Given: no existe el pago
        UUID paymentId = UUID.randomUUID();
        when(paymentService.getPaymentById(paymentId)).thenReturn(null);

        // When & Then: se retorna 404
        mockMvc.perform(get("/api/payments/{id}", paymentId))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Debe retornar 200 OK cuando se obtienen pagos por estado")
    void shouldReturn200WhenGetPaymentsByStatus() throws Exception {
        // Given: existen pagos con el estado especificado
        when(paymentService.getPaymentsByStatus(Payment.PaymentStatus.COMPLETED))
            .thenReturn(java.util.List.of(payment));

        // When & Then: se retorna la lista de pagos
        mockMvc.perform(get("/api/payments")
                .param("status", "COMPLETED"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("Debe validar que el Content-Type sea application/json")
    void shouldRejectNonJsonContent() throws Exception {
        // Given: content-type incorrecto
        // When & Then: se retorna 415 Unsupported Media Type
        mockMvc.perform(post("/api/payments")
                .contentType(MediaType.TEXT_PLAIN)
                .content("invalid content"))
            .andExpect(status().isUnsupportedMediaType());
    }
}