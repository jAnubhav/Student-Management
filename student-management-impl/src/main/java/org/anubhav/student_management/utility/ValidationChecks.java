package org.anubhav.student_management.utility;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationChecks {
    public void validateParameter(String parameter, String parameterName, int expectedLength) {
        if (parameter.isBlank() || parameter.length() != expectedLength) {
            throw new IllegalArgumentException(
                    String.format("Parameter %s cannot be null or less than %d.", parameterName, expectedLength)
            );
        }
    }

}
