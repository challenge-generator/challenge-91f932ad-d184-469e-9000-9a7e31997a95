package com.pragma.payment.infrastructure.adapter;


import com.pragma.payment.domain.PaymentStatus;
import com.pragma.payment.domain.Payment;
import com.pragma.payment.domain.exception.CreditOriginatorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para CreditOriginatorAdapter - Superficie de práctica")
class CreditOriginatorAdapterTest {

    @Mock
    private CreditOriginatorClient creditOriginatorClient;

    @InjectMocks
    private CreditOriginatorAdapter creditOriginatorAdapter;

    private Payment payment;

    @BeforeEach
    void setUp() {
        payment = new Payment(
            UUID.randomUUID(),
            "TXN-002",
            new BigDecimal("5000.00"),
            "USD",
            Payment.PaymentStatus.PENDING,
            "credit_originator"
        );
    }

    @Test
    @DisplayName("Debe procesar crédito exitosamente cuando el originador acepta")
    void shouldProcessCreditSuccessfully() throws CreditOriginatorException {
        // Given: el originador de crédito acepta la solicitud
        CreditOriginatorResponse response = new CreditOriginatorResponse(
            "TXN-002",
            "APPROVED",
            "Credit approved successfully"
        );
        when(creditOriginatorClient.requestCredit(any(CreditOriginatorRequest.class)))
            .thenReturn(response);

        // When: se procesa el crédito
        Payment result = creditOriginatorAdapter.processCredit(payment);

        // Then: el pago se marca como completado
        assertNotNull(result);
        assertEquals(Payment.PaymentStatus.COMPLETED, result.getStatus());
        verify(creditOriginatorClient, times(1)).requestCredit(any(CreditOriginatorRequest.class));
    }

    @Test
    @DisplayName("Debe lanzar CreditOriginatorException cuando el originador rechaza")
    void shouldThrowExceptionWhenCreditRejected() throws CreditOriginatorException {
        // Given: el originador rechaza el crédito
        CreditOriginatorResponse response = new CreditOriginatorResponse(
            "TXN-002",
            "REJECTED",
            "Insufficient credit score"
        );
        when(creditOriginatorClient.requestCredit(any(CreditOriginatorRequest.class)))
            .thenReturn(response);

        // When & Then: se lanza excepción con detalles del rechazo
        CreditOriginatorException exception = assertThrows(
            CreditOriginatorException.class,
            () -> creditOriginatorAdapter.processCredit(payment)
        );
        assertEquals("TXN-002", exception.getTransactionId());
    }

    @Test
    @DisplayName("Debe lanzar CreditOriginatorException cuando hay error de comunicación")
    void shouldThrowExceptionOnCommunicationError() throws CreditOriginatorException {
        // Given: hay un error de comunicación con el originador
        when(creditOriginatorClient.requestCredit(any(CreditOriginatorRequest.class)))
            .thenThrow(new RuntimeException("Connection timeout"));

        // When & Then: se lanza excepción con código de error
        CreditOriginatorException exception = assertThrows(
            CreditOriginatorException.class,
            () -> creditOriginatorAdapter.processCredit(payment)
        );
        assertNotNull(exception.getOriginatorCode());
    }

    @Test
    @DisplayName("Debe validar monto negativo antes de enviar al originador")
    void shouldValidateNegativeAmount() {
        // Given: un pago con monto negativo
        Payment invalidPayment = new Payment(
            UUID.randomUUID(),
            "TXN-003",
            new BigDecimal("-100.00"),
            "USD",
            Payment.PaymentStatus.PENDING,
            "credit_originator"
        );

        // When & Then: se lanza excepción por monto inválido
        assertThrows(IllegalArgumentException.class, () -> {
            creditOriginatorAdapter.processCredit(invalidPayment);
        });
    }

    @Test
    @DisplayName("Debe incluir transactionId en la solicitud al originador")
    void shouldIncludeTransactionIdInRequest() throws CreditOriginatorException {
        // Given: un pago con transactionId específico
        String transactionId = "TXN-SPECIFIC-001";
        payment = new Payment(
            UUID.randomUUID(),
            transactionId,
            new BigDecimal("1000.00"),
            "USD",
            Payment.PaymentStatus.PENDING,
            "credit_originator"
        );

        CreditOriginatorResponse response = new CreditOriginatorResponse(
            transactionId,
            "APPROVED",
            "OK"
        );
        when(creditOriginatorClient.requestCredit(any(CreditOriginatorRequest.class)))
            .thenReturn(response);

        // When: se procesa el crédito
        creditOriginatorAdapter.processCredit(payment);

        // Then: se verifica que el transactionId fue incluido
        verify(creditOriginatorClient).requestCredit(argThat(request -> 
            transactionId.equals(request.getTransactionId())
        ));
    }
}