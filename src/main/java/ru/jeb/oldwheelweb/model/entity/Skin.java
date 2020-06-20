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
public class Skin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String name;
    @Lob
    private byte[] content;

    public Skin(String name, byte[] skin) {
        this.name = name;
        this.content = skin;
    }
}
