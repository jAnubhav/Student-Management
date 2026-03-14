package org.anubhav.student_management.exception.handler;

import org.anubhav.model.FailureResponse;
import org.anubhav.student_management.utils.Constants;
import org.anubhav.student_management.utils.TestType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@Tag(TestType.UNIT)
class DatabaseExceptionHandlerTest {

    private final DatabaseExceptionHandler handler = new DatabaseExceptionHandler();

    @ParameterizedTest
    @MethodSource("databaseExceptionCases")
    void testDatabaseExceptionHandlers(Exception ex, HttpStatus expectedStatus, String expectedType) {
        ResponseEntity<FailureResponse> response = (ex instanceof DuplicateKeyException duplicateKeyException)
                ? handler.handleConflictExceptions(duplicateKeyException)
                : handler.handleDatabaseAvailabilityExceptions(ex);

        Assertions.assertNotNull(response, Constants.RESPONSE_NOT_NULL.toString());

        FailureResponse failureResponse = response.getBody();
        Assertions.assertNotNull(failureResponse, Constants.FAILURE_RESPONSE_NOT_NULL.toString());

        Assertions.assertEquals(expectedStatus, response.getStatusCode(), Constants.INVALID_STATUS_CODE.toString());
        Assertions.assertEquals(
                FailureResponse.RequestStatusEnum.FAILURE,
                failureResponse.getRequestStatus(),
                Constants.REQUEST_STATUS_FAILURE.toString()
        );
        Assertions.assertEquals(
                expectedType,
                failureResponse.getErrors().get(0).getType().getValue(),
                Constants.ERROR_TYPE_NOT_MATCH.toString()
        );
    }

    private static Stream<Arguments> databaseExceptionCases() {
        return Stream.of(
                Arguments.of(new DuplicateKeyException("duplicate"), HttpStatus.CONFLICT, "CONFLICT"),
                Arguments.of(
                        new CannotGetJdbcConnectionException("down"),
                        HttpStatus.SERVICE_UNAVAILABLE,
                        "SERVICE_UNAVAILABLE"
                )
        );
    }

}
