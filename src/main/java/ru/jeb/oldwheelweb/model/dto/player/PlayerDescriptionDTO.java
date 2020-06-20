package ru.jeb.oldwheelweb.model.dto.player;

import lombok.Data;
import ru.jeb.oldwheelweb.model.entity.PlayerInfo;

import javax.annotation.Nullable;

/**
 * @author Jeb
 */
@Data
public class PlayerDescriptionDTO {
    private String exterior;
    private String description;
    private String character;

    public PlayerDescriptionDTO(@Nullable PlayerInfo playerInfo) {
        this.exterior = playerInfo != null ? playerInfo.getExterior() : null;
        this.description = playerInfo != null ? playerInfo.getDescription() : null;
        this.character = playerInfo != null ? playerInfo.getCharacter() : null;
    }
}
