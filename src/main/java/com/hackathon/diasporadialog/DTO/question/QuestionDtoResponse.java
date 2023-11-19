package com.hackathon.diasporadialog.DTO.question;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class QuestionDtoResponse {

    private Long questionId;

    private String questionTitle;

    private String questionText;

    private String questionAuthor;

    private Long meetingId;

    private Long userId;

    private Integer voteCount;

}
