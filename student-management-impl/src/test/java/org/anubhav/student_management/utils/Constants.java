package org.anubhav.student_management.utils;

import lombok.Getter;

import java.util.ResourceBundle;

@Getter
public enum Constants {

    RESPONSE_NOT_NULL, SUCCESS_RESPONSE_NOT_NULL, INVALID_STATUS_CODE, REQUEST_STATUS_SUCCESS, PARENT_DETAILS_NOT_MATCH, STUDENT_DETAILS_NOT_MATCH, STUDENT_ASSIGNED_NOT_MATCH, REQUEST_STATUS_FAILURE, FAILURE_RESPONSE_NOT_NULL, ERROR_TYPE_NOT_MATCH, ERROR_PARAMETER_NOT_MATCH, EXCEPTION_NOT_THROWN, ENTITY_NOT_NULL;

    private final String message;

    Constants() {
        this.message = ResourceBundle.getBundle("test-messages").getString(name());
    }

    @Override
    public String toString() {
        return message;
    }

}
