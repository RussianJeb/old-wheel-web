package ru.jeb.oldwheelweb.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

/**
 * @author Jeb
 */
@Data
@Entity
@NoArgsConstructor
public class Properties {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public static Properties ADMIN = new Properties(5, 5, 5, 5);

    public Properties(int strength, int dexterity, int intelligence, int body) {
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.body = body;
    }

    private int strength = 0;
    private int dexterity = 0;
    private int intelligence = 0;
    private int body = 0;

    public Properties(Properties other) {
        this.strength = other.strength;
        this.body = other.body;
        this.intelligence = other.intelligence;
        this.dexterity = other.dexterity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Properties that = (Properties) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void update(Properties other) {
        this.strength = other.strength;
        this.body = other.body;
        this.intelligence = other.intelligence;
        this.dexterity = other.dexterity;
    }

    public boolean valid() {
        if (strength > 5 || dexterity > 5 || intelligence > 5 || body > 5) {
            return false;
        }
        return strength + dexterity + intelligence + body == 8;
    }
}
