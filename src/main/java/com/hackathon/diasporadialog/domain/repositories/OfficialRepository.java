package com.hackathon.diasporadialog.domain.repositories;

import com.hackathon.diasporadialog.domain.entities.OfficialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfficialRepository extends JpaRepository<OfficialEntity, Long> {
    Optional<OfficialEntity> findByUserId(Long userID);
}
