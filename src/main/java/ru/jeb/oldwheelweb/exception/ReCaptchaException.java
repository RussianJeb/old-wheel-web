package ru.jeb.oldwheelweb.exception;

/**
 * @author Jeb
 */
public class ReCaptchaException extends RuntimeException {
    public ReCaptchaException() {
    }

    public ReCaptchaException(String message) {
        super(message);
    }
}
