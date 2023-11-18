package com.hackathon.diasporadialog.services.impl;

import com.hackathon.diasporadialog.domain.entities.QuestionEntity;
import com.hackathon.diasporadialog.domain.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class QuestionServiceImpl {
    private QuestionRepository questionRepository;

    public List<QuestionEntity> getFilteredQuestions(Integer meetingId, Integer questionId, Integer userId, String titleFilter) {
        return questionRepository.findQuestionsByFilters(meetingId, questionId, userId, titleFilter);
    }
}
