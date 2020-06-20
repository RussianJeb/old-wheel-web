package ru.jeb.oldwheelweb.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.jeb.oldwheelweb.data.AccountRepository;

/**
 * @author Jeb
 */
@Service
public class WheelUserDetailsService implements UserDetailsService {
    private final AccountRepository accountService;

    public WheelUserDetailsService(AccountRepository accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return accountService.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
