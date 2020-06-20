package ru.jeb.oldwheelweb.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.jeb.oldwheelweb.config.ApplicationConfig;
import ru.jeb.oldwheelweb.data.PlayerRepository;
import ru.jeb.oldwheelweb.model.dto.player.AdminPlayerFormDTO;
import ru.jeb.oldwheelweb.model.dto.player.PlayerFormDTO;
import ru.jeb.oldwheelweb.model.dto.player.PlayerReviewDTO;
import ru.jeb.oldwheelweb.model.dto.player.PlayerUpdateDTO;
import ru.jeb.oldwheelweb.model.entity.Account;
import ru.jeb.oldwheelweb.model.entity.Player;
import ru.jeb.oldwheelweb.model.entity.PlayerInfo;
import ru.jeb.oldwheelweb.model.entity.Skin;
import ru.jeb.oldwheelweb.service.PlayerService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Jeb
 */
@Service
@Slf4j
public class DefaultPlayerService implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationConfig applicationConfig;

    public DefaultPlayerService(PlayerRepository playerRepository, PasswordEncoder passwordEncoder, ApplicationConfig applicationConfig) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
        this.applicationConfig = applicationConfig;
    }


    @Override
    public Player registerPlayer(Account owner, PlayerFormDTO form, MultipartFile skinFile) throws IOException {
        Skin skin = new Skin(form.getName(), skinFile.getBytes());
        PlayerInfo playerInfo = new PlayerInfo(form.getDescription(), form.getExterior(), form.getCharacter());
        form.getStats().setMana(form.getStats().calculateMana());
        Player player = new Player(owner, form.getName(), passwordEncoder.encode(form.getPassword()), playerInfo, skin, form.getStats());
        return save(player);
    }

    @Override
    public Player reviewPlayer(Player player, PlayerReviewDTO review) {
        player.setStatus(review.getStatus());
        if (review.getStatus().equals(Player.Status.CANCELED)) {
            player.setCancelMessage(review.getCancelMessage());
        }
        return save(player);
    }

    @Override
    public void delete(Player player) {
        playerRepository.delete(player);
    }

    @Override
    public void ban(Player player) {
        player.setStatus(Player.Status.BANNED);
        save(player);
    }

    @Override
    public Player registerAdminPlayer(Account admin, AdminPlayerFormDTO form, MultipartFile skinFile) throws IOException {
        byte[] data = skinFile == null ? null : skinFile.getBytes();
        Skin skin = new Skin(form.getUsername(), data);
        Player player = new Player(admin, form.getUsername(), passwordEncoder.encode(form.getPassword()), form.getRole(), skin);
        return save(player);
    }

    @Override
    public void changePassword(Player player, String password) {
        player.setPassword(passwordEncoder.encode(password));
        save(player);
    }

    @Override
    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    @Override
    public Optional<Player> findByOwner(Account owner) {
        return playerRepository.findByOwner(owner);
    }

    @Override
    public List<Player> findAllWaiting() {
        return playerRepository.findAllByStatus(Player.Status.WAITING);
    }

    @Override
    public List<Player> findAllByStatus(Player.Status status) {
        return playerRepository.findAllByStatus(status);
    }


    @Override
    public Player updateAccessToken(Player player, String accessToken) {
        player.setAccessToken(accessToken);
        return save(player);
    }

    @Override
    public Player updateServerId(Player player, String serverId) {
        player.setServerID(serverId);
        return save(player);

    }

    @Override
    public boolean checkPassword(Player player, String password) {
        return passwordEncoder.matches(password, player.getPassword());
    }

    @Override
    public Optional<Player> findByStatus(String username, Player.Status status) {
        return playerRepository.findByStatusAndName(status, username);
    }

    @Override
    public Optional<Player> findByNameOrUniqueId(String username, UUID uniqueId) {
        return playerRepository.findByNameOrUniqueId(username, uniqueId);
    }

    @Override
    public Optional<Player> findByName(String name) {
        return playerRepository.findByName(name);
    }

    @Override
    public Optional<Player> findByNameIgnoreCase(String name) {
        return playerRepository.findByNameIgnoreCase(name);
    }

    @Override
    public Optional<Player> findByUniqueId(UUID uniqueId) {
        return playerRepository.findByUniqueId(uniqueId);
    }

    @Override
    public Optional<Player> findByOwnerAndName(Account owner, String name) {
        return playerRepository.findByOwnerAndName(owner, name);
    }

    @Override
    public boolean canCreatePlayer(Account owner) {
        List<Player> players = playerRepository.findAllByOwner(owner);
        return players.stream()
                .filter(player -> !player.getStatus().equals(Player.Status.DEATH))
                .count() < applicationConfig.getPlayerCount();
    }

    @Override
    public Player updateInfo(PlayerUpdateDTO playerUpdate, Player player) {
        if (!player.getStatus().equals(Player.Status.CANCELED))
            throw new IllegalArgumentException("Персонажу еще не отказано.");
        PlayerInfo playerInfo = player.getPlayerInfo();
        if (playerInfo == null) {
            playerInfo = new PlayerInfo();
        }
        playerInfo.setDescription(playerUpdate.getDescription());
        player.setStatus(Player.Status.WAITING);
        playerInfo.setExterior(playerUpdate.getExterior());
        playerInfo.setCharacter(playerUpdate.getCharacter());
        player.setPlayerInfo(playerInfo);
        return save(player);
    }

    @Override
    public Player activatePlayer(Player player) {
        player.setStatus(Player.Status.ACTIVATE);
        return save(player);
    }

    @Override
    public Player cancelPlayer(Player player, String reason) {
        player.setStatus(Player.Status.CANCELED);
        player.setCancelMessage(reason);
        return save(player);
    }

    @Override
    public void waitPlayer(Player player) {
        player.setStatus(Player.Status.WAITING);
        save(player);
    }

    @Override
    public void deathPlayer(Player player) {
        player.setStatus(Player.Status.DEATH);
        save(player);
    }

    @Override
    public Player save(Player player) {
        return playerRepository.save(player);
    }


}
