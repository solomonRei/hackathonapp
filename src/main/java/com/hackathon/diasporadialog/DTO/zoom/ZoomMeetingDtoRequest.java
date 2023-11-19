package com.hackathon.diasporadialog.DTO.zoom;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZoomMeetingDtoRequest {

    private String topic;

    @NotBlank(message = "Start time is mandatory")
    private String startTime;

    private String timezone = "Europe/Chisinau";

    @Min(value = 1, message = "Duration must be at least 1 minute")
    private int duration;

    private int type = 2;
}

