package ru.jeb.oldwheelweb.service;

import ru.jeb.oldwheelweb.model.entity.Account;
import ru.jeb.oldwheelweb.model.entity.ForgottenToken;
import ru.jeb.oldwheelweb.model.entity.VerificationToken;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Jeb
 */
public interface TokenService {

    Optional<VerificationToken> findVaildVerificationToken(UUID token, Account account);

    VerificationToken generateVerificationToken(Account account);

    ForgottenToken generateForgottenToken(Account account);

    Optional<ForgottenToken> findForgottenToken(UUID token, String username);

    Optional<VerificationToken> findVerificationToken(UUID token, Account account);

    Optional<VerificationToken> findVerificationToken(UUID token);


    VerificationToken saveVerificationToken(VerificationToken verificationToken);

    void deleteVerificationToken(VerificationToken verificationToken);

    ForgottenToken saveForgottenToken(ForgottenToken forgottenToken);

    void deleteForgottenToken(ForgottenToken forgottenToken);

    List<VerificationToken> findAllVerificationToken();

}
