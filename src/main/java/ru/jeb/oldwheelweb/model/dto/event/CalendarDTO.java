package ru.jeb.oldwheelweb.model.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Jeb
 */
@Data
public class CalendarDTO {
    public CalendarDTO(LocalDate start, LocalDate end, List<EventDTO> events) {
        this.start = start;
        this.end = end;
        this.events = events;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate start;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate end;
    private List<EventDTO> events;
}
