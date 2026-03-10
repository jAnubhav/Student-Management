package org.anubhav.student_management.exception.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.anubhav.model.ErrorDetail;
import org.anubhav.model.FailureResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

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

    private String extractParameterName(ConstraintViolation<?> violation) {
        String path = violation.getPropertyPath().toString();
        return path.substring(path.lastIndexOf('.') + 1);
    }

}
