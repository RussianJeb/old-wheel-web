package ru.jeb.oldwheelweb.data;

import org.springframework.data.repository.CrudRepository;
import ru.jeb.oldwheelweb.model.entity.Account;
import ru.jeb.oldwheelweb.model.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Jeb
 */
public interface PlayerRepository extends CrudRepository<Player, Long> {
    List<Player> findAll();

    Optional<Player> findByOwner(Account owner);

    List<Player> findAllByOwner(Account owner);

    List<Player> findAllByStatus(Player.Status status);

    Optional<Player> findByStatusAndName(Player.Status status, String name);

    Optional<Player> findByNameOrUniqueId(String name, UUID uniqueId);

    Optional<Player> findByName(String name);

    Optional<Player> findByNameIgnoreCase(String name);

    Optional<Player> findByUniqueId(UUID uniqueId);

    Optional<Player> findByOwnerAndName(Account owner, String name);

    int countByOwner(Account owner);
}
