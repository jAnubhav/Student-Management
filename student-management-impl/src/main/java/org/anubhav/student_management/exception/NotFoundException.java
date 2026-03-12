package org.anubhav.student_management.exception;

import lombok.Getter;

/**
 * Exception used when an expected domain record cannot be found.
 */
@Getter
public class NotFoundException extends RuntimeException {

    private final String parameterName;

    /**
     * Creates a not-found exception with message and failed parameter name.
     *
     * @param message
     *            error description
     * @param parameterName
     *            request field/path variable related to this failure
     */
    public NotFoundException(String message, String parameterName) {
        super(message);
        this.parameterName = parameterName;
    }

}
