package ru.jeb.oldwheelweb.model.dto.launcher;

import lombok.Data;

import java.util.UUID;

/**
 * @author Jeb
 */
@Data
public class UpdateServerIdDTO {
    private UUID uuid;
    private String serverId;
}
