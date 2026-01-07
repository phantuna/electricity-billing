package com.example.electricity.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.example.electricity.dto.request.RoleRequest;
import com.example.electricity.dto.response.RoleResponse;
import com.example.electricity.entity.Permission;
import com.example.electricity.entity.Role;
import com.example.electricity.exception.AppException;
import com.example.electricity.exception.ErrorCode;
import com.example.electricity.repository.PermissionRepository;
import com.example.electricity.repository.RoleRepository;

@Service
public class RoleService {
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RoleRepository roleRepository;
    
    public RoleResponse assignPermissions(@NonNull Long roleId,@NonNull Set<Long> permissionIds) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
       
        Set<Permission> permissions = permissionRepository.findAllById(permissionIds)
                .stream().collect(Collectors.toSet());

        if(permissions.isEmpty()){
            throw new AppException(ErrorCode.PERMISSION_NOT_FOUND);
        }

        role.setPermissions(permissions);
        Role savedRole = roleRepository.save(role);

        RoleResponse response = new RoleResponse();
        response.setId(savedRole.getId());
        response.setName(savedRole.getName());
        response.setDescription(savedRole.getDescription());
         Set<String> permissionNames = savedRole.getPermissions()
            .stream()
            .map(Permission::getName)
            .collect(Collectors.toSet());

        response.setPermissions(permissionNames);
        return response;
    }

    public RoleResponse createdRole( RoleRequest request){

        if (roleRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }
        Role role = new Role();
        role.setName(request.getName());
        role.setDescription(request.getDescription());
        Role saved = roleRepository.save(role);

        RoleResponse roleResponse = new RoleResponse();
        
        roleResponse.setName(saved.getName());

        return roleResponse;
    }

    public void deleteRole(Long id){
        roleRepository.deleteById(id);
    }
    public RoleResponse updatedRole(Long id,RoleRequest request){
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        role.setName(request.getName());
        role.setDescription(request.getDescription());

        Role saved = roleRepository.save(role);

        RoleResponse response = new RoleResponse();
        response.setName(saved.getName());
        response.setDescription(saved.getDescription());

        return response;
    }

    public List<RoleResponse> getAllRole(){
        List<Role> list = roleRepository.findAll();
        List<RoleResponse> response = new ArrayList<>();
        for (Role role : list) {
            RoleResponse dto = new RoleResponse();
            dto.setId(role.getId());
            dto.setName(role.getName());
            dto.setDescription(role.getDescription());
            Set<String> permissionNames = role.getPermissions()
            .stream()
            .map(Permission::getName)
            .collect(Collectors.toSet());
            dto.setPermissions(permissionNames);
            response.add(dto);
        }
        return response;
    }
}
