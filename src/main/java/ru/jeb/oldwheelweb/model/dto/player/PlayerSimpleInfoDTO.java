package ru.jeb.oldwheelweb.model.dto.player;

import lombok.Data;
import ru.jeb.oldwheelweb.model.entity.Player;

/**
 * @author Jeb
 */
@Data
public class PlayerSimpleInfoDTO {
    private String name;
    private Player.Status status;

    public PlayerSimpleInfoDTO(Player player) {
        this.name = player.getName();
        this.status = player.getStatus();
    }
}
