package ru.jeb.oldwheelweb.web.launcher;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.jeb.oldwheelweb.model.dto.launcher.EntryDTO;
import ru.jeb.oldwheelweb.model.dto.launcher.SuccessRequestDTO;
import ru.jeb.oldwheelweb.model.dto.launcher.UpdateAuthDTO;
import ru.jeb.oldwheelweb.model.dto.launcher.UpdateServerIdDTO;
import ru.jeb.oldwheelweb.model.entity.Player;
import ru.jeb.oldwheelweb.service.PlayerService;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Jeb
 */
@RestController
@RequestMapping("/launcher/auth/handler")

public class AuthHandlerController {
    private final PlayerService playerService;

    public AuthHandlerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/get")
    public ResponseEntity<EntryDTO> get(@RequestBody Map<String, String> entryRequest) {
        Optional<Player> optionalPlayer = Optional.empty();
        if (entryRequest.containsKey("username")) {
            optionalPlayer = playerService.findByName(entryRequest.get("username"));
        } else if (entryRequest.containsKey("uuid")) {
            optionalPlayer = playerService.findByUniqueId(UUID.fromString(entryRequest.get("uuid")));
        }
        return ResponseEntity.ok(optionalPlayer.map(EntryDTO::new).orElseThrow(() -> new UsernameNotFoundException("Player not found")));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/update/auth")
    public ResponseEntity<SuccessRequestDTO> updateAuth(@RequestBody UpdateAuthDTO updateAuthDTO) {
        Optional<Player> playerOptional = playerService.findByNameOrUniqueId(updateAuthDTO.getUsername(), updateAuthDTO.getUuid());
        if (playerOptional.isPresent()) {
            playerService.updateAccessToken(playerOptional.get(), updateAuthDTO.getAccessToken());
            return ResponseEntity.ok(new SuccessRequestDTO(true));
        }

        return ResponseEntity.ok(new SuccessRequestDTO(false));
    }

    @PostMapping("/update/server")
    public ResponseEntity<SuccessRequestDTO> updateServerId(@RequestBody UpdateServerIdDTO updateServerDTO) {
        Optional<Player> playerOptional = playerService.findByUniqueId(updateServerDTO.getUuid());
        if (playerOptional.isPresent()) {
            playerService.updateServerId(playerOptional.get(), updateServerDTO.getServerId());
            return ResponseEntity.ok(new SuccessRequestDTO(true));
        }
        return ResponseEntity.ok(new SuccessRequestDTO(false));
    }


}
