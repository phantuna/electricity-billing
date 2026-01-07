package com.example.electricity.service.authentication;


import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.electricity.dto.request.AuthenticationRequest;
import com.example.electricity.dto.response.AuthenticationResponse;
import com.example.electricity.entity.User;
import com.example.electricity.exception.AppException;
import com.example.electricity.exception.ErrorCode;
import com.example.electricity.repository.UserRepository;
import com.example.electricity.service.JwtService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_ROLE_NOT_FOUND));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }

        Set<String> permissions = user.getRole()
                .getPermissions()
                .stream()
                .map(Permission -> Permission.getName())
                .collect(Collectors.toSet());

        String token = jwtService.generateToken(user.getUsername(), permissions);

        return AuthenticationResponse.builder()
                .authenticated(true)
                .token(token)
                .permissions(permissions)
                .build();
    }
}
