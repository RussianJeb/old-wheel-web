package ru.jeb.oldwheelweb.web.launcher;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import ru.jeb.oldwheelweb.config.LauncherConfig;
import ru.jeb.oldwheelweb.exception.BadApiKeyException;
import ru.jeb.oldwheelweb.model.dto.launcher.AuthErrorDTO;
import ru.jeb.oldwheelweb.model.dto.launcher.AuthRequestDTO;
import ru.jeb.oldwheelweb.model.dto.launcher.AuthResponseDTO;
import ru.jeb.oldwheelweb.model.entity.Player;
import ru.jeb.oldwheelweb.service.PlayerService;

import java.util.Optional;

/**
 * @author Jeb
 */
@RestController
@RequestMapping("/launcher/auth/provider")
public class AuthProviderController {
    private final PlayerService playerService;
    private final LauncherConfig launcherConfig;

    public AuthProviderController(PlayerService playerService,
                                  LauncherConfig launcherConfig) {
        this.playerService = playerService;
        this.launcherConfig = launcherConfig;
    }

    @PostMapping
    public ResponseEntity<AuthResponseDTO> auth(@RequestBody AuthRequestDTO request) throws BadApiKeyException {
        if (!launcherConfig.getApiKey().equals(request.getApiKey())) {
            throw new BadApiKeyException();
        }
        Optional<Player> player = playerService.findByStatus(request.getUsername(), Player.Status.ACTIVATE);
        AuthResponseDTO responseDTO = player.filter(p -> playerService.checkPassword(p, request.getPassword()))
                .map(AuthResponseDTO::new)
                .orElseThrow(() -> new BadCredentialsException("Неправильный логин или пароль"));
        if (player.get().getRole().equals(Player.Role.ADMIN)) {
            return ResponseEntity.ok(responseDTO);
        } else {
            throw new RuntimeException("Недостаточно прав");
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AuthErrorDTO> handleUserNotFound(Exception ex) {
        return ResponseEntity.ok(new AuthErrorDTO(ex.getMessage()));
    }
}
