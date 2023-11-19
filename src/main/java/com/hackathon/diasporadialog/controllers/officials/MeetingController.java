package com.hackathon.diasporadialog.controllers.officials;

import com.hackathon.diasporadialog.DTO.meetings.MeetingCreateDtoRequest;
import com.hackathon.diasporadialog.DTO.meetings.MeetingDtoResponse;
import com.hackathon.diasporadialog.DTO.zoom.ZoomMeetingDtoRequest;
import com.hackathon.diasporadialog.domain.repositories.OfficialMeetingProjection;
import com.hackathon.diasporadialog.services.impl.MeetingServiceImpl;
import com.hackathon.diasporadialog.services.impl.OfficialServiceImpl;
import com.hackathon.diasporadialog.services.impl.ZoomServiceImpl;
import com.hackathon.diasporadialog.util.UserAuthorizedUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/meetings")
public class MeetingController {

    private final ZoomServiceImpl zoomService;

    private final MeetingServiceImpl meetingService;

    private final OfficialServiceImpl officialService;

    private final UserAuthorizedUtils userAuthorizedUtils;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/create")
    public ResponseEntity<MeetingDtoResponse> register(@RequestBody @Valid ZoomMeetingDtoRequest zoomMeetingDtoRequest) {
        var linkToMeeting = zoomService.createZoomMeeting(zoomMeetingDtoRequest);

        var meetingCreateDtoRequest = MeetingCreateDtoRequest.builder()
                .official(officialService.getOfficial(userAuthorizedUtils.getAuthenticatedUser().getEmail()))
                .meetingDate(zoomMeetingDtoRequest.getStartTime())
                .meetingLink(linkToMeeting)
                .build();
        var newMeeting = meetingService.createMeeting(meetingCreateDtoRequest);

        return ResponseEntity.ok(newMeeting);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/official-meetings")
    public ResponseEntity<List<OfficialMeetingProjection>> getMeetings(@RequestParam Integer userId) {
        List<OfficialMeetingProjection> meetings = meetingService.getOfficialsAndMeetings(userId);
        return ResponseEntity.ok(meetings);
    }
}
