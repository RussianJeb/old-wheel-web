package ru.jeb.oldwheelweb.model.dto.player;

import lombok.Data;
import ru.jeb.oldwheelweb.model.entity.Player;

/**
 * @author Jeb
 */
@Data
public class PlayerReviewDTO {
    private Player.Status status;
    private String cancelMessage;
}
