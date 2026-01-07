package com.example.electricity.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.example.electricity.exception.AppException;
import com.example.electricity.exception.ErrorCode;
import com.example.electricity.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class PermissionAspect {

    private final JwtService jwtService;
    private final HttpServletRequest request;

    @Before("@annotation(hasPermission)")
    public void checkPermission(HasPermission hasPermission) {
        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            throw new AppException(ErrorCode.MISSING_TOKEN);
        }

        token = token.substring(7);

        String requiredPermission = hasPermission.value();

        boolean allowed = jwtService.hasPermission(token, requiredPermission);

        if (!allowed) {
            throw new AppException(ErrorCode.INVALID_PERMISSIONS);
        }
    }
}