package com.hackathon.diasporadialog.services.impl;

import com.hackathon.diasporadialog.DTO.question.QuestionCreateDtoRequest;
import com.hackathon.diasporadialog.DTO.question.QuestionDtoResponse;
import com.hackathon.diasporadialog.domain.entities.QuestionEntity;
import com.hackathon.diasporadialog.domain.entities.VoteEntity;
import com.hackathon.diasporadialog.domain.repositories.MeetingRepository;
import com.hackathon.diasporadialog.domain.repositories.QuestionRepository;
import com.hackathon.diasporadialog.domain.repositories.VoteRepository;
import com.hackathon.diasporadialog.exceptions.UserNotFoundException;
import com.hackathon.diasporadialog.services.QuestionService;
import com.hackathon.diasporadialog.util.UserAuthorizedUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    private final MeetingRepository meetingRepository;

    private final VoteRepository voteRepository;

    private final UserAuthorizedUtils userAuthorizedUtils;

    public List<QuestionEntity> getFilteredQuestions(Integer meetingId, Integer questionId, Integer userId, String titleFilter) {
        return questionRepository.findQuestionsByFilters(meetingId, questionId, userId, titleFilter);
    }

    public QuestionDtoResponse createQuestion(QuestionCreateDtoRequest questionCreateDtoRequest) {

        var meeting = meetingRepository.findById(questionCreateDtoRequest.getMeetingId())
                .orElseThrow(() -> new UserNotFoundException("Meeting not found with id: " + questionCreateDtoRequest.getMeetingId() +
                        " and meetingId: " + questionCreateDtoRequest.getMeetingId()));
        var user = userAuthorizedUtils.getAuthenticatedUser();
        log.info("Create question for user: {}", user.getName());

        var questionEntity = QuestionEntity.builder()
                .questionTitle(questionCreateDtoRequest.getQuestionTitle())
                .questionText(questionCreateDtoRequest.getQuestionText())
                .meeting(meeting)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        questionRepository.save(questionEntity);

        return QuestionDtoResponse.builder()
                .questionId(questionEntity.getQuestionId())
                .questionTitle(questionEntity.getQuestionTitle())
                .questionText(questionEntity.getQuestionText())
                .questionAuthor(questionEntity.getUser().getName())
                .meetingId(questionEntity.getMeeting().getId())
                .userId(questionEntity.getUser().getId())
                .voteCount(questionEntity.getVoteCount())
                .build();
    }

    public void voteQuestion(Long questionId) {
        var user = userAuthorizedUtils.getAuthenticatedUser();
        var question = questionRepository.findById(questionId)
                .orElseThrow(() -> new UserNotFoundException("Question not found with id: " + questionId));

        var vote = VoteEntity.builder()
                .questionId(questionId)
                .userId(user.getId())
                .build();
        voteRepository.save(vote);

        question.setVoteCount(question.getVoteCount() + 1);
        questionRepository.save(question);

    }
}
