package ru.jeb.oldwheelweb.model.dto.launcher;

import lombok.Data;

import java.util.UUID;

/**
 * @author Jeb
 */
@Data
public class UpdateAuthDTO {
    private UUID uuid;
    private String username;
    private String accessToken;
}
