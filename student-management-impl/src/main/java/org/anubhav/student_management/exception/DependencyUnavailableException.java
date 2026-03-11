package org.anubhav.student_management.exception;

/**
 * Signals that a required runtime dependency is unavailable.
 */
public class DependencyUnavailableException extends RuntimeException {

    /**
     * Creates a dependency-unavailable exception with a descriptive message.
     *
     * @param message reason for service unavailability
     */
    public DependencyUnavailableException(String message) {
        super(message);
    }

}
