package ru.jeb.oldwheelweb.service;

import org.springframework.data.domain.Pageable;
import ru.jeb.oldwheelweb.model.dto.account.AccountDTO;
import ru.jeb.oldwheelweb.model.dto.account.LoginDTO;
import ru.jeb.oldwheelweb.model.dto.account.RegisterAccountDTO;
import ru.jeb.oldwheelweb.model.entity.Account;

import java.util.List;
import java.util.Optional;

/**
 * @author Jeb
 */
public interface AccountService {


    Optional<Account> findByEmail(String email);

    Account createAccount(RegisterAccountDTO registerAccountDTO);

    Account changePassword(Account account, String password);

    Optional<Account> findByUsername(String username);

    Optional<Account> findByUsernameAndEmail(String username, String email);


    List<Account> findAll();

    void ban(Account account);


    void delete(Account account);

    List<Account> findByPageable(Pageable pageable);

    LoginDTO login(AccountDTO account, Account realAccount);

    boolean checkPassword(Account account, String password);

    Account giveAdmin(Account account);

    Account activate(Account account);

    Account save(Account account);

    Account changeContact(Account account, String contact);
}
