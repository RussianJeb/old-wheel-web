package ru.jeb.oldwheelweb.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Jeb
 */
@Data
@Entity
@NoArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String name;
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID uniqueId;
    private String password;

    public Player(Account owner, String name, String password, PlayerInfo playerInfo, Skin skin, PlayerStats stats) {
        this.name = name;
        this.playerStats = stats;
        this.owner = owner;
        this.password = password;
        this.playerInfo = playerInfo;
        this.skin = skin;
    }

    @OneToOne(cascade = CascadeType.ALL)
    private PlayerInfo playerInfo;
    @OneToOne(cascade = CascadeType.ALL)
    private PlayerStats playerStats;
    @Enumerated(EnumType.STRING)
    private Status status = Status.WAITING;
    @Enumerated(EnumType.STRING)
    private Role role = Role.PLAYER;
    private String accessToken;
    private String serverID;
    private Date createdAt;

    public Player(Account owner, String name, String password, Role role, Skin skin) {
        this.name = name;
        this.owner = owner;
        this.password = password;
        this.role = role;
        this.playerInfo = new PlayerInfo();
        this.skin = skin;
        this.status = Status.ACTIVATE;
        this.playerStats = new PlayerStats(PlayerStats.ADMIN);
    }

    private String cancelMessage;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private Skin skin;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account owner;

    public enum Status {
        ACTIVATE,
        CANCELED,
        WAITING,
        DEATH,
        BANNED
    }

    public enum Role {
        PLAYER(0),
        ADMIN(1),
        SERVER(2);

        private final int permission;

        Role(int permission) {
            this.permission = permission;
        }

        public int getPermission() {
            return permission;
        }
    }

    @PrePersist
    public void prePersist() {
        this.uniqueId = UUID.randomUUID();
        this.createdAt = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player account = (Player) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
