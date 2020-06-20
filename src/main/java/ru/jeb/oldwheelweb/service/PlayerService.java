package ru.jeb.oldwheelweb.service;

import org.springframework.web.multipart.MultipartFile;
import ru.jeb.oldwheelweb.model.dto.player.AdminPlayerFormDTO;
import ru.jeb.oldwheelweb.model.dto.player.PlayerFormDTO;
import ru.jeb.oldwheelweb.model.dto.player.PlayerReviewDTO;
import ru.jeb.oldwheelweb.model.dto.player.PlayerUpdateDTO;
import ru.jeb.oldwheelweb.model.entity.Account;
import ru.jeb.oldwheelweb.model.entity.Player;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Jeb
 */
public interface PlayerService {
    Player registerPlayer(Account owner, PlayerFormDTO form, MultipartFile skin) throws IOException;

    Player reviewPlayer(Player player, PlayerReviewDTO review);

    void delete(Player player);

    void ban(Player player);

    Player registerAdminPlayer(Account admin, AdminPlayerFormDTO form, MultipartFile skin) throws IOException;

    void changePassword(Player player, String password);

    List<Player> findAll();

    Optional<Player> findByOwner(Account owner);

    List<Player> findAllWaiting();

    List<Player> findAllByStatus(Player.Status status);

    Player updateAccessToken(Player player, String accessToken);

    Player updateServerId(Player player, String serverId);

    boolean checkPassword(Player player, String password);


    Optional<Player> findByStatus(String username, Player.Status status);

    Optional<Player> findByNameOrUniqueId(String username, UUID uniqueId);

    Optional<Player> findByName(String name);

    Optional<Player> findByNameIgnoreCase(String name);

    Optional<Player> findByUniqueId(UUID uniqueId);

    Optional<Player> findByOwnerAndName(Account owner, String name);

    boolean canCreatePlayer(Account owner);

    Player updateInfo(PlayerUpdateDTO playerUpdate, Player player);


    Player activatePlayer(Player player);

    Player cancelPlayer(Player player, String reason);

    void waitPlayer(Player player);

    void deathPlayer(Player player);

    Player save(Player player);
}
