package com.example.demo.dto;

import com.example.demo.config.validation.groups.PasswordRequired;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Schema(description = "User Data Transfer Object")
public class UserDto {

    @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(name = "username", requiredMode = Schema.RequiredMode.REQUIRED, example = "john_doe")
    @NotBlank
    private String username;

    @Schema(name = "email", requiredMode = Schema.RequiredMode.REQUIRED, example = "john.doe@example.com")
    @Email
    @NotBlank
    private String email;

    @Schema(name = "password", example = "secretpassword")
    @NotBlank(groups = {PasswordRequired.class})
    private String password;
}

