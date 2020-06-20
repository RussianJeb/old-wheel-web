package ru.jeb.oldwheelweb.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Jeb
 */
@Data
@Entity
@NoArgsConstructor
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID uniqueId;
    @OneToOne
    private Account account;

    private LocalDateTime expireTime;

    public VerificationToken(Account account) {
        this.account = account;
        this.expireTime = LocalDateTime.now().plusDays(1);
        this.uniqueId = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerificationToken that = (VerificationToken) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
