package ru.jeb.oldwheelweb.model.dto.account;

import lombok.Data;
import ru.jeb.oldwheelweb.model.entity.Account;

/**
 * @author Jeb
 */
@Data
public class LoginDTO {
    private String token;
    private Account.Role role;

    public LoginDTO(String token, Account.Role roles) {
        this.token = token;
        this.role = roles;
    }
}
