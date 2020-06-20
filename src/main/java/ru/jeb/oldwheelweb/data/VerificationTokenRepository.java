package ru.jeb.oldwheelweb.data;

import org.springframework.data.repository.CrudRepository;
import ru.jeb.oldwheelweb.model.entity.Account;
import ru.jeb.oldwheelweb.model.entity.VerificationToken;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Jeb
 */
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByUniqueIdAndAccount(UUID token, Account account);

    Optional<VerificationToken> findByUniqueId(UUID uniqueId);

    List<VerificationToken> findAll();
}
