package com.hackathon.diasporadialog.DTO.profile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ProfileOfficialDtoResponse {

    private String name;

    private String email;

    private String bio;

    private String workPlace;

    private String position;
}
