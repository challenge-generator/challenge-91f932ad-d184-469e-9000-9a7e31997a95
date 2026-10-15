package com.pragma.payment.infrastructure.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private final int status;
    private final String error;
    private final String message;
    private final String path;
    private final LocalDateTime timestamp;
    private final String transactionId;
    private final Map<String, List<String>> validationErrors;
    private final String developerMessage;

    public ErrorResponse(int status, String error, String message, String path) {
        if (status < 100 || status >= 600) {
            throw new IllegalArgumentException("Codigo de estado HTTP invalido: " + status);
        }
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("El mensaje de error no puede ser nulo o vacio");
        }
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
        this.transactionId = null;
        this.validationErrors = null;
        this.developerMessage = null;
    }

    private ErrorResponse(Builder builder) {
        this.status = builder.status;
        this.error = builder.error;
        this.message = builder.message;
        this.path = builder.path;
        this.timestamp = builder.timestamp != null ? builder.timestamp : LocalDateTime.now();
        this.transactionId = builder.transactionId;
        this.validationErrors = builder.validationErrors;
        this.developerMessage = builder.developerMessage;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Map<String, List<String>> getValidationErrors() {
        return validationErrors;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public boolean hasValidationErrors() {
        return validationErrors != null && !validationErrors.isEmpty();
    }

    public boolean hasTransactionId() {
        return transactionId != null && !transactionId.isBlank();
    }

    public boolean hasDeveloperMessage() {
        return developerMessage != null && !developerMessage.isBlank();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int status;
        private String error;
        private String message;
        private String path;
        private LocalDateTime timestamp;
        private String transactionId;
        private Map<String, List<String>> validationErrors;
        private String developerMessage;

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder error(String error) {
            this.error = error;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public Builder validationErrors(Map<String, List<String>> validationErrors) {
            this.validationErrors = validationErrors;
            return this;
        }

        public Builder developerMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public ErrorResponse build() {
            if (message == null || message.isBlank()) {
                throw new IllegalStateException("El mensaje de error es requerido");
            }
            return new ErrorResponse(this);
        }
    }
}