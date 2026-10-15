package com.pragma.payment.application;


import com.pragma.payment.domain.PaymentStatus;
import com.pragma.payment.domain.Payment;
import com.pragma.payment.domain.PaymentServicePort;
import com.pragma.payment.domain.exception.CreditOriginatorException;
import com.pragma.payment.domain.exception.FraudDetectionException;
import com.pragma.payment.domain.exception.PaymentGatewayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    private final PaymentServicePort paymentPort;

    public PaymentService(PaymentServicePort paymentPort) {
        this.paymentPort = paymentPort;
    }

    public Payment processPayment(Payment payment) {
        logger.info("Iniciando procesamiento de pago para transactionId: {}", payment.getTransactionId());
        logger.debug("Detalles del pago: amount={}, currency={}, origin={}", 
                payment.getAmount(), payment.getCurrency(), payment.getOrigin());
        
        try {
            Payment processedPayment = paymentPort.processPayment(payment);
            logger.info("Pago procesado exitosamente. PaymentId: {}, Status: {}", 
                    processedPayment.getId(), processedPayment.getStatus());
            return processedPayment;
        } catch (CreditOriginatorException e) {
            logger.error("Error del originador de créditos para transactionId: {}. Code: {}, Details: {}",
                    e.getTransactionId(), e.getOriginatorCode(), e.getErrorDetails(), e);
            payment.markAsFailed("CREDIT_ORIGINATOR: " + e.getOriginatorCode() + " - " + e.getMessage());
            return paymentPort.updatePaymentStatus(payment.getId(), payment.getStatus(), e.getMessage());
        } catch (FraudDetectionException e) {
            logger.error("Transacción bloqueada por motor antifraude. TransactionId: {}. Risk: {}, Details: {}",
                    e.getTransactionId(), e.getRiskLevel(), e.getErrorDetails(), e);
            payment.markAsRejected("FRAUD_DETECTED: " + e.getRiskLevel() + " - " + e.getMessage());
            return paymentPort.updatePaymentStatus(payment.getId(), payment.getStatus(), e.getMessage());
        } catch (PaymentGatewayException e) {
            logger.error("Error del gateway de pagos para transactionId: {}. Code: {}, Retryable: {}, Details: {}",
                    e.getTransactionId(), e.getGatewayCode(), e.isRetryable(), e.getErrorDetails(), e);
            payment.markAsFailed("GATEWAY: " + e.getGatewayCode() + " - " + e.getMessage());
            return paymentPort.updatePaymentStatus(payment.getId(), payment.getStatus(), e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado procesando pago para transactionId: {}. Error: {}",
                    payment.getTransactionId(), e.getMessage(), e);
            payment.markAsFailed("UNEXPECTED_ERROR: " + e.getMessage());
            return paymentPort.updatePaymentStatus(payment.getId(), payment.getStatus(), e.getMessage());
        }
    }

    public Payment getPaymentById(UUID paymentId) {
        logger.debug("Obteniendo pago con ID: {}", paymentId);
        Payment payment = paymentPort.getPaymentById(paymentId);
        if (payment == null) {
            logger.warn("Pago no encontrado con ID: {}", paymentId);
        }
        return payment;
    }

    public List<Payment> getPaymentsByStatus(Payment.PaymentStatus status) {
        logger.debug("Obteniendo pagos con estado: {}", status);
        List<Payment> payments = paymentPort.getPaymentsByStatus(status);
        logger.info("Se encontraron {} pagos con estado {}", payments.size(), status);
        return payments;
    }

    public Payment updatePaymentStatus(UUID paymentId, Payment.PaymentStatus newStatus, String failureReason) {
        logger.info("Actualizando estado del pago {} a {}", paymentId, newStatus);
        return paymentPort.updatePaymentStatus(paymentId, newStatus, failureReason);
    }
}