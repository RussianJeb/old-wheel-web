package ru.jeb.oldwheelweb.web.event;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jeb.oldwheelweb.model.dto.event.CalendarDTO;
import ru.jeb.oldwheelweb.service.EventService;

/**
 * @author Jeb
 */
@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/get")
    public ResponseEntity<CalendarDTO> sessionCalendar() {
        return ResponseEntity.ok(eventService.getCalendar());
    }
}
