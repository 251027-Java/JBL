package com.revature.expensereport.security;

import com.revature.expensereport.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class BasicAuthInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicAuthInterceptor.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public BasicAuthInterceptor(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        var authHeader = request.getHeader("Authorization");
        LOGGER.info("incoming request auth: {}", authHeader);

        if (authHeader != null && authHeader.startsWith("Basic ")) {
            String base = authHeader.substring(6);
            var decoded = Base64.getDecoder().decode(base);
            String creds = new String(decoded, StandardCharsets.UTF_8);

            var parts = creds.split(":");
            if (parts.length == 2) {
                String username = parts[0];
                String password = parts[1];

                var entity = userRepository.findByUsername(username);
                LOGGER.info("incoming user and pass: {} {}", username, password);
                LOGGER.info("incoming encoded password: {}", passwordEncoder.encode(password));

                if (entity.isPresent()
                        && passwordEncoder.matches(password, entity.get().getPassword())) {
                    return true;
                }
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println("Unauthorized access");
        return false;
    }
}
