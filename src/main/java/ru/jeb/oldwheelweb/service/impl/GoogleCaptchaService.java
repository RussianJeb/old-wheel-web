package ru.jeb.oldwheelweb.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.jeb.oldwheelweb.config.ApplicationConfig;
import ru.jeb.oldwheelweb.exception.ReCaptchaException;
import ru.jeb.oldwheelweb.model.dto.GoogleResponse;
import ru.jeb.oldwheelweb.service.CaptchaService;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.regex.Pattern;

/**
 * @author Jeb
 */
@Service
public class GoogleCaptchaService implements CaptchaService {
    private final ApplicationConfig webSettings;
    private final HttpServletRequest request;
    private final RestTemplate restTemplate;
    private final ReCaptchaAttemptService reCaptchaAttemptService;

    private static final Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");

    public GoogleCaptchaService(ApplicationConfig webSettings, RestTemplate restTemplate, HttpServletRequest request, ReCaptchaAttemptService reCaptchaAttemptService) {
        this.webSettings = webSettings;
        this.restTemplate = restTemplate;
        this.request = request;
        this.reCaptchaAttemptService = reCaptchaAttemptService;
    }

    @Override
    public void processResponse(String response) {
        if (!responseSanityCheck(response)) {
            throw new IllegalArgumentException("Response has error");
        }

        URI verifyUri = URI.create(String.format(
                "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s&remoteip=%s",
                webSettings.getCaptchaSecret(), response, getClientIP()));
        try {
            GoogleResponse googleResponse = restTemplate.getForObject(verifyUri, GoogleResponse.class);

            if (!googleResponse.isSuccess()) {
                if (googleResponse.hasClientError()) {
                    reCaptchaAttemptService.reCaptchaFailed(getClientIP());
                }
                throw new ReCaptchaException("reCaptcha was not successfully validated");
            }
        } catch (RestClientException rce) {
            throw new ReCaptchaException("Registration unavailable at this time. Please try again later.");
        }
        if (reCaptchaAttemptService.isBlocked(getClientIP())) {
            throw new ReCaptchaException("Registration unavailable at this time. Please try again later.");
        }
        reCaptchaAttemptService.reCaptchaSucceeded(getClientIP());
    }

    private boolean responseSanityCheck(String response) {
        return StringUtils.hasLength(response) && RESPONSE_PATTERN.matcher(response).matches();
    }

    private String getClientIP() {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
