package ru.jeb.oldwheelweb.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.jeb.oldwheelweb.model.dto.player.AdminPlayerFormDTO;

import java.io.IOException;

/**
 * @author Jeb
 */
@Component
public class StringToAdminFormConverter implements Converter<String, AdminPlayerFormDTO> {
    private final ObjectMapper objectMapper;

    public StringToAdminFormConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public AdminPlayerFormDTO convert(String s) {
        try {
            return objectMapper.readValue(s, AdminPlayerFormDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
