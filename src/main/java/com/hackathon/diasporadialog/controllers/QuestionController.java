package com.hackathon.diasporadialog.controllers;

import com.hackathon.diasporadialog.domain.entities.QuestionEntity;
import com.hackathon.diasporadialog.services.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/questions")
public class QuestionController {

    private QuestionService questionService;

    @GetMapping
    public List<QuestionEntity> getQuestions(
            @RequestParam(required = false) Integer meetingId,
            @RequestParam(required = false) Integer questionId,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) String titleFilter) {
        return questionService.getFilteredQuestions(meetingId, questionId, userId, titleFilter);
    }
}
