package ru.jeb.oldwheelweb.web.event;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.jeb.oldwheelweb.model.dto.event.SessionsDTO;
import ru.jeb.oldwheelweb.service.EventService;

/**
 * @author Jeb
 */
@RestController
@RequestMapping("/admin/event")
public class AdminEventController {
    private final EventService eventService;

    public AdminEventController(EventService eventService) {
        this.eventService = eventService;
    }


    @PostMapping("/update")
    public ResponseEntity updateEvents(@RequestBody SessionsDTO date) {
        eventService.saveDates(date);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get")
    public ResponseEntity events() {
        return ResponseEntity.ok(eventService.getSession());
    }
}
