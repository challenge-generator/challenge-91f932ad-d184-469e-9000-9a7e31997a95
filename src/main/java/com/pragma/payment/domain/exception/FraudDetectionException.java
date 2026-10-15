package com.pragma.payment.domain.exception;

public class FraudDetectionException extends Exception {
    private final String transactionId;
    private final String fraudScore;
    private final String riskLevel;

    public FraudDetectionException(String message, String transactionId, String fraudScore, String riskLevel) {
        super(message);
        this.transactionId = transactionId;
        this.fraudScore = fraudScore;
        this.riskLevel = riskLevel;
    }

    public FraudDetectionException(String message, Throwable cause, String transactionId, String fraudScore, String riskLevel) {
        super(message, cause);
        this.transactionId = transactionId;
        this.fraudScore = fraudScore;
        this.riskLevel = riskLevel;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getFraudScore() {
        return fraudScore;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public String getErrorDetails() {
        return String.format("Transaction: %s | Fraud Score: %s | Risk Level: %s | Message: %s",
                transactionId, fraudScore, riskLevel, getMessage());
    }
}