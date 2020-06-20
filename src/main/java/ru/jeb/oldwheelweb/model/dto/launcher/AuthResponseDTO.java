package ru.jeb.oldwheelweb.model.dto.launcher;

import lombok.Data;
import ru.jeb.oldwheelweb.model.entity.Player;

/**
 * @author Jeb
 */
@Data
public class AuthResponseDTO {
    private String username;
    private int permissions;

    public AuthResponseDTO(Player player) {
        this.username = player.getName();
        this.permissions = player.getRole().getPermission();
    }
}
