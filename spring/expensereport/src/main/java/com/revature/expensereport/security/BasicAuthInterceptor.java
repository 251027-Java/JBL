package com.revature.expensereport.security;

import com.revature.expensereport.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class BasicAuthInterceptor implements HandlerInterceptor {
    private final UserRepository userRepository;

    public BasicAuthInterceptor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        var authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Basic ")) {
            String base = authHeader.substring(6);
            var decoded = Base64.getDecoder().decode(base);
            String creds = new String(decoded, StandardCharsets.UTF_8);

            var parts = creds.split(":");
            if (parts.length == 2) {
                String username = parts[0];
                String password = parts[1];

                var entity = userRepository.findByUsername(username);
                return entity.isPresent() && entity.get().getPassword().equals(password);
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println("Unauthorized access");
        return false;
    }
}
