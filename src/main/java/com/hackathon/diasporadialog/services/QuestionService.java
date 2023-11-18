package com.hackathon.diasporadialog.services;

import com.hackathon.diasporadialog.domain.entities.QuestionEntity;

import java.util.List;

public interface QuestionService {

    List<QuestionEntity> getFilteredQuestions(Integer meetingId, Integer questionId, Integer userId, String titleFilter);

}
