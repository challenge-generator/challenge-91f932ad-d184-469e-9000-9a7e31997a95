package com.pragma.payment.domain.exception;

public class PaymentGatewayException extends Exception {
    private final String transactionId;
    private final String gatewayCode;
    private final String gatewayMessage;
    private final boolean isRetryable;

    public PaymentGatewayException(String message, String transactionId, String gatewayCode, String gatewayMessage) {
        super(message);
        this.transactionId = transactionId;
        this.gatewayCode = gatewayCode;
        this.gatewayMessage = gatewayMessage;
        this.isRetryable = isRetryableError(gatewayCode);
    }

    public PaymentGatewayException(String message, Throwable cause, String transactionId, 
                                    String gatewayCode, String gatewayMessage) {
        super(message, cause);
        this.transactionId = transactionId;
        this.gatewayCode = gatewayCode;
        this.gatewayMessage = gatewayMessage;
        this.isRetryable = isRetryableError(gatewayCode);
    }

    private boolean isRetryableError(String code) {
        return "TIMEOUT".equals(code) || "CONNECTION_ERROR".equals(code) || "503".equals(code);
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getGatewayCode() {
        return gatewayCode;
    }

    public String getGatewayMessage() {
        return gatewayMessage;
    }

    public boolean isRetryable() {
        return isRetryable;
    }

    public String getErrorDetails() {
        return String.format("Transaction: %s | Gateway Code: %s | Gateway Message: %s | Retryable: %s | Cause: %s",
                transactionId, gatewayCode, gatewayMessage, isRetryable, getCause() != null ? getCause().getMessage() : "N/A");
    }
}