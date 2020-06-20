package ru.jeb.oldwheelweb.web.account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.jeb.oldwheelweb.exception.UsernameAlreadyExistException;
import ru.jeb.oldwheelweb.model.dto.Error;
import ru.jeb.oldwheelweb.model.dto.account.*;
import ru.jeb.oldwheelweb.model.entity.Account;
import ru.jeb.oldwheelweb.model.entity.ForgottenToken;
import ru.jeb.oldwheelweb.model.entity.VerificationToken;
import ru.jeb.oldwheelweb.service.AccountService;
import ru.jeb.oldwheelweb.service.CaptchaService;
import ru.jeb.oldwheelweb.service.MailService;
import ru.jeb.oldwheelweb.service.TokenService;
import ru.jeb.oldwheelweb.service.impl.LoginAttemptService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

/**
 * @author Jeb
 */
@RestController
@Slf4j
@RequestMapping("/account/")
public class AccountController {
    private final AccountService accountService;
    private final CaptchaService captchaService;
    private final LoginAttemptService loginAttemptService;
    private final TokenService tokenService;
    private final MailService mailService;

    public AccountController(AccountService accountService, CaptchaService captchaService, LoginAttemptService loginAttemptService, TokenService tokenService, MailService mailService) {
        this.accountService = accountService;
        this.captchaService = captchaService;
        this.loginAttemptService = loginAttemptService;
        this.tokenService = tokenService;
        this.mailService = mailService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(@RequestBody AccountDTO account, HttpServletRequest request) {
        if (loginAttemptService.isBlocked(loginAttemptService.getClientIP(request))) {
            throw new BadCredentialsException("Неправильный логин или пароль.");
        }
        Optional<Account> accountOptional = accountService.findByUsername(account.getUsername());
        if (accountOptional
                .filter(Account::isEnabled)
                .map(a -> accountService.checkPassword(a, account.getPassword()))
                .orElse(false)) {
            loginAttemptService.loginSucceeded(loginAttemptService.getClientIP(request));
            return ResponseEntity.ok(accountService.login(account, accountOptional.get()));
        } else {
            loginAttemptService.loginFailed(loginAttemptService.getClientIP(request));
            throw new BadCredentialsException("Неправильный логин или пароль");
        }

    }

    @PostMapping("/verification")
    public ResponseEntity verification(@RequestBody VerificationDTO verificationDTO) {
        Optional<Account> account = accountService.findByUsername(verificationDTO.getUser());
        if (!account.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        Optional<VerificationToken> verificationToken = tokenService.findVerificationToken(verificationDTO.getToken());
        if (verificationToken.isPresent()) {
            tokenService.deleteVerificationToken(verificationToken.get());
            accountService.activate(account.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/change/password")
    public ResponseEntity changePassword(@AuthenticationPrincipal Account account, @RequestParam("password") String password) {
        accountService.changePassword(account, password);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/change/contact")
    public ResponseEntity changeContact(@AuthenticationPrincipal Account account, @RequestParam("contact") String contact) {
        accountService.changeContact(account, contact);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/info")
    public ResponseEntity<AccountInfoDTO> info(@AuthenticationPrincipal Account account) {
        return ResponseEntity.ok(new AccountInfoDTO(account));
    }

    @PostMapping("/forgot")
    public ResponseEntity forgotPassword(@RequestBody ForgotDTO forgotDTO) {
        captchaService.processResponse(forgotDTO.getToken());
        Optional<Account> accountOptional = accountService.findByEmail(forgotDTO.getEmail());
        if (!accountOptional.isPresent()) {
            return ResponseEntity.ok().build();
        }
        ForgottenToken forgottenToken = tokenService.generateForgottenToken(accountOptional.get());
        try {
            mailService.sendForgottenToken(forgottenToken, forgottenToken.getAccount());
        } catch (Exception e) {
            tokenService.deleteForgottenToken(forgottenToken);
            throw new IllegalArgumentException("Произошла ошибка при отправке.");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/restore")
    public ResponseEntity restorePassword(@RequestBody RestorePasswordDTO restorePassword) {
        Optional<ForgottenToken> tokenOptional = tokenService.findForgottenToken(restorePassword.getToken(), restorePassword.getUser());
        if (!tokenOptional.isPresent()) {
            throw new IllegalArgumentException("Произошла ошибка при восстановлении");
        }
        ForgottenToken token = tokenOptional.get();
        accountService.changePassword(token.getAccount(), restorePassword.getPassword());
        tokenService.deleteForgottenToken(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity registerAccount(@Valid @RequestBody RegisterAccountDTO account) {
        captchaService.processResponse(account.getToken());
        Optional<Account> accountOptional = accountService.findByUsernameAndEmail(account.getUsername(), account.getEmail());
        if (accountOptional.isPresent()) {
            throw new UsernameAlreadyExistException("Пользователь уже существует");
        }
        Account a = accountService.createAccount(account);
        VerificationToken token = tokenService.generateVerificationToken(a);
        try {
            mailService.sendToken(token, a);
        } catch (Exception e) {
            tokenService.deleteVerificationToken(token);
            accountService.delete(a);
            throw new IllegalArgumentException("Произошла ошибка при отправке, попробуйте еще раз.");
        }
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handle(Exception ex) {
        return ResponseEntity.badRequest().body(new Error(ex.getMessage()));
    }
}
