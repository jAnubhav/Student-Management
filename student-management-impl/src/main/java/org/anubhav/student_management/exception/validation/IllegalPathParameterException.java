package org.anubhav.student_management.exception.validation;

import lombok.Getter;

@Getter
public class IllegalPathParameterException extends IllegalArgumentException {

    private final String parameterName;

    public IllegalPathParameterException(String message, String parameterName) {
        super(message);
        this.parameterName = parameterName;
    }

}
