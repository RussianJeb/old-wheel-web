package ru.jeb.oldwheelweb.model.dto.account;

import lombok.Data;

import java.util.UUID;

/**
 * @author Jeb
 */
@Data
public class VerificationDTO {
    private UUID token;
    private String user;
}
