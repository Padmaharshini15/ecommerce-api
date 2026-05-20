package com.example.ecommerce_api.model.dto.request;

import com.example.ecommerce_api.model.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "First Name is required!")
    @Size(min = 5,max = 40, message = "First Name should be between 5 to 40 characters" )
    private String firstName;

    @NotBlank(message = "Last Name is required!")
    @Size(min = 5,max = 40, message = "Last Name should be between 5 to 40 characters" )
    private String lastName;

    @Email(message = "Valid Email is required!")
    @NotBlank(message = "Email is required!")
    private String email;

    @NotBlank(message = "Password is required!")
    @Size(min = 6,message = "Password should be atleast 6 characters" )
    private String password;

    @NotNull(message = "Role is required!")
    private UserRole role;

}
