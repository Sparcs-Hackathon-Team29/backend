package com.team29.ArtifactV2.domain.member.entity;

import com.team29.ArtifactV2.domain.member.dto.SignUpDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

import jakarta.persistence.Id;
import lombok.*;


@Entity
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String password;
    private String role;
}
