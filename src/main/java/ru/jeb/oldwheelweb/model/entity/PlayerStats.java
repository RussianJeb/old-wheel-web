package ru.jeb.oldwheelweb.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * @author Jeb
 */
@Data
@Entity
@NoArgsConstructor
public class PlayerStats {

    public static PlayerStats ADMIN = new PlayerStats(Properties.ADMIN, Skills.ADMIN, "ALL", Language.LIR);

    public PlayerStats(Properties properties, Skills skills, String trait, Language language) {
        this.properties = properties;
        this.skills = skills;
        this.trait = trait;
        this.language = language;
    }

    public PlayerStats(PlayerStats playerStats) {
        this.properties = new Properties(playerStats.getProperties());
        this.skills = new Skills(playerStats.getSkills());
        this.mana = playerStats.mana;
        this.trait = playerStats.trait;
        this.language = playerStats.language;
    }


    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Properties properties;


    @OneToOne(cascade = CascadeType.ALL)
    private Skills skills;

    @Enumerated(EnumType.STRING)
    private Language language = Language.LIR;

    private String trait = "NONE";

    private int mana = 9;

    public int calculateMana() {
        switch (skills.getWizard()) {
            case 1: {
                return 12;
            }
            case 2: {
                return 15;
            }
            case 3: {
                return 20;
            }
        }
        return 0;
    }

    public boolean hasTrait(String trait) {
        if (this.trait.equalsIgnoreCase("none")) {
            return false;
        } else if (this.trait.equalsIgnoreCase("all")) {
            return true;
        } else {
            return this.trait.equalsIgnoreCase(trait);
        }
    }


    public enum Language {
        FEN, LIR
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerStats that = (PlayerStats) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void update(PlayerStats other) {
        this.language = other.language;
        this.trait = other.trait;
        this.skills.update(other.skills);
        this.properties.update(other.properties);
        this.mana = other.mana;
    }

    public boolean valid() {
        return skills.valid() && properties.valid() && (generateTrait().contains(trait) || trait.equalsIgnoreCase("none"));
    }

    public List<String> generateTrait() {
        ImmutableList.Builder<String> traits = ImmutableList.builder();
        if (properties.getIntelligence() >= 5) {
            traits.add("POLYGLOT");
        }
        return traits.build();
    }


}
