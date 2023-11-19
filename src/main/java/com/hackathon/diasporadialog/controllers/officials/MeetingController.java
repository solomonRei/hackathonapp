package com.hackathon.diasporadialog.controllers.officials;

import com.hackathon.diasporadialog.DTO.zoom.ZoomMeetingDtoRequest;
import com.hackathon.diasporadialog.services.impl.ZoomServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/meetings")
public class MeetingController {

    private final ZoomServiceImpl zoomService;

    @PostMapping(value = "/create")
    public void register(@RequestBody @Valid ZoomMeetingDtoRequest zoomMeetingDtoRequest) {
        var linkToZoom = zoomService.saveMeeting(zoomMeetingDtoRequest);

    }
}
