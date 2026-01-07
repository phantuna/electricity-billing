package com.example.electricity.dto.request;


import java.util.Set;

import lombok.Data;

@Data
public class PermissionRequest {
    private Set<Long> permissionIds;

}