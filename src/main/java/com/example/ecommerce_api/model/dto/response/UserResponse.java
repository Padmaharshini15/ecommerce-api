package com.example.ecommerce_api.model.dto.response;

import com.example.ecommerce_api.model.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Represents user details returned in API responses")
public class UserResponse {

    @Schema(description = "Unique identifier of the user", example = "1")
    private Long id;

    @Schema(description = "First name of the user", example = "Joseph")
    private String firstName;

    @Schema(description = "Last name of the user", example = "Vijay")
    private String lastName;

    @Schema(description = "Email address of the user", example = "cmvijay@gmail.com")
    private String email;

    @Schema(description = "Role assigned to the user (ADMIN, SELLER, CUSTOMER)", example = "CUSTOMER")
    private UserRole role;
}