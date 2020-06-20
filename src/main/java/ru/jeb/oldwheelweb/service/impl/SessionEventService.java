package ru.jeb.oldwheelweb.service.impl;

import org.springframework.stereotype.Service;
import ru.jeb.oldwheelweb.data.EventRepository;
import ru.jeb.oldwheelweb.model.dto.event.CalendarDTO;
import ru.jeb.oldwheelweb.model.dto.event.EventDTO;
import ru.jeb.oldwheelweb.model.dto.event.SessionsDTO;
import ru.jeb.oldwheelweb.model.entity.Event;
import ru.jeb.oldwheelweb.service.EventService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jeb
 */
@Service
public class SessionEventService implements EventService {
    private final EventRepository eventRepository;

    public SessionEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public CalendarDTO getCalendar() {
        List<Event> events = eventRepository.findAll();
        List<EventDTO> eventDTOS = events.stream().map(EventDTO::new).collect(Collectors.toList());
        LocalDate startDate = eventDTOS.stream().map(EventDTO::getStart).min(LocalDate::compareTo).orElse(LocalDate.now());
        LocalDate endDate = eventDTOS.stream().map(EventDTO::getStart).max(LocalDate::compareTo).orElse(LocalDate.now().plusDays(10));
        return new CalendarDTO(startDate, endDate, eventDTOS);
    }


    @Override
    public void saveDates(SessionsDTO sessions) {
        List<Event> sessionList = sessions.getDates().stream()
                .map(date -> new Event("Сессия", date))
                .collect(Collectors.toList());
        eventRepository.deleteAll();
        eventRepository.saveAll(sessionList);
    }

    @Override
    public SessionsDTO getSession() {
        return new SessionsDTO(eventRepository.findAll().stream().map(Event::getDate).collect(Collectors.toList()));
    }
}
