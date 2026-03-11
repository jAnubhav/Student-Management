package org.anubhav.student_management.exception.handler;

import java.util.List;
import org.anubhav.model.ErrorDetail;
import org.anubhav.model.FailureResponse;
import org.anubhav.student_management.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NotFoundExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<FailureResponse> handleNotFoundException(NotFoundException ex) {
        ErrorDetail errorDetail = new ErrorDetail(ErrorDetail.TypeEnum.NOT_FOUND, ex.getMessage())
                .errorParameter(ex.getParameterName());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new FailureResponse(FailureResponse.RequestStatusEnum.FAILURE, List.of(errorDetail)));
    }

}
