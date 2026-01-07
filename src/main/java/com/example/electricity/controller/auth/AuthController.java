package com.example.electricity.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.electricity.dto.request.AuthenticationRequest;
import com.example.electricity.dto.response.ApiResponse;
import com.example.electricity.dto.response.AuthenticationResponse;
import com.example.electricity.service.authentication.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final AuthenticationService authenticationService;

    @PostMapping
    ApiResponse< AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        
        ApiResponse< AuthenticationResponse> response = new ApiResponse<>();
        response.setResult(authenticationService.authenticate(request));
        return response;
    }
}