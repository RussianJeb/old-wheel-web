package ru.jeb.oldwheelweb.model.dto.launcher;

import lombok.Data;
import ru.jeb.oldwheelweb.model.entity.Player;

import java.util.UUID;

/**
 * @author Jeb
 */
@Data
public class EntryDTO {
    public EntryDTO(Player player) {
        this.uuid = player.getUniqueId();
        this.username = player.getName();
        this.accessToken = player.getAccessToken();
        this.serverID = player.getServerID();
    }

    private UUID uuid;
    private String username;
    private String accessToken;
    private String serverID;
}
