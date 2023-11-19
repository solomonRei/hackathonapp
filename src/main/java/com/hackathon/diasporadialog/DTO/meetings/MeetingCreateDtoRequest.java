package com.hackathon.diasporadialog.DTO.meetings;

import com.hackathon.diasporadialog.domain.entities.OfficialEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MeetingCreateDtoRequest {

    private OfficialEntity official;

    private String meetingDate;

    private String meetingLink;

}
