package com.hackathon.diasporadialog.DTO.meetings;

import com.hackathon.diasporadialog.domain.entities.QuestionEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
public class MeetingDtoResponse {

    private Long meetingId;

    private Long officialId;

    private String meetingDate;

    private String meetingLink;

    private String name;

    private String surname;

    private List<QuestionEntity> questions = new ArrayList<>();

}
