package com.hackathon.diasporadialog.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users_table")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "phone_number", nullable = false)
    private String phone;

    @Column(name = "verification_link")
    private String verificationLink;

    @Column(name = "enabled", columnDefinition = "boolean default true")
    @ColumnDefault("true")
    private boolean enabled;

    @Column(name = "is_verified", columnDefinition = "boolean default false")
    @ColumnDefault("false")
    private boolean verified;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<QuestionEntity> questions;
}