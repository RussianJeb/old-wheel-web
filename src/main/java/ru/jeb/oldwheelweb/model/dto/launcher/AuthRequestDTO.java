package ru.jeb.oldwheelweb.model.dto.launcher;

import lombok.Data;

/**
 * @author Jeb
 */
@Data
public class AuthRequestDTO {
    private String username;
    private String password;
    private String ip;
    private String apiKey;
}
