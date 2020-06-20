package ru.jeb.oldwheelweb.web.server;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.jeb.oldwheelweb.model.dto.player.PlayerDescriptionDTO;
import ru.jeb.oldwheelweb.model.entity.Player;
import ru.jeb.oldwheelweb.model.entity.PlayerStats;
import ru.jeb.oldwheelweb.service.PlayerService;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Jeb
 */
@RestController
@RequestMapping("/server")
public class ServerController {
    private final PlayerService playerService;

    public ServerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/death/{name}")
    public ResponseEntity deathPlayer(@PathVariable("name") String name) {
        Optional<Player> playerOptional = playerService.findByNameIgnoreCase(name);
        if (!playerOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        playerService.deathPlayer(playerOptional.get());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/info/{id}")
    public ResponseEntity infoPlayer(@PathVariable("id") UUID unqiueId) {
        Optional<Player> playerOptional = playerService.findByUniqueId(unqiueId);
        return playerOptional
                .map(player -> ResponseEntity.ok(player.getPlayerStats()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/update/stats/{id}")
    public ResponseEntity updatePlayer(@PathVariable("id") UUID unqiueId, @RequestBody PlayerStats playerStats) {
        Optional<Player> playerOptional = playerService.findByUniqueId(unqiueId);
        playerOptional.ifPresent(player -> {
            player.getPlayerStats().update(playerStats);
            playerService.save(player);
        });
        return playerOptional
                .map(player -> ResponseEntity.ok(player.getPlayerStats()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/infon/{id}")
    public ResponseEntity infoPlayerFromName(@PathVariable("id") String unqiueId) {
        Optional<Player> playerOptional = playerService.findByNameIgnoreCase(unqiueId);
        return playerOptional
                .map(player -> ResponseEntity.ok(player.getPlayerStats()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/updaten/stats/{id}")
    public ResponseEntity updatePlayerFromName(@PathVariable("id") String unqiueId, @RequestBody PlayerStats playerStats) {
        Optional<Player> playerOptional = playerService.findByNameIgnoreCase(unqiueId);
        playerOptional.ifPresent(player -> {
            player.getPlayerStats().update(playerStats);
            playerService.save(player);
        });
        return playerOptional
                .map(player -> ResponseEntity.ok(player.getPlayerStats()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/updaten/mana/{name}")
    public ResponseEntity updateMana(@PathVariable("name") String name, @RequestBody Map<String, Integer> mana) {
        Optional<Player> playerOptional = playerService.findByNameIgnoreCase(name);
        playerOptional.map(Player::getPlayerStats).ifPresent(playerStats -> {
            playerStats.setMana(Math.min(mana.getOrDefault("mana", 0), playerStats.calculateMana()));
            playerService.save(playerOptional.get());
        });
        return playerOptional
                .map(player -> ResponseEntity.ok(player.getPlayerStats()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/pinfon/{id}")
    public ResponseEntity playerInfo(@PathVariable("id") String unqiueId) {
        Optional<Player> playerOptional = playerService.findByNameIgnoreCase(unqiueId);
        return playerOptional
                .map(player -> ResponseEntity.ok(new PlayerDescriptionDTO(player.getPlayerInfo())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
