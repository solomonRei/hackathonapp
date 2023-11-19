package com.hackathon.diasporadialog.util;

import com.hackathon.diasporadialog.domain.entities.UserEntity;
import com.hackathon.diasporadialog.domain.repositories.UserRepository;
import com.hackathon.diasporadialog.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class UserAuthorizedUtils {

    private final UserRepository userRepository;

    public UserEntity getAuthenticatedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User not found"));
    }

    public String getAuthenticatedEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public Collection<String> getAuthenticatedUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}
