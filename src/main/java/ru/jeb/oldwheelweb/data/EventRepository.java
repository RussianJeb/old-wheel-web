package ru.jeb.oldwheelweb.data;

import org.springframework.data.repository.CrudRepository;
import ru.jeb.oldwheelweb.model.entity.Event;

import java.util.List;

/**
 * @author Jeb
 */
public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> findAll();
}
