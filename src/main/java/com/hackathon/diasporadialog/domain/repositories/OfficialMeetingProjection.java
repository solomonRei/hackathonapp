package com.hackathon.diasporadialog.domain.repositories;

import java.sql.Timestamp;

public interface OfficialMeetingProjection {
    Integer getOfficialId();
    String getOfficialName();
    String getOfficialPosition();
    String getOfficialBio();
    Integer getMeetingId();
    String getMeetingLink();
    Timestamp getMeetingDate();
}

