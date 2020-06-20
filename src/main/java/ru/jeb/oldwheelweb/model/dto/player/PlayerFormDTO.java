package ru.jeb.oldwheelweb.model.dto.player;

import lombok.Data;
import ru.jeb.oldwheelweb.model.entity.PlayerStats;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Jeb
 */
@Data
public class PlayerFormDTO {
    @Size(max = 16, min = 3, message = "Имя преувелчиено или преуменьшено")
    private String name;
    private String password;
    @NotBlank(message = "Пустое описание")
    private String description;
    @NotBlank(message = "Пустая внешность")
    private String exterior;
    @NotBlank(message = "Пустой характер")
    private String character;
    private PlayerStats stats;
}
