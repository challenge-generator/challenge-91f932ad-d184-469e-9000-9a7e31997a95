package com.pragma.payment.infrastructure.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidationErrorResponse {
    private final int status;
    private final String error;
    private final String message;
    private final String path;
    private final LocalDateTime timestamp;
    private final List<FieldError> errors;

    public ValidationErrorResponse(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
        this.errors = new ArrayList<>();
    }

    private ValidationErrorResponse(Builder builder) {
        this.status = builder.status;
        this.error = builder.error;
        this.message = builder.message;
        this.path = builder.path;
        this.timestamp = builder.timestamp != null ? builder.timestamp : LocalDateTime.now();
        this.errors = builder.errors != null ? builder.errors : new ArrayList<>();
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

    public List<FieldError> getErrors() {
        return errors;
    }

    public void addFieldError(String field, String message, String code) {
        if (field == null || field.isBlank()) {
            throw new IllegalArgumentException("El nombre del campo no puede ser nulo o vacio");
        }
        this.errors.add(new FieldError(field, message, code));
    }

    public void addFieldErrors(List<FieldError> fieldErrors) {
        if (fieldErrors != null) {
            this.errors.addAll(fieldErrors);
        }
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public int getErrorCount() {
        return errors.size();
    }

    public List<String> getAllErrorMessages() {
        return errors.stream()
                .map(FieldError::getMessage)
                .collect(Collectors.toList());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class FieldError {
        private final String field;
        private final String message;
        private final String code;

        public FieldError(String field, String message, String code) {
            if (field == null || field.isBlank()) {
                throw new IllegalArgumentException("El nombre del campo no puede ser nulo o vacio");
            }
            this.field = field;
            this.message = message;
            this.code = code;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }

        public String getCode() {
            return code;
        }
    }

    public static class Builder {
        private int status = 400;
        private String error = "Bad Request";
        private String message = "Validacion fallida";
        private String path;
        private LocalDateTime timestamp;
        private List<FieldError> errors;

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

        public Builder errors(List<FieldError> errors) {
            this.errors = errors;
            return this;
        }

        public ValidationErrorResponse build() {
            if (path == null || path.isBlank()) {
                throw new IllegalStateException("La ruta de la peticion es requerida");
            }
            return new ValidationErrorResponse(this);
        }
    }
}