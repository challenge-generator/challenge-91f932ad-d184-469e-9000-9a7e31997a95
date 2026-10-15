package com.pragma.payment.infrastructure.controller;


import com.pragma.payment.domain.PaymentStatus;
import com.pragma.payment.domain.Payment;
import com.pragma.payment.domain.PaymentServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
@Tag(name = "Pagos", description = "API para procesamiento de pagos")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentServicePort paymentService;

    public PaymentController(PaymentServicePort paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @Operation(summary = "Procesar un pago", description = "Procesa una solicitud de pago validando el originador de creditos, el detector de fraude y el gateway de pagos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pago procesado exitosamente",
                    content = @Content(schema = @Schema(implementation = PaymentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud invalida - datos de entrada incorrectos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)),
                    responseCode = "400"),
            @ApiResponse(responseCode = "422", description = "Transaccion rechazada por fraude",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)),
                    responseCode = "422"),
            @ApiResponse(responseCode = "502", description = "Error del originador de creditos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)),
                    responseCode = "502"),
            @ApiResponse(responseCode = "503", description = "Gateway de pagos no disponible",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)),
                    responseCode = "503"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)),
                    responseCode = "500")
    })
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest request) {
        logger.info("Recibida solicitud de pago: amount={}, currency={}, origin={}",
                request.amount(), request.currency(), request.origin());

        Payment payment = new Payment(
                UUID.randomUUID(),
                UUID.randomUUID().toString(),
                request.amount(),
                request.currency(),
                Payment.PaymentStatus.PENDING,
                request.origin()
        );

        Payment processedPayment = paymentService.processPayment(payment);
        logger.info("Pago procesado exitosamente: transactionId={}, status={}",
                processedPayment.getTransactionId(), processedPayment.getStatus());

        PaymentResponse response = toPaymentResponse(processedPayment);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{paymentId}")
    @Operation(summary = "Obtener un pago por ID", description = "Recupera los detalles de un pago especifica usando su identificador unico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago encontrado",
                    content = @Content(schema = @Schema(implementation = PaymentResponse.class))),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)),
                    responseCode = "404"),
            @ApiResponse(responseCode = "400", description = "Formato de ID invalido",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)),
                    responseCode = "400")
    })
    public ResponseEntity<PaymentResponse> getPaymentById(
            @Parameter(description = "ID unico del pago", required = true)
            @PathVariable UUID paymentId) {
        logger.debug("Buscando pago con ID: {}", paymentId);

        Payment payment = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(toPaymentResponse(payment));
    }

    @GetMapping
    @Operation(summary = "Listar pagos por estado", description = "Recupera todos los pagos que coinciden con el estado especificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pagos recuperada exitosamente",
                    content = @Content(schema = @Schema(implementation = PaymentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Estado de pago invalido",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)),
                    responseCode = "400")
    })
    public ResponseEntity<List<PaymentResponse>> getPaymentsByStatus(
            @Parameter(description = "Estado del pago (PENDING, COMPLETED, FAILED, REJECTED)", required = true)
            @RequestParam Payment.PaymentStatus status) {
        logger.info("Buscando pagos con estado: {}", status);

        List<Payment> payments = paymentService.getPaymentsByStatus(status);
        List<PaymentResponse> responses = payments.stream()
                .map(this::toPaymentResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{paymentId}/status")
    @Operation(summary = "Actualizar estado del pago", description = "Actualiza el estado de un pago existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = PaymentResponse.class))),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)),
                    responseCode = "404"),
            @ApiResponse(responseCode = "400", description = "Datos de actualizacion invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)),
                    responseCode = "400")
    })
    public ResponseEntity<PaymentResponse> updatePaymentStatus(
            @Parameter(description = "ID unico del pago", required = true)
            @PathVariable UUID paymentId,
            @RequestBody UpdatePaymentStatusRequest request) {
        logger.info("Actualizando estado del pago {} a {}", paymentId, request.newStatus());

        Payment updatedPayment = paymentService.updatePaymentStatus(
                paymentId,
                request.newStatus(),
                request.failureReason()
        );

        return ResponseEntity.ok(toPaymentResponse(updatedPayment));
    }

    private PaymentResponse toPaymentResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId().toString(),
                payment.getTransactionId(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getStatus().name(),
                payment.getOrigin(),
                payment.getCreatedAt().toString(),
                payment.getUpdatedAt() != null ? payment.getUpdatedAt().toString() : null,
                payment.getFailureReason()
        );
    }

    public record PaymentRequest(
            @Parameter(description = "Monto del pago", required = true, example = "100.00")
            BigDecimal amount,
            @Parameter(description = "Moneda del pago (USD, EUR, etc.)", required = true, example = "USD")
            String currency,
            @Parameter(description = "Origen del pago", required = true, example = "ONLINE_STORE")
            String origin
    ) {}

    public record PaymentResponse(
            String id,
            String transactionId,
            BigDecimal amount,
            String currency,
            String status,
            String origin,
            String createdAt,
            String updatedAt,
            String failureReason
    ) {}

    public record UpdatePaymentStatusRequest(
            @Parameter(description = "Nuevo estado del pago", required = true)
            Payment.PaymentStatus newStatus,
            @Parameter(description = "Razon del fallo (requerido si el estado es FAILED)")
            String failureReason
    ) {}

    public record ErrorResponse(
            int status,
            String error,
            String message,
            String path
    ) {}
}