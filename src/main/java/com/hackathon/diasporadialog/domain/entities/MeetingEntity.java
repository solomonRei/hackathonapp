package com.hackathon.diasporadialog.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "meetings")
public class MeetingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "official_id", referencedColumnName = "id")
    private OfficialEntity official;

    @Column(name = "meeting_link")
    private String meetingLink;

    @Column(name = "meeting_date")
    private LocalDateTime meetingDate;

    @JsonBackReference
    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL)
    private List<QuestionEntity> questions;
}