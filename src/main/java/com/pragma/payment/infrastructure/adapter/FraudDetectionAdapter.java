package com.pragma.payment.infrastructure.adapter;

import com.pragma.payment.domain.Payment;
import com.pragma.payment.domain.exception.FraudDetectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;

@Component
public class FraudDetectionAdapter {

    private static final Logger log = LoggerFactory.getLogger(FraudDetectionAdapter.class);
    private static final String FRAUD_ENGINE_CODE = "FRAUD_DET";
    private static final BigDecimal HIGH_RISK_AMOUNT = new BigDecimal("10000.00");
    private static final int MAX_DAILY_TRANSACTIONS = 10;
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    public FraudAnalysisResult analyzeTransaction(Payment payment) throws FraudDetectionException {
        log.debug("Iniciando análisis antifraude para transacción: {}", payment.getTransactionId());
        
        if (payment == null) {
            throw new FraudDetectionException(
                "Payment no puede ser nulo para análisis antifraude",
                null,
                FRAUD_ENGINE_CODE
            );
        }

        if (payment.getAmount() == null) {
            throw new FraudDetectionException(
                "Monto requerido para análisis antifraude",
                payment.getTransactionId(),
                FRAUD_ENGINE_CODE
            );
        }

        if (payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            String errorMsg = "El monto debe ser mayor a cero para análisis antifraude";
            log.warn("Transacción rechazada por monto inválido: {}", errorMsg);
            throw new FraudDetectionException(errorMsg, payment.getTransactionId(), FRAUD_ENGINE_CODE);
        }

        boolean isHighRisk = evaluateRiskLevel(payment);
        boolean isPatternSuspicious = detectSuspiciousPattern(payment);
        boolean isVelocityExceeded = checkVelocityLimit(payment);

        FraudAnalysisResult result = new FraudAnalysisResult(
            payment.getTransactionId(),
            !isHighRisk && !isPatternSuspicious && !isVelocityExceeded,
            isHighRisk ? "Monto alto detectado" : null,
            isPatternSuspicious ? "Patrón sospechoso detectado" : null,
            isVelocityExceeded ? "Límite de velocidad excedido" : null
        );

        log.info("Análisis antifraude completado para {}: aprobado={}, riesgo={}",
            payment.getTransactionId(),
            result.isApproved(),
            result.riskLevel());

        if (!result.isApproved()) {
            throw new FraudDetectionException(
                "Transacción rechazada por motor antifraude: " + result.rejectionReasons(),
                payment.getTransactionId(),
                FRAUD_ENGINE_CODE
            );
        }

        return result;
    }

    public boolean validatePayerIdentification(String identification) throws FraudDetectionException {
        if (identification == null || identification.trim().isEmpty()) {
            throw new FraudDetectionException(
                "Identificación del pagador requerida",
                null,
                FRAUD_ENGINE_CODE
            );
        }

        if (identification.length() < 5 || identification.length() > 20) {
            throw new FraudDetectionException(
                "Identificación con longitud inválida",
                null,
                FRAUD_ENGINE_CODE
            );
        }

        return true;
    }

    public boolean validatePayeeIdentification(String identification) throws FraudDetectionException {
        if (identification == null || identification.trim().isEmpty()) {
            throw new FraudDetectionException(
                "Identificación del beneficiario requerida",
                null,
                FRAUD_ENGINE_CODE
            );
        }
        return true;
    }

    private boolean evaluateRiskLevel(Payment payment) {
        return payment.getAmount().compareTo(HIGH_RISK_AMOUNT) >= 0;
    }

    private boolean detectSuspiciousPattern(Payment payment) {
        if (payment.getOrigin() == null) {
            return true;
        }
        return payment.getOrigin().contains("TEST") || payment.getOrigin().contains("MOCK");
    }

    private boolean checkVelocityLimit(Payment payment) {
        return false;
    }

    public record FraudAnalysisResult(
        String transactionId,
        boolean approved,
        String highRiskReason,
        String suspiciousPatternReason,
        String velocityExceededReason
    ) {
        public String riskLevel() {
            if (!approved) return "HIGH";
            if (highRiskReason != null) return "MEDIUM";
            return "LOW";
        }

        public String rejectionReasons() {
            StringBuilder reasons = new StringBuilder();
            if (highRiskReason != null) reasons.append(highRiskReason).append("; ");
            if (suspiciousPatternReason != null) reasons.append(suspiciousPatternReason).append("; ");
            if (velocityExceededReason != null) reasons.append(velocityExceededReason).append("; ");
            return reasons.toString();
        }
    }
}