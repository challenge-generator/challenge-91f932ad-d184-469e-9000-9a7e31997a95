package com.pragma.payment.application;


import com.pragma.payment.domain.PaymentStatus;
import com.pragma.payment.domain.Payment;
import com.pragma.payment.domain.PaymentServicePort;
import com.pragma.payment.domain.exception.CreditOriginatorException;
import com.pragma.payment.domain.exception.FraudDetectionException;
import com.pragma.payment.domain.exception.PaymentGatewayException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para PaymentService - Superficie de práctica")
class PaymentServiceTest {

    @Mock
    private PaymentServicePort paymentServicePort;

    @InjectMocks
    private PaymentService paymentService;

    private Payment payment;

    @BeforeEach
    void setUp() {
        payment = new Payment(
            UUID.randomUUID(),
            "TXN-001",
            new BigDecimal("1000.00"),
            "USD",
            Payment.PaymentStatus.PENDING,
            "online"
        );
    }

    @Test
    @DisplayName("Debe procesar pago exitosamente cuando pasa todas las validaciones")
    void shouldProcessPaymentSuccessfully() throws CreditOriginatorException, FraudDetectionException, PaymentGatewayException {
        // Given: un pago válido que pasa todas las validaciones
        when(paymentServicePort.processPayment(any(Payment.class))).thenReturn(payment);

        // When: se procesa el pago
        Payment result = paymentService.processPayment(payment);

        // Then: el pago fue procesado
        assertNotNull(result);
        verify(paymentServicePort, times(1)).processPayment(any(Payment.class));
    }

    @Test
    @DisplayName("Debe lanzar CreditOriginatorException cuando el originador falla")
    void shouldThrowCreditOriginatorException() throws CreditOriginatorException, FraudDetectionException, PaymentGatewayException {
        // Given: el originador de crédito rechaza la transacción
        when(paymentServicePort.processPayment(any(Payment.class)))
            .thenThrow(new CreditOriginatorException(
                "Credit originator rejected transaction",
                "TXN-001",
                "CREDIT_REJECTED"
            ));

        // When & Then: se propaga la excepción
        assertThrows(CreditOriginatorException.class, () -> {
            paymentService.processPayment(payment);
        });
    }

    @Test
    @DisplayName("Debe lanzar FraudDetectionException cuando el motor antifraude detecta fraude")
    void shouldThrowFraudDetectionException() throws CreditOriginatorException, FraudDetectionException, PaymentGatewayException {
        // Given: el motor antifraude detecta actividad sospechosa
        when(paymentServicePort.processPayment(any(Payment.class)))
            .thenThrow(new FraudDetectionException(
                "Suspicious activity detected",
                "HIGH_RISK"
            ));

        // When & Then: se propaga la excepción
        assertThrows(FraudDetectionException.class, () -> {
            paymentService.processPayment(payment);
        });
    }

    @Test
    @DisplayName("Debe lanzar PaymentGatewayException cuando el gateway falla")
    void shouldThrowPaymentGatewayException() throws CreditOriginatorException, FraudDetectionException, PaymentGatewayException {
        // Given: el gateway de pagos no puede procesar la transacción
        when(paymentServicePort.processPayment(any(Payment.class)))
            .thenThrow(new PaymentGatewayException(
                "Gateway timeout",
                "TIMEOUT"
            ));

        // When & Then: se propaga la excepción
        assertThrows(PaymentGatewayException.class, () -> {
            paymentService.processPayment(payment);
        });
    }

    @Test
    @DisplayName("Debe obtener pago por ID correctamente")
    void shouldGetPaymentById() {
        // Given: existe un pago con el ID especificado
        UUID paymentId = UUID.randomUUID();
        when(paymentServicePort.getPaymentById(paymentId)).thenReturn(payment);

        // When: se busca el pago por ID
        Payment result = paymentService.getPaymentById(paymentId);

        // Then: se retorna el pago
        assertNotNull(result);
        verify(paymentServicePort, times(1)).getPaymentById(paymentId);
    }

    @Test
    @DisplayName("Debe obtener pagos por estado correctamente")
    void shouldGetPaymentsByStatus() {
        // Given: existen pagos con estado COMPLETED
        List<Payment> payments = List.of(payment);
        when(paymentServicePort.getPaymentsByStatus(Payment.PaymentStatus.COMPLETED))
            .thenReturn(payments);

        // When: se buscan pagos por estado
        List<Payment> result = paymentService.getPaymentsByStatus(Payment.PaymentStatus.COMPLETED);

        // Then: se retornan los pagos
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("Debe actualizar estado del pago correctamente")
    void shouldUpdatePaymentStatus() {
        // Given: el pago existe y se puede actualizar
        UUID paymentId = UUID.randomUUID();
        when(paymentServicePort.updatePaymentStatus(
            eq(paymentId),
            eq(Payment.PaymentStatus.COMPLETED),
            isNull()
        )).thenReturn(payment);

        // When: se actualiza el estado del pago
        Payment result = paymentService.updatePaymentStatus(
            paymentId,
            Payment.PaymentStatus.COMPLETED,
            null
        );

        // Then: el pago fue actualizado
        assertNotNull(result);
        verify(paymentServicePort, times(1)).updatePaymentStatus(
            paymentId,
            Payment.PaymentStatus.COMPLETED,
            null
        );
    }
}