package com.example.ecommerce_api.service.impl;

import com.example.ecommerce_api.exception.BadRequestException;
import com.example.ecommerce_api.model.Cart;
import com.example.ecommerce_api.model.User;
import com.example.ecommerce_api.model.Wishlist;
import com.example.ecommerce_api.model.dto.request.LoginRequest;
import com.example.ecommerce_api.model.dto.request.RegisterRequest;
import com.example.ecommerce_api.model.dto.response.AuthResponse;
import com.example.ecommerce_api.model.enums.UserRole;
import com.example.ecommerce_api.repository.CartRepository;
import com.example.ecommerce_api.repository.UserRepository;
import com.example.ecommerce_api.repository.WishlistRepository;
import com.example.ecommerce_api.service.inf.IAuthService;
import com.example.ecommerce_api.service.inf.IJwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final WishlistRepository wishlistRepository;
    private final BCryptPasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final IJwtService jwtService;

    @Override
    public String register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail()))
                throw new BadRequestException("Email already exists!");

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .role(request.getRole())
                .password(encoder.encode(request.getPassword()))
                .build();

        User savedUser = userRepository.save(user);

        if (request.getRole().equals(UserRole.USER)) {
            Cart cart = Cart.builder()
                    .user(savedUser)
                    .build();
            cartRepository.save(cart);

            Wishlist wishlist = Wishlist.builder()
                    .user(savedUser)
                    .build();
            wishlistRepository.save(wishlist);
        }
        return "User Created Successfully!";
    }

    @Override
    public AuthResponse login(LoginRequest request) {
//       User user = userRepository.findByEmail(request.getEmail())
//               .orElseThrow(() ->new BadRequestException("Invalid Email or Password!"));
//
//       if(!user.getPassword().equals(request.getPassword()))
//           throw new BadRequestException("Invalid Email or Password!");
//
//       return AuthResponse.builder()
//               .token("123")
//               .build();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));

        if (authentication.isAuthenticated()) {

            return AuthResponse.builder()
                    .token(jwtService.generateToken(request.getEmail()))
                    .build();
        }
        return AuthResponse.builder()
                .token("")
                .build();
    }
}
