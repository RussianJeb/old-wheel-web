package ru.jeb.oldwheelweb.model.dto.account;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author Jeb
 */
@Data
@NoArgsConstructor
public class RegisterAccountDTO {
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Неккоректное имя")
    private String username;
    @NotBlank
    private String password;
    @Email
    private String email;
    @Pattern(regexp = "(.+#\\d{4})|(https?://vk\\.com/.+)|^()?$", message = "Некоректные контакты")
    private String contact;
    private String token;

    public RegisterAccountDTO(String username, String password, @Email String email, String contact) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.contact = contact;
    }
}
