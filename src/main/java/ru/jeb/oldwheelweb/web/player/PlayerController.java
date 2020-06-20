package ru.jeb.oldwheelweb.web.player;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.jeb.oldwheelweb.exception.UsernameAlreadyExistException;
import ru.jeb.oldwheelweb.model.dto.Error;
import ru.jeb.oldwheelweb.model.dto.player.PlayerFormDTO;
import ru.jeb.oldwheelweb.model.dto.player.PlayerInfoDTO;
import ru.jeb.oldwheelweb.model.dto.player.PlayerUpdateDTO;
import ru.jeb.oldwheelweb.model.entity.Account;
import ru.jeb.oldwheelweb.model.entity.Player;
import ru.jeb.oldwheelweb.model.entity.PlayerStats;
import ru.jeb.oldwheelweb.service.PlayerService;
import ru.jeb.oldwheelweb.service.SkinService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author Jeb
 */
@Slf4j
@RestController
@RequestMapping("/player")
public class PlayerController {
    private final PlayerService playerService;
    private final SkinService skinService;

    public PlayerController(PlayerService playerService, SkinService skinService) {
        this.playerService = playerService;
        this.skinService = skinService;
    }

    @PostMapping("/register")
    public ResponseEntity registerPlayer(@AuthenticationPrincipal Account owner,
                                         @Valid @RequestParam("player") PlayerFormDTO form,
                                         @RequestParam("skin") MultipartFile skin) throws IOException {
        if (skin.getSize() > 20000L || !skinService.validSkin(skin)) {
            throw new IllegalArgumentException("Некоректный формат скина");
        }
        if (playerService.canCreatePlayer(owner) && form != null) {
            if (!playerService.findByNameIgnoreCase(form.getName()).isPresent()) {
                if (form.getStats().valid()) {
                    playerService.registerPlayer(owner, form, skin);
                }
            } else {
                throw new UsernameAlreadyExistException("Персонаж уже существует");
            }
        } else {
            throw new IndexOutOfBoundsException("Вы уже создали максимальное количество персонажей");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/traits")
    public ResponseEntity<List<String>> generateTraits(@RequestBody PlayerStats stats) {
        return ResponseEntity.ok(stats.generateTrait());
    }

    @GetMapping("/info/{name}")
    public ResponseEntity<PlayerInfoDTO> playerInfo(@AuthenticationPrincipal Account owner, @PathVariable("name") String name) {
        Optional<Player> optionalPlayer = playerService.findByOwnerAndName(owner, name);
        return ResponseEntity.ok(optionalPlayer.map(PlayerInfoDTO::new)
                .orElseThrow(() -> new UsernameNotFoundException("Игрок не найден")));
    }


    @PostMapping("/change/password/{name}")
    public ResponseEntity changePassword(@AuthenticationPrincipal Account owner,
                                         @PathVariable("name") String name,
                                         @RequestParam("password") String password) {
        Optional<Player> optionalPlayer = playerService.findByOwnerAndName(owner, name);
        optionalPlayer.ifPresent(player -> playerService.changePassword(player, password));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/change/skin/{name}")
    public ResponseEntity changeSkin(@AuthenticationPrincipal Account owner,
                                     @PathVariable("name") String name,
                                     @RequestParam("skin") MultipartFile skin) throws IOException {
        if (skin.getSize() > 20000L || !skinService.validSkin(skin)) {
            throw new IllegalArgumentException("Некоректный формат скина");
        }
        Optional<Player> optionalPlayer = playerService.findByOwnerAndName(owner, name);
        if (optionalPlayer.isPresent()) {
            if (optionalPlayer.get().getStatus().equals(Player.Status.CANCELED)) {
                playerService.waitPlayer(optionalPlayer.get());
            }
            skinService.changeSkin(optionalPlayer.get(), skin);
        }
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception ex) {
        return ResponseEntity.badRequest().body(new Error(ex.getMessage()));
    }

    @PostMapping("/recreate/{name}")
    public ResponseEntity recreatePlayer(@AuthenticationPrincipal Account owner,
                                         @RequestBody PlayerUpdateDTO playerUpdate,
                                         @PathVariable("name") String name) {
        Optional<Player> playerOptional = playerService.findByOwnerAndName(owner, name);
        playerOptional.ifPresent(player -> {
            if (player.getStatus().equals(Player.Status.CANCELED)) {
                playerService.updateInfo(playerUpdate, player);
            }
        });
        return ResponseEntity.ok().build();
    }



   /* @PostMapping("/recreate/skin/{name}")
    public ResponseEntity recreateSkin(@AuthenticationPrincipal Account owner,
                                       @RequestParam("skin") MultipartFile file,
                                       @PathVariable("name") String name) throws IOException {
        if (file.getSize() > 15000L) {
            throw new IllegalArgumentException("Слишком большой скин");
        }
        Optional<Player> playerOptional = playerService.findByOwnerAndName(owner, name);
        if (playerOptional.isPresent()) {
            skinService.changeSkin(playerOptional.get(), file);
            PlayerInfo playerInfo = playerOptional.get().getPlayerInfo();
            playerService.updateInfo(new PlayerUpdateDTO(playerInfo.getDescription(), playerInfo.getDescription()), playerOptional.get());
        }
        return ResponseEntity.ok().build();
    }*/

    @GetMapping("/registrable")
    public ResponseEntity canRegister(@AuthenticationPrincipal Account account) {
        if (account.getRole().equals(Account.Role.ADMIN)) {
            return ResponseEntity.ok().build();
        }
        if (playerService.canCreatePlayer(account)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
