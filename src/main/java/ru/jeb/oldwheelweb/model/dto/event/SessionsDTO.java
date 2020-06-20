package ru.jeb.oldwheelweb.model.dto.event;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Jeb
 */
@Data
public class SessionsDTO {
    private List<LocalDate> dates;

    public SessionsDTO() {
    }

    public SessionsDTO(List<LocalDate> dates) {
        this.dates = dates;
    }
}
