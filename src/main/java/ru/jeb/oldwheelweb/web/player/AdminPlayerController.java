package ru.jeb.oldwheelweb.web.player;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.jeb.oldwheelweb.model.dto.player.AdminPlayerFormDTO;
import ru.jeb.oldwheelweb.model.dto.player.PlayerCancelDTO;
import ru.jeb.oldwheelweb.model.dto.player.PlayerInfoDTO;
import ru.jeb.oldwheelweb.model.dto.player.PlayerReviewDTO;
import ru.jeb.oldwheelweb.model.entity.Account;
import ru.jeb.oldwheelweb.model.entity.Player;
import ru.jeb.oldwheelweb.service.AccountService;
import ru.jeb.oldwheelweb.service.PlayerService;
import ru.jeb.oldwheelweb.service.SkinService;

import java.io.IOException;
import java.util.Optional;

/**
 * @author Jeb
 */
@Slf4j
@RestController
@RequestMapping("/admin/player")
public class AdminPlayerController {
    private final PlayerService playerService;
    private final AccountService accountService;
    private final SkinService skinService;

    public AdminPlayerController(PlayerService playerService, AccountService accountService, SkinService skinService) {
        this.playerService = playerService;
        this.accountService = accountService;
        this.skinService = skinService;
    }

    @PostMapping("/ban/{name}")
    public ResponseEntity ban(@PathVariable("name") String name) {
        Optional<Player> optionalPlayer = playerService.findByName(name);
        optionalPlayer.ifPresent(playerService::ban);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/aban/{name}")
    public ResponseEntity aBan(@PathVariable("name") String name) {
        Optional<Player> optionalPlayer = playerService.findByName(name);
        optionalPlayer.ifPresent(p -> {
            playerService.ban(p);
            accountService.ban(p.getOwner());
        });
        return ResponseEntity.ok().build();
    }


    @PostMapping("/delete/{name}")
    public ResponseEntity delete(@PathVariable("name") String name) {
        Optional<Player> optionalPlayer = playerService.findByName(name);
        optionalPlayer.ifPresent(playerService::delete);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/review/{name}")
    public ResponseEntity review(@PathVariable("name") String name, @RequestBody PlayerReviewDTO review) {
        Optional<Player> optionalPlayer = playerService.findByName(name);
        optionalPlayer.ifPresent(player -> playerService.reviewPlayer(player, review));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/activate/{name}")
    public ResponseEntity activate(@PathVariable("name") String name) {
        Optional<Player> optionalPlayer = playerService.findByName(name);
        optionalPlayer.ifPresent(player -> playerService.activatePlayer(player));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cancel/{name}")
    public ResponseEntity cancel(@PathVariable("name") String name, @RequestBody PlayerCancelDTO cancelDTO) {
        Optional<Player> optionalPlayer = playerService.findByName(name);
        optionalPlayer.ifPresent(player -> playerService.cancelPlayer(player, cancelDTO.getReason()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity registerPlayer(@AuthenticationPrincipal Account owner,
                                         @RequestParam("player") AdminPlayerFormDTO adminForm,
                                         @RequestParam("skin") MultipartFile skin) throws IOException {
        if (skin.getSize() > 20000L || !skinService.validSkin(skin)) {
            throw new IllegalArgumentException("Некоректный формат скина");
        }
        if (adminForm == null) {
            throw new IllegalArgumentException("Неправильный формат пользователя");
        }
        if (!playerService.findByNameIgnoreCase(adminForm.getUsername()).isPresent()) {
            playerService.registerAdminPlayer(owner, adminForm, skin);
        } else {
            throw new IllegalArgumentException("Персонаж уже существует");
        }
        return ResponseEntity.ok().build();

    }

    @PostMapping("/waitings")
    public ResponseEntity waitingPlayers() {
        return ResponseEntity.ok(
                playerService.findAllByStatus(Player.Status.WAITING)
                        .stream()
                        .map(PlayerInfoDTO::new)
        );
    }

    @PostMapping("/deaths")
    public ResponseEntity deathPlayers() {
        return ResponseEntity.ok(
                playerService.findAllByStatus(Player.Status.DEATH)
                        .stream()
                        .map(PlayerInfoDTO::new)
        );
    }

    @PostMapping("/canceled")
    public ResponseEntity canceledPlayers() {
        return ResponseEntity.ok(
                playerService.findAllByStatus(Player.Status.CANCELED)
                        .stream()
                        .map(PlayerInfoDTO::new)
        );
    }

    @PostMapping("/activates")
    public ResponseEntity activatesPlayers() {
        return ResponseEntity.ok(
                playerService.findAllByStatus(Player.Status.ACTIVATE)
                        .stream()
                        .map(PlayerInfoDTO::new)
        );
    }

    @GetMapping("/info/{name}")
    public ResponseEntity<PlayerInfoDTO> playerInfo(@PathVariable("name") String name) {
        Optional<Player> optionalPlayer = playerService.findByName(name);
        return ResponseEntity.ok(optionalPlayer.map(PlayerInfoDTO::new)
                .orElseThrow(() -> new UsernameNotFoundException("Игрок не найден")));
    }
}
