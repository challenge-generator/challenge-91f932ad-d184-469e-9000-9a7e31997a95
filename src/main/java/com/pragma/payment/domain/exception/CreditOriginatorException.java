package com.pragma.payment.domain.exception;

public class CreditOriginatorException extends Exception {
    private final String transactionId;
    private final String originatorCode;

    public CreditOriginatorException(String message, String transactionId, String originatorCode) {
        super(message);
        this.transactionId = transactionId;
        this.originatorCode = originatorCode;
    }

    public CreditOriginatorException(String message, Throwable cause, String transactionId, String originatorCode) {
        super(message, cause);
        this.transactionId = transactionId;
        this.originatorCode = originatorCode;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getOriginatorCode() {
        return originatorCode;
    }

    public String getErrorDetails() {
        return String.format("Transaction ID: %s, Originator Code: %s, Error: %s", 
                transactionId, originatorCode, getMessage());
    }
}