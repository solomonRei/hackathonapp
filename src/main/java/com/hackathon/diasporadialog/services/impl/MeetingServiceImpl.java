package com.hackathon.diasporadialog.services.impl;

import com.hackathon.diasporadialog.DTO.meetings.MeetingCreateDtoRequest;
import com.hackathon.diasporadialog.DTO.meetings.MeetingDtoResponse;
import com.hackathon.diasporadialog.domain.entities.MeetingEntity;
import com.hackathon.diasporadialog.domain.repositories.MeetingRepository;
import com.hackathon.diasporadialog.domain.repositories.OfficialMeetingProjection;
import com.hackathon.diasporadialog.util.UserAuthorizedUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MeetingServiceImpl {

    private final MeetingRepository meetingRepository;

    private final UserAuthorizedUtils userAuthorizedUtils;

    public MeetingDtoResponse createMeeting(MeetingCreateDtoRequest meetingCreateDtoRequest) {
        var user = userAuthorizedUtils.getAuthenticatedUser();
        log.info("Create meeting");
        String dateTimeString = meetingCreateDtoRequest.getMeetingDate();
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTimeString, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime localDateTime = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
        log.info("Local date time: {}", localDateTime);
        var meeting = MeetingEntity.builder()
                .official(meetingCreateDtoRequest.getOfficial())
                .meetingDate(localDateTime)
                .meetingLink(meetingCreateDtoRequest.getMeetingLink())
                .build();

        meetingRepository.save(meeting);

        return MeetingDtoResponse.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .meetingId(meeting.getId())
                .officialId(meeting.getOfficial().getId())
                .meetingDate(meeting.getMeetingDate().toString())
                .meetingLink(meeting.getMeetingLink())
                .build();
    }

    public List<OfficialMeetingProjection> getOfficialsAndMeetings(Integer userId) {
        return meetingRepository.getOfficialsAndMeetings(userId);
    }

    public List<MeetingDtoResponse> getMeetings() {
        var meetings = meetingRepository.findAll();
        return meetings.stream()
                .map(meeting -> MeetingDtoResponse.builder()
                        .name(meeting.getOfficial().getUser().getName())
                        .surname(meeting.getOfficial().getUser().getSurname())
                        .meetingId(meeting.getId())
                        .officialId(meeting.getOfficial().getId())
                        .meetingDate(meeting.getMeetingDate().toString())
                        .meetingLink(meeting.getMeetingLink())
                        .build())
                .toList();
    }
}
