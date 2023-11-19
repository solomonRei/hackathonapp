package com.hackathon.diasporadialog.services.impl;

import com.hackathon.diasporadialog.domain.entities.OfficialEntity;
import com.hackathon.diasporadialog.domain.repositories.OfficialRepository;
import com.hackathon.diasporadialog.domain.repositories.UserRepository;
import com.hackathon.diasporadialog.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OfficialServiceImpl {

    private final OfficialRepository officialRepository;

    private final UserRepository userRepository;

    public void createOfficial(String username) {
        var user = userRepository.findByEmail(username).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
        var official = OfficialEntity.builder()
                .position("Your position")
                .user(user)
                .build();

        officialRepository.save(official);

    }

}
