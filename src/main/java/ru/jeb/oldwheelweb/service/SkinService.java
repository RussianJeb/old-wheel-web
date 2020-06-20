package ru.jeb.oldwheelweb.service;

import org.springframework.web.multipart.MultipartFile;
import ru.jeb.oldwheelweb.model.entity.Player;
import ru.jeb.oldwheelweb.model.entity.Skin;

import java.io.IOException;
import java.util.Optional;

/**
 * @author Jeb
 */
public interface SkinService {
    Optional<Skin> changeSkin(Player player, MultipartFile skin) throws IOException;

    boolean validSkin(MultipartFile skinFile) throws IOException;

    Optional<Skin> findSkinByName(String name);
}
