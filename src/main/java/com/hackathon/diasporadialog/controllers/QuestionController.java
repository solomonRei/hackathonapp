package com.hackathon.diasporadialog.controllers;

import com.hackathon.diasporadialog.DTO.question.QuestionCreateDtoRequest;
import com.hackathon.diasporadialog.DTO.question.QuestionDtoResponse;
import com.hackathon.diasporadialog.domain.entities.QuestionEntity;
import com.hackathon.diasporadialog.services.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public List<QuestionEntity> getQuestions(
            @RequestParam(required = false) Integer meetingId,
            @RequestParam(required = false) Integer questionId,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) String titleFilter) {
        return questionService.getFilteredQuestions(meetingId, questionId, userId, titleFilter);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/create")
    public QuestionDtoResponse createQuestion(@RequestBody @Valid QuestionCreateDtoRequest questionCreateDtoRequest) {
        return questionService.createQuestion(questionCreateDtoRequest);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/vote")
    public void voteQuestion(@RequestParam Long questionId) {
        questionService.voteQuestion(questionId);
    }
}
