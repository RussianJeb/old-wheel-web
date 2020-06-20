package ru.jeb.oldwheelweb.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Jeb
 */
@Data
@Entity
@NoArgsConstructor
public class PlayerInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String description = "Отсутствует";
    @Column(columnDefinition = "TEXT")
    private String character = "Отсутствует";


    public PlayerInfo(String description, String exterior, String character) {
        this.description = description;
        this.character = character;
        this.exterior = exterior;
    }

    @Column(columnDefinition = "TEXT")
    private String exterior = "Отсутствует";
}
