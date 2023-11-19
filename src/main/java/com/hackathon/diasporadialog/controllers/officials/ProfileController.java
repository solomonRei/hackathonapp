package com.hackathon.diasporadialog.controllers.officials;

import com.hackathon.diasporadialog.DTO.profile.ProfileOfficialUpdateDtoRequest;
import com.hackathon.diasporadialog.DTO.profile.ProfileOfficialDtoResponse;
import com.hackathon.diasporadialog.services.impl.OfficialServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final OfficialServiceImpl officialService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/update")
    public ResponseEntity<ProfileOfficialDtoResponse> update(@RequestBody @Valid ProfileOfficialUpdateDtoRequest profileOfficialUpdateDtoRequest) {
        var officialResponse = officialService.updateOfficial(profileOfficialUpdateDtoRequest);
        return ResponseEntity.ok(officialResponse);
    }

}
