package org.anubhav.student_management.utility;

import lombok.Getter;

/**
 * Shared constant keys used in API validation and error responses.
 */
@Getter
public enum Constants {

    ENROLLMENT_NUMBER_PATH_VARIABLE_NAME("enrollmentNumber"), PARENT_ID_PATH_VARIABLE_NAME("parentId");

    private final String value;

    Constants(String value) {
        this.value = value;
    }

}
