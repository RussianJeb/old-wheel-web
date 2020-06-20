package ru.jeb.oldwheelweb.data;

import org.springframework.data.repository.CrudRepository;
import ru.jeb.oldwheelweb.model.entity.Skin;

import java.util.Optional;

/**
 * @author Jeb
 */
public interface SkinRepository extends CrudRepository<Skin, Long> {
    Optional<Skin> findByNameIgnoreCase(String name);
}
