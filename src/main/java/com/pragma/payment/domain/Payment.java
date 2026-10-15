package com.pragma.payment.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Payment {
    private UUID id;

    @NotNull(message = "El ID de la transacción no puede ser nulo")
    private String transactionId;

    @NotNull(message = "El monto no puede ser nulo")
    @Positive(message = "El monto debe ser positivo")
    private BigDecimal amount;

    @NotNull(message = "La moneda no puede ser nula")
    @Size(min = 3, max = 3, message = "La moneda debe tener 3 caracteres")
    private String currency;

    @NotNull(message = "El estado del pago no puede ser nulo")
    private PaymentStatus status;

    @NotNull(message = "El origen del pago no puede ser nulo")
    private String origin;

    @NotNull(message = "La fecha de creación no puede ser nula")
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    private String failureReason;

    public enum PaymentStatus {
        PENDING,
        COMPLETED,
        FAILED,
        REJECTED
    }

    public Payment() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.status = PaymentStatus.PENDING;
    }

    public Payment(UUID id, String transactionId, BigDecimal amount, String currency, 
                  PaymentStatus status, String origin, LocalDateTime createdAt, 
                  LocalDateTime updatedAt, String failureReason) {
        this.id = id != null ? id : UUID.randomUUID();
        this.transactionId = transactionId;
        this.amount = amount;
        this.currency = currency;
        this.status = status != null ? status : PaymentStatus.PENDING;
        this.origin = origin;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.updatedAt = updatedAt;
        this.failureReason = failureReason;
    }

    public void markAsCompleted() {
        this.status = PaymentStatus.COMPLETED;
        this.updatedAt = LocalDateTime.now();
        this.failureReason = null;
    }

    public void markAsFailed(String reason) {
        this.status = PaymentStatus.FAILED;
        this.updatedAt = LocalDateTime.now();
        this.failureReason = reason;
    }

    public void markAsRejected(String reason) {
        this.status = PaymentStatus.REJECTED;
        this.updatedAt = LocalDateTime.now();
        this.failureReason = reason;
    }
}