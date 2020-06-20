package ru.jeb.oldwheelweb.model.dto.account;

import lombok.Data;
import ru.jeb.oldwheelweb.model.entity.Account;

/**
 * @author Jeb
 */
@Data
public class SimpleAccountInfoDTO {
    private String name;
    private Account.Role role;

    public SimpleAccountInfoDTO(Account account) {
        this.name = account.getUsername();
        this.role = account.getRole();
    }
}
