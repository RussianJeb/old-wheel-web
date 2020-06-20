package ru.jeb.oldwheelweb.service.impl;

import org.springframework.stereotype.Service;
import ru.jeb.oldwheelweb.data.ForgottenTokenRepository;
import ru.jeb.oldwheelweb.data.VerificationTokenRepository;
import ru.jeb.oldwheelweb.model.entity.Account;
import ru.jeb.oldwheelweb.model.entity.ForgottenToken;
import ru.jeb.oldwheelweb.model.entity.VerificationToken;
import ru.jeb.oldwheelweb.service.TokenService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Jeb
 */
@Service
public class DefaultTokenService implements TokenService {
    private final VerificationTokenRepository verificationTokenRepository;
    private final ForgottenTokenRepository forgottenTokenRepository;

    public DefaultTokenService(VerificationTokenRepository verificationTokenRepository, ForgottenTokenRepository forgottenTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.forgottenTokenRepository = forgottenTokenRepository;
    }

    @Override
    public Optional<VerificationToken> findVaildVerificationToken(UUID token, Account account) {
        return findVerificationToken(token, account).filter(t -> t.getExpireTime().isAfter(LocalDateTime.now()));
    }

    @Override
    public VerificationToken generateVerificationToken(Account account) {
        return saveVerificationToken(new VerificationToken(account));
    }

    @Override
    public ForgottenToken generateForgottenToken(Account account) {
        return saveForgottenToken(new ForgottenToken(account));
    }

    @Override
    public Optional<ForgottenToken> findForgottenToken(UUID token, String username) {
        return forgottenTokenRepository.findByUniqueIdAndAccount_Username(token, username);
    }

    @Override
    public Optional<VerificationToken> findVerificationToken(UUID token, Account account) {
        return verificationTokenRepository.findByUniqueIdAndAccount(token, account);
    }

    @Override
    public Optional<VerificationToken> findVerificationToken(UUID token) {
        return verificationTokenRepository.findByUniqueId(token);
    }


    @Override
    public VerificationToken saveVerificationToken(VerificationToken verificationToken) {
        return verificationTokenRepository.save(verificationToken);
    }

    @Override
    public void deleteVerificationToken(VerificationToken verificationToken) {
        verificationTokenRepository.delete(verificationToken);
    }

    @Override
    public ForgottenToken saveForgottenToken(ForgottenToken forgottenToken) {
        return forgottenTokenRepository.save(forgottenToken);
    }

    @Override
    public void deleteForgottenToken(ForgottenToken forgottenToken) {
        forgottenTokenRepository.delete(forgottenToken);
    }

    @Override
    public List<VerificationToken> findAllVerificationToken() {
        return verificationTokenRepository.findAll();
    }

}
