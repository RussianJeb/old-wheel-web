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
public class ForgottenToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID uniqueId;
    @OneToOne
    private Account account;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForgottenToken that = (ForgottenToken) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public ForgottenToken(Account account) {
        this.account = account;
        this.uniqueId = UUID.randomUUID();
        this.expireTime = LocalDateTime.now().plusDays(1);
    }

    private LocalDateTime expireTime;
}
