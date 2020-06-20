package ru.jeb.oldwheelweb.model.dto.launcher;

import lombok.Data;

/**
 * @author Jeb
 */
@Data
public class AuthErrorDTO {
    public AuthErrorDTO(String error) {
        this.error = error;
    }

    private String error;
}
