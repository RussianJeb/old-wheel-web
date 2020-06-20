package ru.jeb.oldwheelweb.model.dto.player;

import lombok.Data;
import ru.jeb.oldwheelweb.model.entity.Player;
import ru.jeb.oldwheelweb.model.entity.PlayerStats;

/**
 * @author Jeb
 */
@Data
public class PlayerInfoDTO {
    private String name;
    private Player.Status status;
    private PlayerDescriptionDTO playerInfo;
    private String cancelMessage;
    private PlayerStats stats;
    private String contact;

    public PlayerInfoDTO(Player player) {
        this.name = player.getName();
        this.contact = player.getOwner().getContact();
        this.status = player.getStatus();
        this.stats = player.getPlayerStats();
        this.playerInfo = new PlayerDescriptionDTO(player.getPlayerInfo());
        this.cancelMessage = player.getCancelMessage();
    }
}
