package com.hackathon.diasporadialog.services.impl;

import com.hackathon.diasporadialog.DTO.meetings.MeetingCreateDtoRequest;
import com.hackathon.diasporadialog.DTO.meetings.MeetingDtoResponse;
import com.hackathon.diasporadialog.domain.entities.MeetingEntity;
import com.hackathon.diasporadialog.domain.repositories.MeetingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
@Service
public class MeetingServiceImpl {

    private final MeetingRepository meetingRepository;

    public MeetingDtoResponse createMeeting(MeetingCreateDtoRequest meetingCreateDtoRequest) {
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
                .officialId(meeting.getOfficial().getId())
                .meetingDate(meeting.getMeetingDate().toString())
                .meetingLink(meeting.getMeetingLink())
                .build();
    }
}
