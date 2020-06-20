package ru.jeb.oldwheelweb.web.launcher;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jeb.oldwheelweb.model.entity.Skin;
import ru.jeb.oldwheelweb.service.PlayerService;
import ru.jeb.oldwheelweb.service.SkinService;
import ru.jeb.oldwheelweb.utils.MinecraftSkinUtil;

import java.util.Optional;

/**
 * @author Jeb
 */
@RestController
@RequestMapping(value = "/launcher/texture", produces = MediaType.IMAGE_PNG_VALUE)
public class TextureProviderController {
    private final PlayerService playerService;
    private final SkinService skinService;

    public TextureProviderController(SkinService skinService, PlayerService playerService) {
        this.playerService = playerService;
        this.skinService = skinService;
    }

    @GetMapping(value = "/skin/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getSkin(@PathVariable("name") String name) {
        Optional<Skin> skinOptional = skinService.findSkinByName(name);
        return skinOptional.map(Skin::getContent)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/front/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getFront(@PathVariable("name") String name) {
        Optional<Skin> skinOptional = skinService.findSkinByName(name);
        return skinOptional.map(skin -> MinecraftSkinUtil.getPlayerSkinFront(skin, 4))
                .map(MinecraftSkinUtil.SkinImage::toByteArray)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/back/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBack(@PathVariable("name") String name) {
        Optional<Skin> skinOptional = skinService.findSkinByName(name);
        return skinOptional.map(skin -> MinecraftSkinUtil.getPlayerSkimBack(skin, 4))
                .map(MinecraftSkinUtil.SkinImage::toByteArray)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/cloak/{name}")
    public ResponseEntity<byte[]> getCloak(@PathVariable("name") String name) {
        return ResponseEntity.notFound().build();
    }
}
