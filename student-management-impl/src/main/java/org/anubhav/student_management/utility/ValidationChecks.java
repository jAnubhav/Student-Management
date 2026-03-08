package org.anubhav.student_management.utility;

import lombok.experimental.UtilityClass;

import org.anubhav.student_management.exception.validation.IllegalPathParameterException;

@UtilityClass
public class ValidationChecks {

    public void validateParameter(String parameter, String parameterName, int expectedLength) {
        if (parameter.isBlank()) {
            throw new IllegalPathParameterException(
                    String.format("Path parameter %s cannot be null or empty", parameterName),
                    parameterName
            );
        }

        if (parameter.length() != expectedLength) {
            throw new IllegalPathParameterException(
                    String.format("Path parameter %s should be of length %d.", parameterName, expectedLength),
                    parameterName
            );
        }
    }

    public void validateParameterValue(String parameter, String parameterName) {
        if (!parameter.matches("-?\\d+")) {
            throw new IllegalPathParameterException(
                    String.format("Path parameter %s should be a number.", parameterName),
                    parameterName
            );
        }
    }

}
