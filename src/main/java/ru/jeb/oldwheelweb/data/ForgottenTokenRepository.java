package ru.jeb.oldwheelweb.data;

import org.springframework.data.repository.CrudRepository;
import ru.jeb.oldwheelweb.model.entity.ForgottenToken;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Jeb
 */
public interface ForgottenTokenRepository extends CrudRepository<ForgottenToken, Long> {
    Optional<ForgottenToken> findByUniqueIdAndAccount_Username(UUID uuid, String username);
}
