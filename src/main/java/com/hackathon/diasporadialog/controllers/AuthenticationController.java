package com.hackathon.diasporadialog.controllers;

import com.hackathon.diasporadialog.DTO.auth.AuthAndRegistrationResponseDTO;
import com.hackathon.diasporadialog.DTO.auth.AuthenticationDTO;
import com.hackathon.diasporadialog.security.JWTUtil;
import com.hackathon.diasporadialog.services.impl.OfficialServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final JWTUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    private final OfficialServiceImpl officialService;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<AuthAndRegistrationResponseDTO> authenticate(@RequestBody @Valid AuthenticationDTO authenticationDTO) {
        var role = "";
        UsernamePasswordAuthenticationToken inputToken = new UsernamePasswordAuthenticationToken(
                authenticationDTO.getEmail(), authenticationDTO.getPassword());

        var authentication = authenticationManager.authenticate(inputToken);
        var userDetails = (UserDetails) authentication.getPrincipal();

        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            role = authority.getAuthority();
        }

        var jwt = jwtUtil.generateAccessToken(authenticationDTO.getEmail());

        if (role.equals("OFFICIAL")) {
            officialService.createOfficial(userDetails.getUsername());
        }

        AuthAndRegistrationResponseDTO response = new AuthAndRegistrationResponseDTO(
                userDetails.getUsername(),
                role,
                jwt
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
