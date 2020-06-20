package ru.jeb.oldwheelweb.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import ru.jeb.oldwheelweb.data.AccountRepository;
import ru.jeb.oldwheelweb.model.dto.account.AccountDTO;
import ru.jeb.oldwheelweb.model.dto.account.LoginDTO;
import ru.jeb.oldwheelweb.model.dto.account.RegisterAccountDTO;
import ru.jeb.oldwheelweb.model.entity.Account;
import ru.jeb.oldwheelweb.service.AccountService;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

/**
 * @author Jeb
 */
@Service
public class DefaultAccountService implements AccountService {
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    public DefaultAccountService(PasswordEncoder passwordEncoder, AccountRepository accountRepository) {
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public Account createAccount(RegisterAccountDTO registerAccountDTO) {
        return accountRepository.save(new Account(
                registerAccountDTO.getUsername(),
                passwordEncoder.encode(registerAccountDTO.getPassword()),
                registerAccountDTO.getEmail(),
                registerAccountDTO.getContact())
        );
    }

    @Override
    public Account changePassword(Account account, String password) {
        account.setPassword(passwordEncoder.encode(password));
        return accountRepository.save(account);
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public Optional<Account> findByUsernameAndEmail(String username, String email) {
        return accountRepository.findByUsernameOrEmail(username, email);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public void ban(Account account) {
        account.setEnabled(false);
        accountRepository.save(account);
    }

    @Override
    public void delete(Account account) {
        accountRepository.delete(account);
    }

    @Override
    public List<Account> findByPageable(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    @Override
    public LoginDTO login(AccountDTO account, Account realAccount) {
        return new LoginDTO(
                encodeToBasic(account.getUsername(), account.getPassword()),
                realAccount.getRole()
        );
    }

    @Override
    public boolean checkPassword(Account account, String password) {
        return passwordEncoder.matches(password, account.getPassword());
    }

    @Override
    public Account giveAdmin(Account account) {
        account.setRole(Account.Role.ADMIN);
        return accountRepository.save(account);
    }

    @Override
    public Account activate(Account account) {
        account.setEnabled(true);
        return accountRepository.save(account);
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account changeContact(Account account, String contact) {
        account.setContact(contact);
        return accountRepository.save(account);
    }

    private String encodeToBasic(String username, String password) {
        String tokenDecoded = username + ":" + password;
        return Base64Utils.encodeToString(tokenDecoded.getBytes(StandardCharsets.UTF_8));
    }

}
