package ru.jeb.oldwheelweb.model.dto.account;

import lombok.Data;

/**
 * @author Jeb
 */
@Data
public class ForgotDTO {
    private String token;
    private String email;
}
