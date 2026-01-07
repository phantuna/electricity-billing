package com.example.electricity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.electricity.config.HasPermission;
import com.example.electricity.dto.request.UserRequest;
import com.example.electricity.dto.response.ApiResponse;
import com.example.electricity.dto.response.UserResponse;
import com.example.electricity.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@Validated
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    ApiResponse< UserResponse> createUser(@Valid @RequestBody UserRequest entity) {
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(userService.createUser(entity));
        return response;
    }

    @GetMapping("/detail")
    @HasPermission("VIEW_USER")
    ApiResponse< List<UserResponse>> getUser() {
        ApiResponse<List<UserResponse>> response = new ApiResponse<>();
        response.setResult(userService.getAllUsers()); 
        return response;
    }
    
    
}
