package com.team29.ArtifactV2.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team29.ArtifactV2.domain.member.dto.SignUpDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String password;

    public Member(SignUpDto dto) {
        this.email = dto.getEmail();
        this.password = dto.getPassword();
    }
}
