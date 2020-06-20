package ru.jeb.oldwheelweb.model.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author Jeb
 */
@Data
public class Error {
    private String error;
    private int status;

    public Error(String error) {
        this.error = error;
        this.status = HttpStatus.BAD_REQUEST.value();
    }

    public Error(String error, int status) {
        this.error = error;
        this.status = status;
    }
}
