package com.dataworks.eventsubscriber;

import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.repository.UserRepository;
import com.dataworks.eventsubscriber.service.category.CategoryService;
import com.dataworks.eventsubscriber.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpHeaders;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.Base64;

@ControllerAdvice
@RequiredArgsConstructor

public class BasicAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final UserService userService;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        // 401
        var message = "";

        if (authException.getClass() == LockedException.class) {
            var authHeader = request.getHeader("Authorization").replace("Basic ", "");
            var decodedHeader = Base64.getDecoder().decode(authHeader);
            var headerParts = new String(decodedHeader).split(":");
            var email = headerParts[0];
            var user = userService.findByEmail(email);
            message = user.getBlockedDescription();
        }

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed " + message);
    }
}
