package com.hackathon.diasporadialog.services;

public interface EmailNotificationService {

    void sendNotificationAboutGrantedAdminRole(final String userEmail);

    void sendVerificationLink(final String to, final String verificationLink);
}
