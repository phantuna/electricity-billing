package com.example.electricity.dto.response;


import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {
    private boolean authenticated;
    private String token;
    private Set<String> permissions;
}
