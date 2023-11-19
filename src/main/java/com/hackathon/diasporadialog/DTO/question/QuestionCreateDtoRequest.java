package com.hackathon.diasporadialog.DTO.question;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionCreateDtoRequest {

    private String questionTitle;

    private String questionText;

    private Long meetingId;
}
