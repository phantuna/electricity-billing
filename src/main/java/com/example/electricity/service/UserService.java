package com.example.electricity.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.electricity.dto.request.UserRequest;
import com.example.electricity.dto.response.UserResponse;
import com.example.electricity.entity.Role;
import com.example.electricity.entity.User;
import com.example.electricity.exception.AppException;
import com.example.electricity.exception.ErrorCode;
import com.example.electricity.repository.RoleRepository;
import com.example.electricity.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public UserResponse  createUser(UserRequest dto) {

        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
         if (userRepository.existsByUsername(dto.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        user.setRole(role);
        User savedUser = userRepository.save(user);

        
        UserResponse  response = new UserResponse ();
        response.setUsername(savedUser.getUsername());
        response.setPassword(savedUser.getPassword());
        response.setRole(savedUser.getRole().getName());
        return response;

    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse>  dtos = new ArrayList<>();
        
        for (User user : users){
            UserResponse userDto = new UserResponse();
            userDto.setId(user.getId());
            userDto.setUsername(user.getUsername());
            userDto.setPassword(user.getPassword());
            dtos.add(userDto);
        }
        return dtos;
    }
   


    public void delete( Long id){
         userRepository.deleteById(id);
    }

    public UserResponse update(Long id,UserRequest request){
        User user = userRepository.findById(id)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setUsername(request.getUsername());
        
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
        user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        }
        User savedUser = userRepository.save(user);

        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setUsername(savedUser.getUsername());
        response.setPassword(savedUser.getPassword());
        return response;
    }
}
