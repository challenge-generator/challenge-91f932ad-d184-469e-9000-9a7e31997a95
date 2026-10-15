package com.pragma.payment.domain;




import com.pragma.payment.domain.exception.PaymentGatewayException;
import com.pragma.payment.domain.exception.FraudDetectionException;
import com.pragma.payment.domain.exception.CreditOriginatorException;
import java.util.List;
import java.util.UUID;

public interface PaymentServicePort {
    Payment processPayment(Payment payment) throws CreditOriginatorException, FraudDetectionException, PaymentGatewayException;
    
    Payment getPaymentById(UUID paymentId);
    
    List<Payment> getPaymentsByStatus(Payment.PaymentStatus status);
    
    Payment updatePaymentStatus(UUID paymentId, Payment.PaymentStatus newStatus, String failureReason);
}