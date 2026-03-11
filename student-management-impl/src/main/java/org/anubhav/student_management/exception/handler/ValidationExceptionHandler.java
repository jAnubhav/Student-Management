package org.anubhav.student_management.exception.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.anubhav.model.ErrorDetail;
import org.anubhav.model.FailureResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<FailureResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        List<ErrorDetail> errors = ex.getConstraintViolations().stream().map(violation -> {
            String parameterName = extractParameterName(violation);
            String message = resolveMessage(violation, parameterName);
            return new ErrorDetail(ErrorDetail.TypeEnum.INVALID_PARAMETER, message).errorParameter(parameterName);
        }).toList();

        return ResponseEntity.badRequest().body(new FailureResponse(FailureResponse.RequestStatusEnum.FAILURE, errors));
    }

    private String extractParameterName(ConstraintViolation<?> violation) {
        String path = violation.getPropertyPath().toString();
        return path.substring(path.lastIndexOf('.') + 1);
    }

    private String resolveMessage(ConstraintViolation<?> violation, String parameterName) {
        if (violation.getConstraintDescriptor().getAnnotation().annotationType() == Size.class) {
            String key = parameterName + ".size";
            try {
                ResourceBundle bundle = ResourceBundle.getBundle("ValidationMessages", LocaleContextHolder.getLocale());
                return bundle.getString(key);
            } catch (MissingResourceException ex) {
                return violation.getMessage();
            }
        }
        return violation.getMessage();
    }

}
