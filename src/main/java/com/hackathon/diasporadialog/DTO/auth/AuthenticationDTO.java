package com.hackathon.diasporadialog.DTO.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AuthenticationDTO {

    @NotEmpty(message = "Email cannot be empty !")
    @Size(min = 12, max = 35, message = "Size of the email should be between 12 and 35 characters !")
    @Email(message = "")
    private String email;

    private String password;

}
