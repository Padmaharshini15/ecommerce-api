package com.example.ecommerce_api.service.inf;

import com.example.ecommerce_api.model.dto.request.LoginRequest;
import com.example.ecommerce_api.model.dto.request.RegisterRequest;
import com.example.ecommerce_api.model.dto.response.AuthResponse;

public interface IAuthService {

    String register(RegisterRequest request);
    AuthResponse login(LoginRequest request);

}
