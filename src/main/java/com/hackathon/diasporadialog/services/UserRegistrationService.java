package com.hackathon.diasporadialog.services;

import com.hackathon.diasporadialog.DTO.UserRegistrationDtoRequest;
import com.hackathon.diasporadialog.DTO.UserRegistrationDtoResponse;

public interface UserRegistrationService {


    UserRegistrationDtoResponse register(UserRegistrationDtoRequest registrationDtoRequest);

    void grantAdminPermissions(Long id, String email);
}
