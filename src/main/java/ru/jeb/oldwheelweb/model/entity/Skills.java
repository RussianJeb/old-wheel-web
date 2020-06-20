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
public class Skills {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public static Skills ADMIN = new Skills(3, 3, 3, 3, 3, 3, 3, 3, 3);

    public Skills(int artisan, int getter, int engineer, int farmer, int alchemist, int wizard, int blacksmith, int melee, int distant) {
        this.alchemist = alchemist;
        this.artisan = artisan;
        this.getter = getter;
        this.farmer = farmer;
        this.wizard = wizard;
        this.blacksmith = blacksmith;
        this.melee = melee;
        this.distant = distant;
        this.engineer = engineer;
    }

    private int artisan = 0;

    private int getter = 0;

    private int engineer = 0;

    private int farmer = 0;

    private int alchemist = 0;

    private int wizard = 0;

    private int blacksmith = 0;

    private int melee = 0;

    private int distant = 0;

    public Skills(Skills other) {
        this.getter = other.getter;
        this.alchemist = other.alchemist;
        this.artisan = other.artisan;
        this.melee = other.melee;
        this.farmer = other.farmer;
        this.blacksmith = other.blacksmith;
        this.wizard = other.wizard;
        this.distant = other.distant;
        this.engineer = other.engineer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skills skills = (Skills) o;
        return Objects.equals(id, skills.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void update(Skills other) {
        this.getter = other.getter;
        this.alchemist = other.alchemist;
        this.artisan = other.artisan;
        this.melee = other.melee;
        this.farmer = other.farmer;
        this.blacksmith = other.blacksmith;
        this.wizard = other.wizard;
        this.distant = other.distant;
        this.engineer = other.engineer;
    }

    public boolean valid() {
        if (melee > 3 || distant > 3 || getter > 3 || alchemist > 3 || blacksmith > 3 || wizard > 3 || engineer > 3 || artisan > 3 || farmer > 3) {
            return false;
        }
        return melee + distant + getter + alchemist + blacksmith + wizard + engineer + artisan + farmer == 4;
    }
}
