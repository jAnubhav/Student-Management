package org.anubhav.student_management.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final String parameterName;

    public NotFoundException(String message, String parameterName) {
        super(message);
        this.parameterName = parameterName;
    }

}
