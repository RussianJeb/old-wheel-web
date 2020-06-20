package ru.jeb.oldwheelweb.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.jeb.oldwheelweb.model.entity.Account;
import ru.jeb.oldwheelweb.model.entity.ForgottenToken;
import ru.jeb.oldwheelweb.model.entity.VerificationToken;
import ru.jeb.oldwheelweb.service.MailService;

/**
 * @author Jeb
 */
@Service
@Profile("prod")
public class DefaultMailService implements MailService {
    private final JavaMailSender mailSender;

    public DefaultMailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public boolean sendToken(VerificationToken token, Account to) {
        String message = String.format("Для подтверждения аккаунта перейдите по ссылке: https://ow-rp.ru/verify/%s?user=%s", token.getUniqueId(), to.getUsername());
        return sendMessage(message, "Подтверждение", to);
    }

    @Override
    public boolean sendForgottenToken(ForgottenToken token, Account to) {
        String message = String.format("Для восстановления пароля перейдите по ссылке: https://ow-rp.ru/forgotten/%s?user=%s", token.getUniqueId(), to.getUsername());
        return sendMessage(message, "Восстановление", to);
    }

    @Override
    public boolean sendMessage(String message, String subject, Account to) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to.getEmail());
        mailMessage.setFrom("verification@ow-rp.ru");
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
        return true;

    }
}
