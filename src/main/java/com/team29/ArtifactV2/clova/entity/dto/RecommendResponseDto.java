package com.team29.ArtifactV2.clova.entity.dto;

import com.team29.ArtifactV2.domain.recommend.entity.Recommend;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendResponseDto {
    private String name;
    private String address;
    private String url;

    public RecommendResponseDto(Recommend recommend) {
        this.name = recommend.getName();
        this.address = recommend.getAddress();
        this.url = recommend.getUrl();
    }
}
