package org.anubhav.student_management.utils;

import lombok.Getter;

@Getter
public enum Constants {

    RESPONSE_NOT_NULL("Response should not be null"), SUCCESS_RESPONSE_NOT_NULL(
            "Success response should not be null"), INVALID_STATUS_CODE("Invalid status code"), REQUEST_STATUS_SUCCESS(
                    "Request status should have been SUCCESS"), PARENT_DETAILS_NOT_MATCH(
                            "Parent details in response should match the service response"), STUDENT_DETAILS_NOT_MATCH(
                                    "Student details in response should match the service response"), STUDENT_ASSIGNED_NOT_MATCH(
                                            "Student assigned details should match expected value"), REQUEST_STATUS_FAILURE(
                                                    "Request status should have been FAILURE"), FAILURE_RESPONSE_NOT_NULL(
                                                            "Failure response should not be null"), ERROR_TYPE_NOT_MATCH(
                                                                    "Error type should match expected value"), ERROR_PARAMETER_NOT_MATCH(
                                                                            "Error parameter should match expected value"), EXCEPTION_NOT_THROWN(
                                                                                    "Expected exception was not thrown"), ENTITY_NOT_NULL(
                                                                                            "Entity/value should not be null");

    private final String message;

    Constants(String message) {
        this.message = message;
    }

}
