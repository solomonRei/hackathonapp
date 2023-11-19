package com.hackathon.diasporadialog.services.impl;

import com.hackathon.diasporadialog.DTO.profile.ProfileOfficialDtoResponse;
import com.hackathon.diasporadialog.DTO.profile.ProfileOfficialUpdateDtoRequest;
import com.hackathon.diasporadialog.domain.entities.OfficialEntity;
import com.hackathon.diasporadialog.domain.repositories.OfficialRepository;
import com.hackathon.diasporadialog.domain.repositories.UserRepository;
import com.hackathon.diasporadialog.exceptions.UserNotFoundException;
import com.hackathon.diasporadialog.util.UserAuthorizedUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OfficialServiceImpl {

    private final OfficialRepository officialRepository;

    private final UserRepository userRepository;

    private final UserAuthorizedUtils userAuthorizedUtils;

    public ProfileOfficialDtoResponse createOfficial(String username) {
        var user = userRepository.findByEmail(username).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
        log.info("Create official profile for user: {}", user.getName());
        var official = OfficialEntity.builder()
                .position("Your position")
                .user(user)
                .build();

        officialRepository.save(official);

        log.info("Official profile created for user: {}", user.getName());
        return ProfileOfficialDtoResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .bio(official.getBio())
                .position(official.getPosition())
                .workPlace(official.getWorkPlace())
                .build();

    }

    public OfficialEntity getOfficial(String username) {
        var user = userRepository.findByEmail(username).orElseThrow(
                () -> new UserNotFoundException("User not found ")
        );
        log.info("Get official profile for user: {}", user.getName());
        return officialRepository.findByUserId(user.getId()).orElseThrow(
                () -> new UserNotFoundException("User not found in official repository with id: " + user.getId() + " and email: " + user.getEmail())
        );
    }

    public ProfileOfficialDtoResponse updateOfficial(ProfileOfficialUpdateDtoRequest profileOfficialUpdateDtoRequest) {
        var userAuthenticated = userAuthorizedUtils.getAuthenticatedUser();
        log.info("Update official profile for user: {}", userAuthenticated.getName());

        var official = officialRepository.findByUserId(userAuthenticated.getId()).orElseThrow(
                () -> new UserNotFoundException("User not found in official repository with id: " + userAuthenticated.getId() + " and email: " + userAuthenticated.getEmail())
        );

        if (profileOfficialUpdateDtoRequest.getBio() != null) {
            official.setBio(profileOfficialUpdateDtoRequest.getBio());
        }
        if (profileOfficialUpdateDtoRequest.getWorkPlace() != null) {
            official.setWorkPlace(profileOfficialUpdateDtoRequest.getWorkPlace());
        }
        if (profileOfficialUpdateDtoRequest.getPosition() != null) {
            official.setPosition(profileOfficialUpdateDtoRequest.getPosition());
        }

        officialRepository.save(official);

        return ProfileOfficialDtoResponse.builder()
                .name(userAuthenticated.getName())
                .email(userAuthenticated.getEmail())
                .bio(official.getBio())
                .position(official.getPosition())
                .workPlace(official.getWorkPlace())
                .build();
    }
}
