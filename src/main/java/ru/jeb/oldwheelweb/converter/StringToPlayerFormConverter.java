package ru.jeb.oldwheelweb.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.jeb.oldwheelweb.model.dto.player.PlayerFormDTO;

import java.io.IOException;

/**
 * @author Jeb
 */
@Slf4j
@Component
public class StringToPlayerFormConverter implements Converter<String, PlayerFormDTO> {
    private final ObjectMapper objectMapper;

    public StringToPlayerFormConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public PlayerFormDTO convert(String s) {
        try {
            return objectMapper.readValue(s, PlayerFormDTO.class);
        } catch (IOException e) {
            return null;
        }
    }
}
