package org.anubhav.student_management.exception.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import org.anubhav.student_management.exception.DependencyUnavailableException;
import org.anubhav.student_management.exception.NotFoundException;
import org.anubhav.model.ErrorDetail;
import org.anubhav.model.FailureResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<FailureResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        List<ErrorDetail> errors = ex.getConstraintViolations().stream().map(violation -> {
            String parameterName = extractParameterName(violation);
            return new ErrorDetail(ErrorDetail.TypeEnum.INVALID_PARAMETER, violation.getMessage())
                    .errorParameter(parameterName);
        }).toList();

        return ResponseEntity.badRequest().body(new FailureResponse(FailureResponse.RequestStatusEnum.FAILURE, errors));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FailureResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ErrorDetail> errors = ex.getBindingResult().getFieldErrors().stream().map(this::toErrorDetail).toList();

        return ResponseEntity.badRequest().body(new FailureResponse(FailureResponse.RequestStatusEnum.FAILURE, errors));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<FailureResponse> handleNotFoundException(NotFoundException ex) {
        ErrorDetail errorDetail = new ErrorDetail(ErrorDetail.TypeEnum.NOT_FOUND, ex.getMessage())
                .errorParameter(ex.getParameterName());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new FailureResponse(FailureResponse.RequestStatusEnum.FAILURE, List.of(errorDetail)));
    }

    @ExceptionHandler(DependencyUnavailableException.class)
    public ResponseEntity<FailureResponse> handleDependencyUnavailableException(DependencyUnavailableException ex) {
        ErrorDetail errorDetail = new ErrorDetail(ErrorDetail.TypeEnum.SERVICE_UNAVAILABLE, ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new FailureResponse(FailureResponse.RequestStatusEnum.FAILURE, List.of(errorDetail)));
    }

    private String extractParameterName(ConstraintViolation<?> violation) {
        String path = violation.getPropertyPath().toString();
        return path.substring(path.lastIndexOf('.') + 1);
    }

    private ErrorDetail toErrorDetail(FieldError fieldError) {
        return new ErrorDetail(ErrorDetail.TypeEnum.INVALID_PARAMETER, fieldError.getDefaultMessage())
                .errorParameter(fieldError.getField());
    }

}
