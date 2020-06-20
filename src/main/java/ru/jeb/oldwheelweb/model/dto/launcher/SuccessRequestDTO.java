package ru.jeb.oldwheelweb.model.dto.launcher;

import lombok.Data;

/**
 * @author Jeb
 */
@Data
public class SuccessRequestDTO {
    private boolean success;

    public SuccessRequestDTO(boolean success) {
        this.success = success;
    }
}
