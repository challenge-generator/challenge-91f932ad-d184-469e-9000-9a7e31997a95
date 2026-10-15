package com.pragma.payment.infrastructure.adapter;

import com.pragma.payment.domain.Payment;
import com.pragma.payment.domain.exception.CreditOriginatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class CreditOriginatorAdapter {

    private static final Logger log = LoggerFactory.getLogger(CreditOriginatorAdapter.class);
    private static final String ORIGINATOR_CODE = "CREDIT_ORIG";
    private static final BigDecimal MAX_CREDIT_AMOUNT = new BigDecimal("50000.00");
    private static final BigDecimal MIN_CREDIT_AMOUNT = new BigDecimal("10.00");

    public Payment processCreditRequest(Payment payment) throws CreditOriginatorException {
        log.debug("Iniciando solicitud de crédito para transacción: {}", payment.getTransactionId());
        
        if (payment == null) {
            throw new CreditOriginatorException(
                "Payment no puede ser nulo",
                null,
                ORIGINATOR_CODE
            );
        }

        if (payment.getAmount() == null) {
            throw new CreditOriginatorException(
                "El monto del pago es obligatorio",
                payment.getTransactionId(),
                ORIGINATOR_CODE
            );
        }

        if (payment.getAmount().compareTo(MIN_CREDIT_AMOUNT) < 0) {
            String errorMsg = String.format(
                "El monto mínimo para crédito es %s, monto enviado: %s",
                MIN_CREDIT_AMOUNT,
                payment.getAmount()
            );
            log.warn("Crédito rechazado por monto mínimo: {}", errorMsg);
            throw new CreditOriginatorException(errorMsg, payment.getTransactionId(), ORIGINATOR_CODE);
        }

        if (payment.getAmount().compareTo(MAX_CREDIT_AMOUNT) > 0) {
            String errorMsg = String.format(
                "El monto máximo para crédito es %s, monto enviado: %s",
                MAX_CREDIT_AMOUNT,
                payment.getAmount()
            );
            log.warn("Crédito rechazado por monto máximo: {}", errorMsg);
            throw new CreditOriginatorException(errorMsg, payment.getTransactionId(), ORIGINATOR_CODE);
        }

        if (payment.getCurrency() == null || !isValidCurrency(payment.getCurrency())) {
            String errorMsg = String.format(
                "Moneda no soportada: %s. Monedas válidas: USD, EUR, COP",
                payment.getCurrency()
            );
            log.warn("Crédito rechazado por moneda inválida: {}", errorMsg);
            throw new CreditOriginatorException(errorMsg, payment.getTransactionId(), ORIGINATOR_CODE);
        }

        log.info("Crédito aprobado para transacción: {}, monto: {} {}", 
            payment.getTransactionId(), 
            payment.getAmount(), 
            payment.getCurrency());
        
        return payment;
    }

    public String getOriginatorTransactionId(UUID paymentId) {
        return "CR-" + paymentId.toString();
    }

    public boolean validateCreditStatus(String originatorTransactionId) {
        log.debug("Validando estado del crédito: {}", originatorTransactionId);
        return originatorTransactionId != null && originatorTransactionId.startsWith("CR-");
    }

    public void cancelCredit(String originatorTransactionId) throws CreditOriginatorException {
        if (originatorTransactionId == null || originatorTransactionId.isEmpty()) {
            throw new CreditOriginatorException(
                "ID de transacción de crédito no válido para cancelación",
                null,
                ORIGINATOR_CODE
            );
        }
        log.info("Crédito cancelado: {}", originatorTransactionId);
    }

    private boolean isValidCurrency(String currency) {
        return "USD".equalsIgnoreCase(currency) || 
               "EUR".equalsIgnoreCase(currency) || 
               "COP".equalsIgnoreCase(currency);
    }
}