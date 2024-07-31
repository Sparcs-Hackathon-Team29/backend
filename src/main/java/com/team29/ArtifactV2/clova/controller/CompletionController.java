package com.team29.ArtifactV2.clova.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.team29.ArtifactV2.clova.entity.dto.RequestDto;
import com.team29.ArtifactV2.clova.service.ClovaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompletionController {
    private final ClovaService clovaService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/api/completion")
    public String getCompletion(@RequestBody RequestDto request) {
        try {
            // 전체 응답에서 'content' JSON 문자열 추출
            String content = clovaService.execute2(clovaService.execute(request));
            // 'content' JSON 문자열에서 'result' 배열 추출
            JsonNode resultArray = parseResultArray(extractContentFromResponse(content));

            // 'result' 배열을 JSON 문자열로 반환
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultArray);
        } catch (JsonProcessingException e) {
            // JSON 파싱 오류 처리
            e.printStackTrace();
            return "{\"error\":\"Failed to process JSON\"}";
        }

    }


    private String extractContentFromResponse(String response) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(response);
        return rootNode.path("result").path("message").path("content").asText();
    }

    private JsonNode parseResultArray(String content) throws JsonProcessingException {
        JsonNode contentNode = objectMapper.readTree(content);
        return contentNode.path("result");
    }
}
