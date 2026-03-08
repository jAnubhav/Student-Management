package org.anubhav.student_management.utility;

import lombok.experimental.UtilityClass;
import org.anubhav.student_management.exception.validation.IllegalPathParameterException;

@UtilityClass
public class ValidationChecks {

    public void validateParameter(String parameter, String parameterName, int exLength) {
        if (parameter.isBlank() || parameter.length() != exLength) {
            throw new IllegalPathParameterException(
                    String.format("%s cannot be null and length cannot be smaller than %d.", parameterName, exLength),
                    parameterName
            );
        }
    }

    public void validateParameterValue(String parameter, String parameterName) {
        if (!parameter.matches("-?\\d+")) {
            throw new IllegalPathParameterException(
                    String.format("%s should be a number.", parameterName),
                    parameterName
            );
        }
    }

}
