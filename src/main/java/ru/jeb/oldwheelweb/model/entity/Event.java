package ru.jeb.oldwheelweb.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * @author Jeb
 */
@Data
@Entity
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private LocalDate date;

    public Event(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }
}