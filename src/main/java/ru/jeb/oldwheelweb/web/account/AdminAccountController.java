package ru.jeb.oldwheelweb.web.account;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.jeb.oldwheelweb.model.dto.account.AccountInfoDTO;
import ru.jeb.oldwheelweb.model.dto.account.SimpleAccountInfoDTO;
import ru.jeb.oldwheelweb.service.AccountService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Jeb
 */
@RestController
@RequestMapping("/admin/account")
public class AdminAccountController {
    private final AccountService accountService;

    public AdminAccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @PostMapping("/give/admin/{name}")
    public ResponseEntity giveAdmin(@PathVariable("name") String name) {
        accountService.findByUsername(name).ifPresent(account -> {
            accountService.giveAdmin(account);
        });
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity allAccounts() {
        return ResponseEntity.ok(accountService.findAll().stream()
                .map(SimpleAccountInfoDTO::new)
                .collect(Collectors.toList()));
    }

    @PostMapping("/delete/{name}")
    public ResponseEntity delete(@PathVariable("name") String name) {
        accountService.findByUsername(name).ifPresent(account -> {
            accountService.delete(account);
        });
        return ResponseEntity.ok().build();
    }

    @GetMapping("/info/{name}")
    public ResponseEntity<AccountInfoDTO> info(@PathVariable("name") String name) {
        Optional<AccountInfoDTO> accountInfo = accountService.findByUsername(name).map(AccountInfoDTO::new);
        if (accountInfo.isPresent()) {
            return ResponseEntity.ok(accountInfo.get());
        }
        throw new UsernameNotFoundException("Аккаунт не найден.");
    }

    @GetMapping("/get/full")
    public ResponseEntity<List<AccountInfoDTO>> getPage() {
        return ResponseEntity.ok(accountService.findAll()
                .stream()
                .map(AccountInfoDTO::new)
                .collect(Collectors.toList()));
    }


    @PostMapping("/ban/{name}")
    public ResponseEntity ban(@PathVariable("name") String name) {
        accountService.findByUsername(name).ifPresent(account -> {
            accountService.ban(account);
        });
        return ResponseEntity.ok().build();
    }

}
