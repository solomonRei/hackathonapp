package com.hackathon.diasporadialog.domain.repositories;

import com.hackathon.diasporadialog.domain.entities.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    @Query(value = "SELECT * FROM get_filtered_questions(?1, ?2, ?3, ?4)", nativeQuery = true)
    List<QuestionEntity> findQuestionsByFilters(Integer meetingId, Integer questionId, Integer userId, String titleFilter);

}
