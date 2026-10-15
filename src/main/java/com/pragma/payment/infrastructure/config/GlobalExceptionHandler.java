package com.pragma.payment.infrastructure.config;



import com.pragma.payment.domain.Payment;
import com.pragma.payment.infrastructure.controller.ErrorResponse;
import com.pragma.payment.domain.exception.CreditOriginatorException;
import com.pragma.payment.domain.exception.FraudDetectionException;
import com.pragma.payment.domain.exception.PaymentGatewayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CreditOriginatorException.class)
    public ResponseEntity<ErrorResponse> handleCreditOriginatorException(
            CreditOriginatorException ex, WebRequest request) {
        logger.error("Error del originador de creditos: transactionId={}, code={}, details={}",
                ex.getTransactionId(), ex.getOriginatorCode(), ex.getErrorDetails(), ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_GATEWAY.value())
                .error("Credit Originator Error")
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .transactionId(ex.getTransactionId())
                .developerMessage("El originador de creditos rechazo la transaccion. Verificar el codigo del originador: " 
                        + ex.getOriginatorCode())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorResponse);
    }

    @ExceptionHandler(FraudDetectionException.class)
    public ResponseEntity<ErrorResponse> handleFraudDetectionException(
            FraudDetectionException ex, WebRequest request) {
        logger.error("Error en detector de fraude: transactionId={}, riskLevel={}, details={}",
                ex.getTransactionId(), ex.getRiskLevel(), ex.getErrorDetails(), ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .error("Fraud Detection Error")
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .transactionId(ex.getTransactionId())
                .developerMessage("Transaccion marcada como fraude. Nivel de riesgo: " + ex.getRiskLevel())
                .build();

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
    }

    @ExceptionHandler(PaymentGatewayException.class)
    public ResponseEntity<ErrorResponse> handlePaymentGatewayException(
            PaymentGatewayException ex, WebRequest request) {
        logger.error("Error del gateway de pagos: transactionId={}, gatewayCode={}, details={}",
                ex.getTransactionId(), ex.getGatewayCode(), ex.getErrorDetails(), ex);

        HttpStatus status = determineHttpStatus(ex.getErrorCode());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .error("Payment Gateway Error")
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .transactionId(ex.getTransactionId())
                .developerMessage("Error del gateway de pagos. Codigo de error: " + ex.getErrorCode())
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {
        logger.warn("Error de validacion en la peticion: {}", request.getDescription(false));

        List<ValidationErrorResponse.FieldError> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ValidationErrorResponse.FieldError(
                        error.getField(),
                        error.getDefaultMessage() != null ? error.getDefaultMessage() : "Campo invalido",
                        error.getCode()))
                .collect(Collectors.toList());

        ValidationErrorResponse response = ValidationErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Validation Failed")
                .message("Los datos de la solicitud no son validos")
                .path(request.getDescription(false).replace("uri=", ""))
                .errors(fieldErrors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        logger.warn("Tipo de argumento invalido: {} - valor proporcionado: {}",
                ex.getName(), ex.getValue());

        String message = String.format("El parametro '%s' tiene un formato invalido. Se esperaba un tipo '%s'",
                ex.getName(), ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconocido");

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Type Mismatch")
                .message(message)
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {
        logger.warn("Argumento ilegal: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("Error inesperado no manejado: {}", request.getDescription(false), ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message("Ocurrio un error interno en el servidor. Por favor, contacte al administrador")
                .path(request.getDescription(false).replace("uri=", ""))
                .developerMessage(ex.getClass().getName() + ": " + ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    private HttpStatus determineHttpStatus(String errorCode) {
        if (errorCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return switch (errorCode) {
            case "GATEWAY_TIMEOUT" -> HttpStatus.GATEWAY_TIMEOUT;
            case "SERVICE_UNAVAILABLE" -> HttpStatus.SERVICE_UNAVAILABLE;
            case "CONFLICT" -> HttpStatus.CONFLICT;
            default -> HttpStatus.BAD_GATEWAY;
        };
    }
}