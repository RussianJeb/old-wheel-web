package ru.jeb.oldwheelweb.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.jeb.oldwheelweb.model.entity.Account;
import ru.jeb.oldwheelweb.model.entity.ForgottenToken;
import ru.jeb.oldwheelweb.model.entity.VerificationToken;
import ru.jeb.oldwheelweb.service.MailService;

/**
 * @author Jeb
 */
@Slf4j
@Service
@Profile("dev")
public class DevMailService implements MailService {
    @Override
    public boolean sendToken(VerificationToken token, Account to) {
        log.info("New Verification Token: {} to {}", token.getUniqueId(), to.getUsername());
        return false;
    }

    @Override
    public boolean sendForgottenToken(ForgottenToken token, Account to) {
        log.info("New Forgotten Token: {} to {}", token.getUniqueId(), to.getUsername());
        return false;
    }

    @Override
    public boolean sendMessage(String message, String subject, Account to) {
        log.info("New message: {}", message);
        return false;
    }
}
