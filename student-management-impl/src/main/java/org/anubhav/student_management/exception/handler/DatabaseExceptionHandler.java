package org.anubhav.student_management.exception.handler;

import java.util.List;
import org.anubhav.model.ErrorDetail;
import org.anubhav.model.FailureResponse;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DatabaseExceptionHandler {

    @ExceptionHandler({DuplicateKeyException.class, DataIntegrityViolationException.class})
    public ResponseEntity<FailureResponse> handleConflictExceptions(DataAccessException ex) {
        ErrorDetail error = new ErrorDetail(
                ErrorDetail.TypeEnum.CONFLICT,
                "Database conflict occurred while processing the request."
        );
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new FailureResponse(FailureResponse.RequestStatusEnum.FAILURE, List.of(error)));
    }

    @ExceptionHandler({CannotGetJdbcConnectionException.class, DataAccessResourceFailureException.class,
            CannotAcquireLockException.class, QueryTimeoutException.class, TransactionSystemException.class})
    public ResponseEntity<FailureResponse> handleDatabaseAvailabilityExceptions(Exception ex) {
        ErrorDetail error = new ErrorDetail(
                ErrorDetail.TypeEnum.SERVICE_UNAVAILABLE,
                "Database service is temporarily unavailable. Please try again."
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new FailureResponse(FailureResponse.RequestStatusEnum.FAILURE, List.of(error)));
    }

}
