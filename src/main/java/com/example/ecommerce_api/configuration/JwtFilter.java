package com.example.ecommerce_api.configuration;

import com.example.ecommerce_api.exception.JwtValidationException;
import com.example.ecommerce_api.model.dto.response.ErrorResponse;
import com.example.ecommerce_api.service.inf.IJwtService;
import com.example.ecommerce_api.service.inf.IUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ExceptionDepthComparator;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final IJwtService jwtService;
    private final IUserService userService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/api/v1/auth/")
                || path.startsWith("/swagger-ui/")
                || path.startsWith("/v3/api-docs")
                || path.equals("/swagger-ui.html");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        String token = null;
        String email = null;

        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            token = authHeader.substring(7);
            email = jwtService.extractUserEmail(token);
                if (email != null &&
                        SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails =
                            userService.loadUserByUsername(email);
                    if (jwtService.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.getAuthorities());
                        authToken.setDetails(
                                new WebAuthenticationDetailsSource()
                                        .buildDetails(request));
                        SecurityContextHolder.getContext()
                                .setAuthentication(authToken);
                    }
                }

            filterChain.doFilter(request, response);
        } catch (Exception ex) {
           handleJwtValidationException(response, new JwtValidationException("Jwt Validation Failed!"));

        }
    }

    private void handleJwtValidationException(HttpServletResponse response, JwtValidationException ex) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("Jwt Validation Failed!")
                .message(ex.getMessage())
                .build();

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(errorResponse));
    }
}