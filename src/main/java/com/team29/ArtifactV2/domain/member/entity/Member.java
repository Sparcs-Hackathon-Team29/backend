package com.team29.ArtifactV2.domain.member.entity;


import jakarta.persistence.*;

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
