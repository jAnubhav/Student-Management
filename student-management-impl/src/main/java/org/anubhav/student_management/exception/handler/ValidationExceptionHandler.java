package org.anubhav.student_management.exception.handler;

import org.anubhav.model.ErrorDetail;
import org.anubhav.model.FailureResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<FailureResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(
                new FailureResponse(
                        FailureResponse.RequestStatusEnum.FAILURE,
                        List.of(new ErrorDetail(ErrorDetail.TypeEnum.BAD_REQUEST, ex.getMessage()))
                )
        );
    }

}
