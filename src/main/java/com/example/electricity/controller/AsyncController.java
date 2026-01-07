package com.example.electricity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.electricity.dto.response.ApiResponse;
import com.example.electricity.service.AsyncService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/async")
@RequiredArgsConstructor
public class AsyncController {
    private final AsyncService asyncService;

    @GetMapping("/send/{email}")
    ApiResponse< String> send(@PathVariable String email) {
        asyncService.dailyEmailTask();
        ApiResponse< String> response = new ApiResponse<>();
        response.setResult("Processing email in background...");
        return response;
    }
}
