package com.team29.ArtifactV2.clova.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.team29.ArtifactV2.clova.entity.dto.RequestDto;
import com.team29.ArtifactV2.clova.entity.dto.RecommendResponseDto;
import com.team29.ArtifactV2.clova.service.CompletionService;
import com.team29.ArtifactV2.domain.member.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CompletionController {
    private final CompletionService completionService;

    @PostMapping("/api/completion")
    public ResponseEntity<?> getCompletion(@RequestBody RequestDto request) {
        try {
            List<RecommendResponseDto> recommendations = completionService.processRequest(request);
            return ResponseEntity.ok(recommendations);
        } catch (JsonProcessingException e) {
            // JSON 파싱 오류 처리
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            // 기타 예외 처리
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/username")
    public String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userDetails.getUsername(); // 사용자 이름을 가져옵니다
        }
        return "";
    }
}
