package ru.jeb.oldwheelweb.exception;

/**
 * @author Jeb
 */
public class UsernameAlreadyExistException extends RuntimeException {
    public UsernameAlreadyExistException() {
    }

    public UsernameAlreadyExistException(String message) {
        super(message);
    }
}
