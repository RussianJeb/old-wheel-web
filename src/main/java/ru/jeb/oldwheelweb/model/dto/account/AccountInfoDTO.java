package ru.jeb.oldwheelweb.model.dto.account;

import lombok.Data;
import ru.jeb.oldwheelweb.model.dto.player.PlayerSimpleInfoDTO;
import ru.jeb.oldwheelweb.model.entity.Account;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jeb
 */
@Data
public class AccountInfoDTO {
    private String username;
    private Account.Role role;
    private String contact;
    private List<PlayerSimpleInfoDTO> players;

    public AccountInfoDTO(Account account) {
        this.username = account.getUsername();
        this.role = account.getRole();
        this.contact = account.getContact();
        this.players = account.getPlayers().stream().map(PlayerSimpleInfoDTO::new).collect(Collectors.toList());
    }
}
