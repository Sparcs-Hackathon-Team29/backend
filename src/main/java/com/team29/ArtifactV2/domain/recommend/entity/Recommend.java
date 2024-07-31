package com.team29.ArtifactV2.domain.recommend.entity;

import com.team29.ArtifactV2.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Recommend {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String address;

    private String url;

    private String username;


    private Long createId;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
