package com.hackathon.diasporadialog.domain.repositories;

import com.hackathon.diasporadialog.domain.entities.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<VoteEntity, Long> {

    Optional<VoteEntity> findByUserIdAndQuestionId(Long userId, Long questionId);
}
