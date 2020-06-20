package ru.jeb.oldwheelweb.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.jeb.oldwheelweb.model.entity.Account;

import java.util.List;
import java.util.Optional;

/**
 * @author Jeb
 */
public interface AccountRepository extends CrudRepository<Account, Long> {
    Optional<Account> findByUsername(String username);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByUsernameOrEmail(String username, String email);

    List<Account> findAll();

    List<Account> findAll(Pageable pageable);
}
