package com.hackathon.diasporadialog.domain.repositories;

import com.hackathon.diasporadialog.domain.entities.MeetingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<MeetingEntity, Long> {
    @Query(value = "SELECT * FROM get_officials_and_meetings(:userId)", nativeQuery = true)
    List<OfficialMeetingProjection> getOfficialsAndMeetings(@Param("userId") Integer userId);
}
