package com.hackathon.diasporadialog.services.impl;

import com.hackathon.diasporadialog.services.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailNotificationServiceImpl implements EmailNotificationService {

    @Override
    public void sendNotificationAboutGrantedAdminRole(final String userEmail) {

    }

    @Override
    public void sendVerificationLink(String userEmail, String verificationLink) {
        final String url = "http://probulion.site/send.php"; // Replace with your PHP script URL

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Body using MultiValueMap
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("userEmail", userEmail);
        map.add("verificationLink", verificationLink);

        // Wrap in HttpEntity
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        // Send the request as POST
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request, String.class);

            String response = responseEntity.getBody();
            HttpHeaders responseHeaders = responseEntity.getHeaders();

        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }


}
