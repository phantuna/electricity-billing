package com.example.electricity.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.electricity.config.HasPermission;
import com.example.electricity.dto.request.PermissionRequest;
import com.example.electricity.dto.request.RoleRequest;
import com.example.electricity.dto.response.ApiResponse;
import com.example.electricity.dto.response.RoleResponse;
import com.example.electricity.service.RoleService;



@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("/{roleid}")
    @HasPermission("UPDATE_EMPLOYEE")
    ApiResponse<RoleResponse> assignPermissions(@PathVariable @NonNull Long roleid , @RequestBody PermissionRequest request) {
        ApiResponse<RoleResponse> response = new ApiResponse<>();
        response.setResult(roleService.assignPermissions(roleid, request.getPermissionIds()));
        System.out.println("Permission IDs nhận được: " + request.getPermissionIds());
        return response;
    }

    @PostMapping("/create")
    @HasPermission("UPDATE_EMPLOYEE")
    ApiResponse<RoleResponse> createdRole(@RequestBody RoleRequest entity) {
        ApiResponse<RoleResponse> response = new ApiResponse<>();
        response.setResult(roleService.createdRole(entity)); 
        return response;
    }
    
    @PutMapping("update/{id}")
    @HasPermission("UPDATE_EMPLOYEE")
    ApiResponse< RoleResponse> updatedRole(@PathVariable Long id, @RequestBody RoleRequest entity) {
        ApiResponse<RoleResponse> response = new ApiResponse<>();
        response.setResult(roleService.updatedRole(id, entity));
        return response;
    }

    @GetMapping("/allrole")
    public List<RoleResponse> getAllRole() {
        return roleService.getAllRole();
    }
    
}
