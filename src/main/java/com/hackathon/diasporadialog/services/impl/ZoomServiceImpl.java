package com.hackathon.diasporadialog.services.impl;

import com.hackathon.diasporadialog.DTO.zoom.MeetingDtoResponse;
import com.hackathon.diasporadialog.DTO.zoom.ZoomMeetingDtoRequest;
import com.hackathon.diasporadialog.domain.entities.MeetingEntity;
import com.hackathon.diasporadialog.domain.repositories.MeetingRepository;
import com.hackathon.diasporadialog.exceptions.meetings.ZoomTokenErrorException;
import com.hackathon.diasporadialog.util.UserAuthorizedUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class ZoomServiceImpl {

    private final MeetingRepository meetingRepository;

    private final String accountId = "yR0iKwOfTpuUfb8mXpAIag";

    private final String clientId = "KNdImTeYSkecPVFYRZHVSA";

    private final String clientSecret = "5mMeUndPPgQO1KGORvWHBLOvKQiFwg1o";

    public ResponseEntity<MeetingDtoResponse> saveMeeting(ZoomMeetingDtoRequest zoomMeetingDtoRequest) {
        var linkToZoom = createZoomMeeting(zoomMeetingDtoRequest);

        var meetingEntity = new MeetingEntity();
        meetingEntity.setMeetingLink(linkToZoom);
        meetingEntity.setMeetingDate(LocalDateTime.parse(zoomMeetingDtoRequest.getStartTime()));

        // Save the meeting entity to the database
        meetingRepository.save(meetingEntity);

        // Create a response DTO (assuming you have a constructor or setters in MeetingDtoResponse)
        MeetingDtoResponse meetingDtoResponse = new MeetingDtoResponse();
        meetingDtoResponse.setMeetingLink(linkToZoom);
        // Set other properties in the DTO as required


        return new ResponseEntity<>(meetingDtoResponse, HttpStatus.OK);
    }

    private String createZoomMeeting(ZoomMeetingDtoRequest request) {
        var accessToken = getAccessToken();
        System.out.println(accessToken);

        return "testMeeting";

//        if (accessToken == null) {
//            throw new ZoomTokenErrorException("Failed to retrieve access token from Zoom");
//        }
//
//        var restTemplate = new RestTemplate();
//        var headers = new HttpHeaders();
//
//        headers.add("Authorization", "Bearer " + accessToken);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<ZoomMeetingDtoRequest> requestEntity = new HttpEntity<>(request, headers);
//
//        ResponseEntity<String> response = restTemplate.postForEntity(
//                "https://api.zoom.us/v2/users/me/meetings",
//                requestEntity,
//                String.class
//        );
//
//        return response.getBody();
    }


    private String getAccessToken() {
        var restTemplate = new RestTemplate();

        var tokenEndpoint = "https://zoom.us/oauth/token";

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        var base64Credentials = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
        headers.add("Authorization", "Basic " + base64Credentials);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("account_id", accountId);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        var response = restTemplate.postForEntity(tokenEndpoint, entity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            var responseBody = response.getBody();
            System.out.println(responseBody);
            return responseBody != null ? (String) responseBody.get("access_token") : null;
        } else {
            throw new ZoomTokenErrorException("Failed to retrieve access token from Zoom");
        }
    }
}

