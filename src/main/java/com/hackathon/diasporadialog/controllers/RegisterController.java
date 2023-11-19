package com.hackathon.diasporadialog.controllers;

import com.hackathon.diasporadialog.DTO.UserRegistrationDtoRequest;
import com.hackathon.diasporadialog.DTO.UserRegistrationDtoResponse;
import com.hackathon.diasporadialog.DTO.auth.AuthAndRegistrationResponseDTO;
import com.hackathon.diasporadialog.exceptions.ValidationCustomException;
import com.hackathon.diasporadialog.security.JWTUtil;
import com.hackathon.diasporadialog.services.UserRegistrationService;
import com.hackathon.diasporadialog.util.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/authentication")
public class RegisterController {

    private final UserRegistrationService userRegistrationService;

    private final UserValidator userValidator;

    private final JWTUtil jwtUtil;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/register")
    public ResponseEntity<AuthAndRegistrationResponseDTO> register(@RequestBody @Valid UserRegistrationDtoRequest registrationDtoRequest,
                                                                   BindingResult bindingResult) {
        log.info("Registering user.");
        userValidator.validate(registrationDtoRequest, bindingResult);

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new LinkedHashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                if (!errors.containsKey(error.getField())) {
                    errors.put(error.getField(), error.getDefaultMessage());
                }
            }

            throw new ValidationCustomException(errors);
        }

        UserRegistrationDtoResponse created = userRegistrationService.register(registrationDtoRequest);

        String token = jwtUtil.generateAccessToken(registrationDtoRequest.getEmail());

        AuthAndRegistrationResponseDTO response = new AuthAndRegistrationResponseDTO(
                created.getEmail(),
                created.getRole().toString(),
                token
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam("token") String token) {
        boolean isVerified = userRegistrationService.verifyUser(token);
        if (isVerified) {
            return ResponseEntity.ok("User has been successfully verified.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User verification failed.");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/grantAdmin")
    public ResponseEntity<HttpStatus> grantAdminPermissions(@RequestParam(value = "userId", required = false) Long userId,
                                                            @RequestParam(value = "email", required = false) String email) {
        userRegistrationService.grantAdminPermissions(userId, email);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
