package org.anubhav.student_management.exception.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.anubhav.model.ErrorDetail;
import org.anubhav.model.FailureResponse;
import org.anubhav.student_management.exception.DependencyUnavailableException;
import org.anubhav.student_management.exception.NotFoundException;
import org.anubhav.student_management.utils.Constants;
import org.anubhav.student_management.utils.TestType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.springframework.http.HttpStatus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.core.MethodParameter;
import org.mockito.Mockito;

import java.util.Set;
import java.util.stream.Stream;

@Tag(TestType.UNIT)
class ValidationExceptionHandlerTest {

    private final ValidationExceptionHandler handler = new ValidationExceptionHandler();

    @ParameterizedTest
    @MethodSource("handlerCases")
    void testValidationHandlerCases(Exception ex, HttpStatus expectedStatus, String expectedType,
            String expectedErrorParameter) {
        ResponseEntity<FailureResponse> response = (ex instanceof DependencyUnavailableException dependencyException)
                ? handler.handleDependencyUnavailableException(dependencyException)
                : handler.handleNotFoundException((NotFoundException) ex);

        Assertions.assertNotNull(response, Constants.RESPONSE_NOT_NULL.toString());

        FailureResponse failureResponse = response.getBody();
        Assertions.assertNotNull(failureResponse, Constants.FAILURE_RESPONSE_NOT_NULL.toString());

        Assertions.assertEquals(expectedStatus, response.getStatusCode(), Constants.INVALID_STATUS_CODE.toString());
        Assertions.assertEquals(
                FailureResponse.RequestStatusEnum.FAILURE,
                failureResponse.getRequestStatus(),
                Constants.REQUEST_STATUS_FAILURE.toString()
        );

        ErrorDetail errorDetail = failureResponse.getErrors().get(0);
        Assertions.assertEquals(
                expectedType,
                errorDetail.getType().getValue(),
                Constants.ERROR_TYPE_NOT_MATCH.toString()
        );
        if (expectedErrorParameter == null) {
            Assertions.assertNull(errorDetail.getErrorParameter());
        } else {
            Assertions.assertEquals(
                    expectedErrorParameter,
                    errorDetail.getErrorParameter(),
                    Constants.ERROR_PARAMETER_NOT_MATCH.toString()
            );
        }
    }

    @ParameterizedTest
    @MethodSource("validationViolationCases")
    void testValidationViolationHandlers(boolean methodArgumentViolation, String fieldOrParameter, String message) {
        ResponseEntity<FailureResponse> response;
        if (methodArgumentViolation) {
            BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "request");
            bindingResult.addError(new FieldError("request", fieldOrParameter, message));
            MethodArgumentNotValidException ex = new MethodArgumentNotValidException(
                    Mockito.mock(MethodParameter.class),
                    bindingResult
            );
            response = handler.handleMethodArgumentNotValidException(ex);
        } else {
            @SuppressWarnings("unchecked")
            ConstraintViolation<Object> violation = (ConstraintViolation<Object>) Mockito
                    .mock(ConstraintViolation.class);
            Path path = Mockito.mock(Path.class);
            Mockito.when(path.toString()).thenReturn("method." + fieldOrParameter);
            Mockito.when(violation.getMessage()).thenReturn(message);
            Mockito.when(violation.getPropertyPath()).thenReturn(path);

            ConstraintViolationException ex = new ConstraintViolationException(Set.of(violation));
            response = handler.handleConstraintViolationException(ex);
        }

        Assertions.assertNotNull(response, Constants.RESPONSE_NOT_NULL.toString());
        FailureResponse failureResponse = response.getBody();
        Assertions.assertNotNull(failureResponse, Constants.FAILURE_RESPONSE_NOT_NULL.toString());
        Assertions.assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode(),
                Constants.INVALID_STATUS_CODE.toString()
        );
        Assertions.assertEquals(
                FailureResponse.RequestStatusEnum.FAILURE,
                failureResponse.getRequestStatus(),
                Constants.REQUEST_STATUS_FAILURE.toString()
        );
        Assertions.assertEquals(
                "INVALID_PARAMETER",
                failureResponse.getErrors().get(0).getType().getValue(),
                Constants.ERROR_TYPE_NOT_MATCH.toString()
        );
        Assertions.assertEquals(
                fieldOrParameter,
                failureResponse.getErrors().get(0).getErrorParameter(),
                Constants.ERROR_PARAMETER_NOT_MATCH.toString()
        );
        Assertions.assertEquals(
                message,
                failureResponse.getErrors().get(0).getMessage(),
                Constants.PARENT_DETAILS_NOT_MATCH.toString()
        );
    }

    private static Stream<Arguments> handlerCases() {
        return Stream.of(
                Arguments.of(
                        new NotFoundException("Student not found", "enrollmentNumber"),
                        HttpStatus.NOT_FOUND,
                        "NOT_FOUND",
                        "enrollmentNumber"
                ),
                Arguments.of(
                        new DependencyUnavailableException("Student service is unavailable."),
                        HttpStatus.SERVICE_UNAVAILABLE,
                        "SERVICE_UNAVAILABLE",
                        null
                )
        );
    }

    private static Stream<Arguments> validationViolationCases() {
        return Stream.of(
                Arguments.of(false, "enrollmentNumber", "Enrollment number is invalid"),
                Arguments.of(false, "parentId", "Parent ID is invalid"),
                Arguments.of(true, "firstName", "First name is required"),
                Arguments.of(true, "zipcode", "Zipcode must be 6 digits")
        );
    }

}
