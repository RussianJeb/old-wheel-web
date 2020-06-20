package ru.jeb.oldwheelweb.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.jeb.oldwheelweb.data.SkinRepository;
import ru.jeb.oldwheelweb.model.entity.Player;
import ru.jeb.oldwheelweb.model.entity.Skin;
import ru.jeb.oldwheelweb.service.SkinService;
import ru.jeb.oldwheelweb.utils.MinecraftSkinUtil;

import java.io.IOException;
import java.util.Optional;

/**
 * @author Jeb
 */
@Service
public class DefaultSkinService implements SkinService {
    private final SkinRepository skinRepository;

    public DefaultSkinService(SkinRepository skinRepository) {
        this.skinRepository = skinRepository;
    }

    @Override
    @CachePut(value = "skin", key = "#player.name")
    public Optional<Skin> changeSkin(Player player, MultipartFile skinFile) throws IOException {
        Skin skin = player.getSkin();
        skin.setContent(skinFile.getBytes());
        return Optional.of(skinRepository.save(skin));
    }

    @Override
    public boolean validSkin(MultipartFile skinFile) {
        try {
            MinecraftSkinUtil.getPlayerSkin(skinFile.getBytes(), false);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Cacheable(value = "skin", key = "#name")
    public Optional<Skin> findSkinByName(String name) {
        return skinRepository.findByNameIgnoreCase(name);
    }
}
