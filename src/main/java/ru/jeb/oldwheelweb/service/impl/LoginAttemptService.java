package ru.jeb.oldwheelweb.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author Jeb
 */
@Service
public class LoginAttemptService {
    private final int MAX_ATTEMPT = 7;
    private final LoadingCache<String, Integer> attemptsCache;

    public LoginAttemptService() {
        attemptsCache = CacheBuilder.newBuilder().expireAfterWrite(4, TimeUnit.HOURS).build(new CacheLoader<String, Integer>() {
            @Override
            public Integer load(final String key) {
                return 0;
            }
        });
    }

    public void loginSucceeded(final String key) {
        attemptsCache.invalidate(key);
    }

    public void loginFailed(final String key) {
        int attempts = attemptsCache.getUnchecked(key);
        attempts++;
        attemptsCache.put(key, attempts);
    }

    public boolean isBlocked(final String key) {
        return attemptsCache.getUnchecked(key) >= MAX_ATTEMPT;
    }

    public String getClientIP(HttpServletRequest request) {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
