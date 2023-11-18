package com.hackathon.diasporadialog.DTO;

import com.hackathon.diasporadialog.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDtoResponse {

    private Long id;

    private String name;

    private String email;

    private Role role;
}
