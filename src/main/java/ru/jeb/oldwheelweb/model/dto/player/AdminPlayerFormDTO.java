package ru.jeb.oldwheelweb.model.dto.player;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.jeb.oldwheelweb.model.entity.Player;

/**
 * @author Jeb
 */
@Data
@NoArgsConstructor
public class AdminPlayerFormDTO {
    private String username;
    private String password;
    private Player.Role role;

    public AdminPlayerFormDTO(String username, String password, Player.Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
