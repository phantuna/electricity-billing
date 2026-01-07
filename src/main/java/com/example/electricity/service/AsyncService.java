package com.example.electricity.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {
    @Async
    public void sendEmailAsync(String email) {
        try {
            Thread.sleep(5000); // giả lập tác vụ nặng
            System.out.println("Email sent to: " + email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedRate = 5000)
    public void dailyEmailTask() {
        sendEmailAsync("default@gmail.com");
    }
}
