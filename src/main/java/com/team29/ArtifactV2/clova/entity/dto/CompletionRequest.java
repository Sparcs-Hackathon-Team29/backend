package com.team29.ArtifactV2.clova.entity.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder

public class CompletionRequest {
    private List<Message> messages;
    private RequestData requestData;

    @Data
    @Builder
    public static class Message {
        private String role;
        private String content;
    }

    @Data
    @Builder
    public static class RequestData {
        private double topP;
        private int topK;
        private int maxTokens;
        private double temperature;
        private double repeatPenalty;
        private boolean includeAiFilters;
        private int seed;
    }
}
