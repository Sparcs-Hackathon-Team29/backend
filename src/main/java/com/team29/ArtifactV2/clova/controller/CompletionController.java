package com.team29.ArtifactV2.clova.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team29.ArtifactV2.clova.entity.dto.RequestDto;
import com.team29.ArtifactV2.clova.service.ClovaService;
import com.team29.ArtifactV2.domain.member.dto.CustomUserDetails;
import com.team29.ArtifactV2.domain.member.entity.Member;
import com.team29.ArtifactV2.domain.member.repository.MemberRepository;
import com.team29.ArtifactV2.domain.recommend.entity.Recommend;
import com.team29.ArtifactV2.domain.recommend.repository.RecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompletionController {
    private final ClovaService clovaService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RecoRepository recoRepository;
    private final MemberRepository memberRepository;
    private Long ctn = 0L;

    @PostMapping("/api/completion")
    public ResponseEntity<?> getCompletion(@RequestBody RequestDto request) {

        try {
            // 전체 응답에서 'content' JSON 문자열 추출
            String content = clovaService.execute2(clovaService.execute(request));

            // JSON 응답 출력 (디버깅 용도)
            System.out.println("Full JSON Response: " + content);

            // 'content' JSON 문자열에서 'content' 내부의 JSON 문자열 추출
            JsonNode resultArray = extractResultArrayFromResponse(content);
            System.out.println("Result Array: " + resultArray.toString());

            // 현재 로그인한 사용자의 이름을 가져와서 Member를 조회
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
                String username = userDetails.getUsername(); // 사용자 이름을 가져옵니다

                // 사용자 이름으로 Member를 조회합니다
                Member member = memberRepository.findByUsername(username);

                // 'result' 배열의 각 요소를 'Recommend' 엔티티로 변환하여 저장
                if (resultArray.isArray()) {
                    for (JsonNode node : resultArray) {
                        Recommend recommend = new Recommend();
                        recommend.setName(node.path("name").asText());
                        recommend.setAddress(node.path("address").asText());

                        // Recommend 객체에 Member 설정
                        recommend.setUrl("https://map.naver.com/p/search/" + recommend.getName());
                        recommend.setCreateId(ctn);
                        recommend.setUsername(member.getUsername());
                        recoRepository.save(recommend);
                    }
                }
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (JsonProcessingException e) {
            // JSON 파싱 오류 처리
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // 기타 예외 처리
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private JsonNode extractResultArrayFromResponse(String response) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(response);
        // 'result' -> 'message' -> 'content' 경로로 JSON 문자열 추출
        String contentJson = rootNode.path("result").path("message").path("content").asText();

        // contentJson이 JSON 배열 형식으로 되어 있어야 합니다.
        // JSON 문자열을 다시 JsonNode로 변환
        JsonNode contentNode = objectMapper.readTree("[" + contentJson + "]"); // 배열로 감싸기
        ctn++;
        return contentNode;
    }

    @GetMapping("/username")
    public String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userDetails.getUsername(); // 사용자 이름을 가져옵니다
            // 사용자 이름으로 Member를 조회합니다
        }
        return "";
    }
}
