package com.hackathon.diasporadialog.DTO.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AuthAndRegistrationResponseDTO {

    private final String email;

    private final String role;

    private final String jwt;
}
