package ru.jeb.oldwheelweb.model.dto.account;

import lombok.Data;

import java.util.UUID;

/**
 * @author Jeb
 */
@Data
public class RestorePasswordDTO {
    private UUID token;
    private String user;
    private String password;
}
