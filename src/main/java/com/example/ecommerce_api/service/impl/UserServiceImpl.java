package com.example.ecommerce_api.service.impl;

import com.example.ecommerce_api.exception.ResourceNotFoundException;
import com.example.ecommerce_api.exception.UnauthorizedException;
import com.example.ecommerce_api.model.User;
import com.example.ecommerce_api.model.UserPrinciple;
import com.example.ecommerce_api.model.dto.response.UserResponse;
import com.example.ecommerce_api.repository.UserRepository;
import com.example.ecommerce_api.service.inf.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(user -> this.mapToUserResponse(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserById(Long id) {
        return mapToUserResponse(userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with ID: "+id)));
    }

    @Override
    public User getCurrentUser(){
       String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UnauthorizedException("User not found!"));
    }

    private UserResponse mapToUserResponse(User user){
       return UserResponse.builder()
               .id(user.getId())
               .firstName(user.getFirstName())
               .lastName(user.getLastName())
               .email(user.getEmail())
               .role(user.getRole())
               .build();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       User user =  userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found with Email"));
       return new UserPrinciple(user);
    }
}
