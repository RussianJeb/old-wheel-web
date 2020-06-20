package ru.jeb.oldwheelweb.service;

import ru.jeb.oldwheelweb.model.entity.Account;
import ru.jeb.oldwheelweb.model.entity.ForgottenToken;
import ru.jeb.oldwheelweb.model.entity.VerificationToken;

/**
 * @author Jeb
 */
public interface MailService {

    boolean sendToken(VerificationToken token, Account to);

    boolean sendForgottenToken(ForgottenToken token, Account to);


    boolean sendMessage(String message, String subject, Account to);
}
