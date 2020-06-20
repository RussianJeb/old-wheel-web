package ru.jeb.oldwheelweb.service;

import ru.jeb.oldwheelweb.model.dto.event.CalendarDTO;
import ru.jeb.oldwheelweb.model.dto.event.SessionsDTO;

/**
 * @author Jeb
 */
public interface EventService {

    CalendarDTO getCalendar();


    void saveDates(SessionsDTO sessions);

    SessionsDTO getSession();

}
