package com.muss_and_toeberg.snake_that.technical;

/**
 * custom Exception for when an obstacle is not a circle but is used as one
 */
public class IsNoCircleException extends Exception {
    /**
     * parameterless constructor
     */
    public IsNoCircleException() {
        super();
    }

    /**
     * constructor for a message
     * @param message string to describe the exception
     */
    public IsNoCircleException(String message) {
        super(message);
    }

    /**
     * constructor for the cause
     * @param cause cause for the exception
     */
    public IsNoCircleException(Throwable cause) {
        super(cause);
    }

    /**
     * constructor for a message and the cause
     * @param message string to describe the exception
     * @param cause cause for the exception
     */
    public IsNoCircleException(String message, Throwable cause) {
        super(message, cause);
    }
}
