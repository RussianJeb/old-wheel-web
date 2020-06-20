package ru.jeb.oldwheelweb.model.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.jeb.oldwheelweb.model.entity.Event;

import java.time.LocalDate;

/**
 * @author Jeb
 */
@Data
public class EventDTO {

    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate start;

    public EventDTO(String name, LocalDate date) {
        this.name = name;
        this.start = date;
    }

    public EventDTO(Event event) {
        this.name = event.getName();
        this.start = event.getDate();
    }
}
