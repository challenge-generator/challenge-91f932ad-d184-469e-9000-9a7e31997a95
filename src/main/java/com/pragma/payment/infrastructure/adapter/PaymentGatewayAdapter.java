package com.pragma.payment.infrastructure.adapter;

import com.pragma.payment.domain.Payment;
import com.pragma.payment.domain.exception.PaymentGatewayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class PaymentGatewayAdapter {

    private static final Logger log = LoggerFactory.getLogger(PaymentGatewayAdapter.class);
    private static final String GATEWAY_CODE = "PAY_GATEWAY";
    private static final BigDecimal GATEWAY_FEE_PERCENT = new BigDecimal("0.029");
    private static final BigDecimal MIN_TRANSACTION_AMOUNT = new BigDecimal("1.00");
    private static final BigDecimal MAX_TRANSACTION_AMOUNT = new BigDecimal("100000.00");

    public GatewayResponse processPayment(Payment payment) throws PaymentGatewayException {
        log.debug("Procesando pago a través del gateway para transacción: {}", payment.getTransactionId());
        
        validatePayment(payment);

        BigDecimal gatewayFee = calculateGatewayFee(payment.getAmount());
        BigDecimal totalAmount = payment.getAmount().add(gatewayFee);

        String gatewayTransactionId = generateGatewayTransactionId(payment.getTransactionId());
        
        boolean authorizationResult = authorizeTransaction(payment);
        
        if (!authorizationResult) {
            log.warn("Autorización denegada por gateway para transacción: {}", payment.getTransactionId());
            throw new PaymentGatewayException(
                "Autorización denegada por el gateway de pagos",
                payment.getTransactionId(),
                GATEWAY_CODE,
                "AUTH_DENIED"
            );
        }

        boolean captureResult = captureTransaction(payment, totalAmount);
        
        if (!captureResult) {
            log.error("Captura fallida para transacción: {}", payment.getTransactionId());
            throw new PaymentGatewayException(
                "Error al capturar el pago en el gateway",
                payment.getTransactionId(),
                GATEWAY_CODE,
                "CAPTURE_FAILED"
            );
        }

        log.info("Pago procesado exitosamente. Gateway ID: {}, Fee: {}", 
            gatewayTransactionId, 
            gatewayFee);

        return new GatewayResponse(
            gatewayTransactionId,
            true,
            "APPROVED",
            gatewayFee,
            totalAmount,
            LocalDateTime.now()
        );
    }

    public GatewayResponse refundPayment(String gatewayTransactionId, BigDecimal amount) throws PaymentGatewayException {
        if (gatewayTransactionId == null || gatewayTransactionId.isEmpty()) {
            throw new PaymentGatewayException(
                "ID de transacción de gateway requerido para reembolso",
                null,
                GATEWAY_CODE,
                "INVALID_TRANSACTION_ID"
            );
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new PaymentGatewayException(
                "Monto de reembolso inválido",
                null,
                GATEWAY_CODE,
                "INVALID_AMOUNT"
            );
        }

        log.info("Procesando reembolso para gateway ID: {}, monto: {}", gatewayTransactionId, amount);
        
        String refundId = "REF-" + UUID.randomUUID().toString();
        
        return new GatewayResponse(
            refundId,
            true,
            "REFUNDED",
            BigDecimal.ZERO,
            amount,
            LocalDateTime.now()
        );
    }

    public boolean verifyTransactionStatus(String gatewayTransactionId) throws PaymentGatewayException {
        if (gatewayTransactionId == null || gatewayTransactionId.isEmpty()) {
            throw new PaymentGatewayException(
                "ID de transacción de gateway requerido para verificación",
                null,
                GATEWAY_CODE,
                "INVALID_TRANSACTION_ID"
            );
        }

        log.debug("Verificando estado de transacción: {}", gatewayTransactionId);
        return true;
    }

    private void validatePayment(Payment payment) throws PaymentGatewayException {
        if (payment.getAmount() == null) {
            throw new PaymentGatewayException(
                "Monto de pago requerido",
                payment.getTransactionId(),
                GATEWAY_CODE,
                "MISSING_AMOUNT"
            );
        }

        if (payment.getAmount().compareTo(MIN_TRANSACTION_AMOUNT) < 0) {
            throw new PaymentGatewayException(
                String.format("Monto mínimo de transacción es %s", MIN_TRANSACTION_AMOUNT),
                payment.getTransactionId(),
                GATEWAY_CODE,
                "AMOUNT_TOO_LOW"
            );
        }

        if (payment.getAmount().compareTo(MAX_TRANSACTION_AMOUNT) > 0) {
            throw new PaymentGatewayException(
                String.format("Monto máximo de transacción es %s", MAX_TRANSACTION_AMOUNT),
                payment.getTransactionId(),
                GATEWAY_CODE,
                "AMOUNT_TOO_HIGH"
            );
        }

        if (payment.getCurrency() == null || payment.getCurrency().isEmpty()) {
            throw new PaymentGatewayException(
                "Moneda de pago requerida",
                payment.getTransactionId(),
                GATEWAY_CODE,
                "MISSING_CURRENCY"
            );
        }
    }

    private BigDecimal calculateGatewayFee(BigDecimal amount) {
        return amount.multiply(GATEWAY_FEE_PERCENT).setScale(2, java.math.RoundingMode.HALF_UP);
    }

    private String generateGatewayTransactionId(String paymentTransactionId) {
        return "GW-" + paymentTransactionId + "-" + System.currentTimeMillis();
    }

    private boolean authorizeTransaction(Payment payment) {
        return payment.getAmount().compareTo(new BigDecimal("5000.00")) < 0;
    }

    private boolean captureTransaction(Payment payment, BigDecimal totalAmount) {
        return true;
    }

    public record GatewayResponse(
        String gatewayTransactionId,
        boolean success,
        String status,
        BigDecimal fee,
        BigDecimal totalAmount,
        LocalDateTime processedAt
    ) {}
}